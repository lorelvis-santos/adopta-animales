package com.stackmasters.adoptaanimales.dto;

import com.stackmasters.adoptaanimales.model.SolicitudAdopcion.EstadoSolicitud;
import com.stackmasters.adoptaanimales.model.Cita.CitaAdopcion;
import java.time.LocalDate;

/**
 *
 * @author Lorelvis Santos
 * 
 * DTO para agrupar criterios de búsqueda de solicitudes de adopción.
 * Se usa en el panel general de solicitudes.
 * Todos los campos son opcionales; el servicio aplicará solo los no nulos.
 * 
 */
public class FiltroSolicitudDTO {
    private final String texto; // buscar por nombre de mascota o adoptante
    private final EstadoSolicitud estadoSolicitud;
    private final CitaAdopcion estadoCita;
    private final int mascotaId;
    private final LocalDate fechaDesde;
    private final LocalDate fechaHasta;

    public FiltroSolicitudDTO(String texto, EstadoSolicitud estadoSolicitud, CitaAdopcion estadoCita, int mascotaId, LocalDate fechaDesde, LocalDate fechaHasta) {
        this.texto = texto;
        this.estadoSolicitud = estadoSolicitud;
        this.estadoCita = estadoCita;
        this.mascotaId = mascotaId;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
    }

    public String getTexto() {
        return texto;
    }

    public EstadoSolicitud getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public CitaAdopcion getEstadoCita() {
        return estadoCita;
    }

    public int getMascotaId() {
        return mascotaId;
    }

    public LocalDate getFechaDesde() {
        return fechaDesde;
    }

    public LocalDate getFechaHasta() {
        return fechaHasta;
    }
}
