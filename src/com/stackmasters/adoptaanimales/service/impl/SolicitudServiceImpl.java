package com.stackmasters.adoptaanimales.service.impl;

import com.stackmasters.adoptaanimales.service.SolicitudService;
import com.stackmasters.adoptaanimales.dto.*;
import com.stackmasters.adoptaanimales.exception.*;
import com.stackmasters.adoptaanimales.exception.MascotaNoDisponibleException;
import com.stackmasters.adoptaanimales.model.*;
import com.stackmasters.adoptaanimales.model.SolicitudAdopcion.EstadoSolicitud;
import com.stackmasters.adoptaanimales.model.Cita.EstadoCita;
import com.stackmasters.adoptaanimales.model.Mascota.EstadoMascota;
import com.stackmasters.adoptaanimales.repository.*;
import com.stackmasters.adoptaanimales.model.RespuestaBD;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Sky L.
 */

public class SolicitudServiceImpl implements SolicitudService {
    
    private final MascotaRepository mascotaRepo;
    private final AdoptanteRepository adoptanteRepo;
    private final SolicitudAdopcionRepository solicitudRepo;

    public SolicitudServiceImpl(
        MascotaRepository mascotaRepo,
        AdoptanteRepository adoptanteRepo,
        SolicitudAdopcionRepository solicitudRepo
    ) {
        this.mascotaRepo = mascotaRepo;
        this.adoptanteRepo = adoptanteRepo;
        this.solicitudRepo = solicitudRepo;
    }
    
    // Crear Solicitud
    
    @Override
    public SolicitudAdopcion crear(int mascotaId, CrearAdoptanteDTO dto, LocalDateTime fechaCita)
            throws DatosInvalidosException, MascotaNoDisponibleException, YaExisteSolicitudActivaException {

        // Validación básica del DTO
        if (dto == null || dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new DatosInvalidosException("Datos del adoptante incompletos.");
        }
        
         // Verificar que la mascota exista
        Mascota mascota = mascotaRepo.findById(mascotaId);
       
        if (mascota == null) {
            throw new MascotaNoDisponibleException("La mascota indicada no existe.");
        }

        // Verificar disponibilidad de la mascota
        if (mascota.getEstado() != Mascota.EstadoMascota.EnAlbergue) {
            throw new MascotaNoDisponibleException("La mascota no está disponible para adopción.");
        }
        
        // Validar que no exista una solicitud activa previa
        List<SolicitudAdopcion> solicitudes = solicitudRepo.findAll();

        for (SolicitudAdopcion s : solicitudes) {
            boolean esMismaMascota = s.getMascotaId() == mascotaId;
            boolean estaActiva = 
                s.getEstado() == EstadoSolicitud.Pendiente ||
                s.getEstado() == EstadoSolicitud.Aprobada;

            if (esMismaMascota && estaActiva) {
                throw new YaExisteSolicitudActivaException(
                    "Ya existe una solicitud activa para esta mascota."
                );
            }
        }
        
        // Creamos el adoptante
        RespuestaBD respuestaAdoptante = adoptanteRepo.insertAdoptante(new Adoptante(
            -1,
            dto.getNombre(),
            dto.getApellido(),
            dto.getFechaNacimiento(),
            dto.getTelefono(),
            dto.getDireccion()
        ));
        
        if (!respuestaAdoptante.isOk()) {
            throw new DatosInvalidosException("No se pudo crear el adoptante");
        }

        // Crear nueva solicitud
        SolicitudAdopcion nueva = new SolicitudAdopcion();
        nueva.setEstado(EstadoSolicitud.Pendiente);
        nueva.setFechaSolicitud(LocalDate.now());
        nueva.setFechaRespuesta(null);
        nueva.setMotivoRechazo(null);
        nueva.setAdoptanteId(respuestaAdoptante.getId());
        nueva.setMascotaId(mascotaId);
        nueva.setCita(fechaCita);

        // insert usando RespuestaBD (para obtener el ID generado)
        RespuestaBD respuesta = solicitudRepo.insertSolicitud(nueva);

        if (!respuesta.isOk()) {
            throw new DatosInvalidosException("No se pudo crear la solicitud.");
        }

        // Asignar el ID generado por la BD al objeto
        nueva.setIdSolicitud(respuesta.getId());
        
        // Actualizamos el estado de la mascota
        mascota.setEstado(EstadoMascota.EnProcesoDeAdopcion);
        mascotaRepo.updateMascota(mascota, mascotaId);

        return nueva;
    }

    // Obtener las solicitudes por el id
    @Override
    public SolicitudAdopcion obtener(int solicitudId) {
        return solicitudRepo.findById(solicitudId);
    }

    // Actualizar el estado de la solicitud
    @Override
    public boolean actualizarEstado(int solicitudId, EstadoSolicitud nuevoEstado, String motivoRechazo)
            throws DatosInvalidosException {

        //  Verificar existencia
        SolicitudAdopcion solicitud = solicitudRepo.findById(solicitudId);
        if (solicitud == null) {
            throw new DatosInvalidosException("La solicitud no existe.");
        }

        // Validar reglas del rechazo
        if (nuevoEstado == EstadoSolicitud.Rechazada) {
            if (motivoRechazo == null || motivoRechazo.isBlank()) {
                throw new DatosInvalidosException("Debe proporcionar un motivo de rechazo.");
            }
        }

        // Aplicar cambios
        solicitud.setEstado(nuevoEstado);
        solicitud.setFechaRespuesta(LocalDate.now());
        solicitud.setMotivoRechazo(motivoRechazo);
        
        if (!solicitudRepo.updateSolicitud(solicitud, solicitudId)) {
            throw new DatosInvalidosException("No se pudo eliminar la solicitud");
        }
        
         // Verificar que la mascota exista
        Mascota mascota = mascotaRepo.findById(solicitud.getMascotaId());

        if (mascota != null) {
            if (nuevoEstado == EstadoSolicitud.Rechazada || nuevoEstado == EstadoSolicitud.Cancelada) {
                mascota.setEstado(EstadoMascota.EnAlbergue);
            } else if (nuevoEstado == EstadoSolicitud.Aprobada) {
                mascota.setEstado(EstadoMascota.Adoptada);
            } else {
                mascota.setEstado(EstadoMascota.EnProcesoDeAdopcion);
            }
            
            mascotaRepo.updateMascota(mascota, solicitud.getMascotaId());
        }
        
        return true;
    }
    
    @Override
    public boolean eliminar(int solicitudId) throws DatosInvalidosException {
        //  Verificar existencia
        SolicitudAdopcion solicitud = solicitudRepo.findById(solicitudId);
        
        if (solicitud == null) {
            throw new DatosInvalidosException("La solicitud no existe.");
        }
        
        // Verificar que la mascota exista
        Mascota mascota = mascotaRepo.findById(solicitud.getMascotaId());
       
        if (mascota != null) {
            mascota.setEstado(EstadoMascota.EnAlbergue);
            mascotaRepo.updateMascota(mascota, solicitud.getMascotaId());
        }
        
        return solicitudRepo.delete(solicitudId);
    }

    // Listar las solicitudes 
    @Override
    public List<SolicitudAdopcion> listar(FiltroSolicitudDTO filtro) {
        // Obtener todas las solicitudes desde la BD
        List<SolicitudAdopcion> todas = solicitudRepo.findAll();

        // Si no hay filtro, devolver todas
        if (filtro == null) {
            return todas;
        }

        List<SolicitudAdopcion> filtradas = new java.util.ArrayList<>();

        for (SolicitudAdopcion s : todas) {

            // Filtro por texto (nombre de mascota o adoptante)
            if (filtro.getTexto() != null && !filtro.getTexto().isBlank()) {
                String txt = filtro.getTexto().toLowerCase();

                // Obtener nombre de mascota
                Mascota m = mascotaRepo.findById(s.getMascotaId());
                String nombreMascota = (m != null) ? m.getNombre().toLowerCase() : "";

                // Obtener nombre completo del adoptante
                Adoptante a = adoptanteRepo.findById(s.getAdoptanteId());
                String nombreAdoptante = "";
                if (a != null) {
                    nombreAdoptante = (a.getNombre() + " " + a.getApellido()).toLowerCase();
                }

                // Si texto NO está en ninguno → NO pasa el filtro
                if (!nombreMascota.contains(txt) && !nombreAdoptante.contains(txt)) {
                    continue;
                }
            } 

            // Filtro por estado de la solicitud
            if (filtro.getEstadoSolicitud() != null &&
                s.getEstado() != filtro.getEstadoSolicitud()) {
                continue;
            }

            // Filtro por ID de mascota/publicación
            if (filtro.getMascotaId() != 0 &&
                s.getMascotaId() != filtro.getMascotaId()) {
                continue;
            }

            // Filtro por rango de fechas (fechaSolicitud)
            if (filtro.getFechaDesde() != null &&
                s.getFechaSolicitud() != null &&
                s.getFechaSolicitud().isBefore(filtro.getFechaDesde())) {
                continue;
            }

            if (filtro.getFechaHasta() != null &&
                s.getFechaSolicitud() != null &&
                s.getFechaSolicitud().isAfter(filtro.getFechaHasta())) {
                continue;
            }

            // Si pasa todos los filtros, lo agregamos
            filtradas.add(s);
        }

        return filtradas;
    }
    
    public int totalSolicitudes() {
        return solicitudRepo.totalSolicitudes();
    }
    
    // Contar solicitudes por estado
    public int totalSolicitudesPorEstado(EstadoSolicitud estado) {
        if (estado == null) {
            return 0;
        }

        return solicitudRepo.totalSolicitudPendiente(estado.db());
    }
}
