/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stackmasters.adoptaanimales.service.impl;

import com.stackmasters.adoptaanimales.service.SolicitudService;
import com.stackmasters.adoptaanimales.dto.*;
import com.stackmasters.adoptaanimales.exception.*;
import com.stackmasters.adoptaanimales.exception.MascotaNoDisponibleException;
import com.stackmasters.adoptaanimales.model.*;
import com.stackmasters.adoptaanimales.model.SolicitudAdopcion.EstadoSolicitud;
import com.stackmasters.adoptaanimales.model.Cita.EstadoCita;
import com.stackmasters.adoptaanimales.repository.*;
import com.stackmasters.adoptaanimales.model.RespuestaBD;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Sky L.
 */

public class SolicitudServiceImpl implements SolicitudService {
    
    private final MascotaRepository mascotaRepo;
    private final AdoptanteRepository adoptanteRepo;
    private final SolicitudAdopcionRepository solicitudRepo;
    private final CitaRepository citaRepo;

    public SolicitudServiceImpl(
        MascotaRepository mascotaRepo,
        AdoptanteRepository adoptanteRepo,
        SolicitudAdopcionRepository solicitudRepo,
        CitaRepository citaRepo
    ) {
        this.mascotaRepo = mascotaRepo;
        this.adoptanteRepo = adoptanteRepo;
        this.solicitudRepo = solicitudRepo;
        this.citaRepo = citaRepo;
    }
    
    // Crear Solicitud
    
    @Override
    public SolicitudAdopcion crear(int mascotaId, CrearAdoptanteDTO dto)
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

        // Crear nueva solicitud
        SolicitudAdopcion nueva = new SolicitudAdopcion();
        nueva.setEstado(EstadoSolicitud.Pendiente);
        nueva.setFechaSolicitud(LocalDate.now());
        nueva.setFechaRespuesta(null);
        nueva.setMotivoRechazo(null);
        nueva.setAdoptanteId(1);   // temporal para pruebas
        nueva.setMascotaId(mascotaId);

        // insert usando RespuestaBD (para obtener el ID generado)
        RespuestaBD respuesta = solicitudRepo.insertSolicitud(nueva);

    if (!respuesta.isOk()) {
        throw new DatosInvalidosException("No se pudo crear la solicitud.");
    }

    // Asignar el ID generado por la BD al objeto
    nueva.setIdSolicitud(respuesta.getId());

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

        // Verificar existencia
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

        // Guardar cambios
        return solicitudRepo.updateSolicitud(solicitud, solicitudId);
    }

    // Programar cita para una solicitud aprobada
    @Override
    public Cita programarCita(int solicitudId, CitaDTO dto)
            throws SolicitudNoAprobadaException, CitaYaExisteException, DatosInvalidosException {

        // Verificar solicitud
        SolicitudAdopcion solicitud = solicitudRepo.findById(solicitudId);
        if (solicitud == null) {
            throw new DatosInvalidosException("La solicitud no existe.");
        }

        // Solo puede programarse una cita si la solicitud está aprobada
        if (solicitud.getEstado() != EstadoSolicitud.Aprobada) {
            throw new SolicitudNoAprobadaException("La solicitud aún no está aprobada.");
        }

        // Verificar si ya existe cita asociada
        Cita citaExistente = citaRepo.findBySolicitudId(solicitudId);
        
        if (citaExistente != null) {
        throw new CitaYaExisteException("Esta solicitud ya tiene una cita programada.");
}
        // Crear la nueva cita
        Cita nueva = new Cita();
        nueva.setFechaHora(dto.getFechaHora());
        nueva.setEstadoCita(dto.getEstado());
        nueva.setNotas(dto.getNotas());
        nueva.setSolicitudId(solicitudId);
        nueva.setAlbergueId(dto.getAlbergueId());

        // Insert usando RespuestaBD para obtener el ID
        RespuestaBD respuesta = citaRepo.insertCita(nueva);

        if (!respuesta.isOk()) {
            throw new DatosInvalidosException("No se pudo crear la cita.");
        }

        // Asignar el ID generado por la BD
        nueva.setIdCita(respuesta.getId());

        return nueva;

    }

    // Obtener cita que este asociada a una solicitud
    @Override
    public Cita obtenerCita(int solicitudId) {
        return citaRepo.findBySolicitudId(solicitudId);
    }

    // Actualizar estado de la cita
    @Override
    public boolean actualizarEstadoCita(int solicitudId, EstadoCita nuevoEstado)
            throws DatosInvalidosException {

        Cita cita = obtenerCita(solicitudId);

        if (cita == null) {
            throw new DatosInvalidosException("No existe una cita para esta solicitud.");
        }

        cita.setEstadoCita(nuevoEstado);

        return citaRepo.updateCita(cita, cita.getIdCita());
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

        // Filtro por estado de cita (solo si existe cita)
        if (filtro.getEstadoCita() != null) {
            Cita cita = obtenerCita(s.getIdSolicitud());

            // Si no tiene cita no pasa el filtro
            if (cita == null || cita.getEstadoCita() != filtro.getEstadoCita()) {
                continue;
            }
        }

        // Si pasa todos los filtros, lo agregamos
        filtradas.add(s);
    }

    return filtradas;
}
    
    // Contar solicitudes por estado
    public int totalSolicitudesPorEstado(EstadoSolicitud estado) {
        if (estado == null) {
            return 0;
        }

        return solicitudRepo.totalSolicitudPendiente(estado.db());
    }
}
