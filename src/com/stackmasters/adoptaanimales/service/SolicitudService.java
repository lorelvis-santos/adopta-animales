package com.stackmasters.adoptaanimales.service;

import com.stackmasters.adoptaanimales.model.SolicitudAdopcion;
import com.stackmasters.adoptaanimales.model.SolicitudAdopcion.EstadoSolicitud;
import com.stackmasters.adoptaanimales.dto.CrearAdoptanteDTO;
import com.stackmasters.adoptaanimales.dto.CitaDTO;
import com.stackmasters.adoptaanimales.exception.*;
import com.stackmasters.adoptaanimales.model.Cita;
import com.stackmasters.adoptaanimales.model.Cita.EstadoCita;
import com.stackmasters.adoptaanimales.dto.FiltroSolicitudDTO;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Lorelvis Santos
 * 
 * Servicio encargado de gestionar el ciclo de vida de las solicitudes de adopción,
 * incluyendo su creación, consulta, actualización de estado y administración de la
 * cita asociada. Este servicio constituye la capa principal de reglas de negocio
 * relacionadas con el proceso de adopción.
 *
 * Flujo típico administrado por este servicio:
 * 1. Crear solicitud de adopción (adoptante nuevo o existente).
 * 2. Validar disponibilidad de la mascota.
 * 3. Cambiar estados de la solicitud (pendiente, aprobada, rechazada).
 * 4. Programar una cita para solicitudes aprobadas.
 * 5. Actualizar el estado de la cita (pendiente, confirmada, realizada, cancelada).
 * 6. Listar solicitudes según filtros aplicados por el administrador.
 */
public interface SolicitudService {
    /**
     * Crea una nueva solicitud de adopción para una mascota. 
     * Permite registrar simultáneamente los datos del adoptante cuando aún
     * no existe en el sistema.
     *
     * Reglas principales:
     * - La mascota debe estar disponible.
     * - No puede existir una solicitud activa previa para la misma mascota.
     * - Los datos del adoptante deben ser válidos.
     *
     * @param mascotaId ID de la mascota a solicitar.
     * @param adoptante Datos del adoptante enviados desde el formulario.
     * @return La solicitud creada y persistida.
     * @throws DatosInvalidosException Si faltan datos o no cumplen validaciones básicas.
     * @throws MascotaNoDisponibleException Si la mascota ya no está disponible para adoptar.
     * @throws YaExisteSolicitudActivaException Si ya existe una solicitud activa que impide continuar.
     */
    SolicitudAdopcion crear(int mascotaId, CrearAdoptanteDTO adoptante, LocalDateTime fechaCita) 
        throws DatosInvalidosException, MascotaNoDisponibleException, YaExisteSolicitudActivaException;
    
    /**
     * Obtiene una solicitud por su ID.
     *
     * @param solicitudId ID de la solicitud.
     * @return La solicitud correspondiente o null si no existe.
     */
    SolicitudAdopcion obtener(int solicitudId);
    
    /**
     * Actualiza el estado de una solicitud de adopción.
     * Estados permitidos: PENDIENTE, APROBADA, RECHAZADA.
     *
     * Cuando el estado es RECHAZADA, puede incluirse un motivo descriptivo.
     *
     * @param solicitudId ID de la solicitud a actualizar.
     * @param nuevoEstado Estado destino.
     * @param motivoRechazo Mensaje opcional en caso de rechazo.
     * @param cita Fecha y hora de la visita pautada
     * @return true si el estado fue actualizado; false si no hubo cambios.
     * @throws DatosInvalidosException Si la transición de estado no es válida.
     */
    boolean actualizarEstado(int solicitudId, EstadoSolicitud nuevoEstado, String motivoRechazo, LocalDateTime cita)
        throws DatosInvalidosException;
    
    /**
     * Eliminar solicitud
     *
     * @param solicitudId ID de la solicitud a actualizar.
     * @return true si fue eliminada
     */
    boolean eliminar(int solicitudId)
        throws DatosInvalidosException;
    
    /**
     * Lista las solicitudes aplicando filtros opcionales como texto,
     * estado de solicitud, estado de cita o rango de fechas.
     *
     * El servicio solo aplicará los criterios que no sean null.
     *
     * @param filtro Conjunto de criterios opcionales.
     * @return Lista de solicitudes que coinciden con el filtro.
     */
    List<SolicitudAdopcion> listar(FiltroSolicitudDTO filtro);
    
    /**
     * Obtiene la cantidad de solicitudes
     * 
     * @return Cantidad entera de solicitudes
     */
    int totalSolicitudes();
    
    /**
     * Obtiene la cantidad de solicitudes por estado.
     * 
     * @param estado Estado de la solicitud
     * @return Cantidad entera de solicitudes en el estado consultado
     */
    int totalSolicitudesPorEstado(EstadoSolicitud estado);
}
