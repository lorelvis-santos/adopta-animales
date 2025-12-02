package com.stackmasters.adoptaanimales.dto;

import java.time.LocalDateTime;
import com.stackmasters.adoptaanimales.model.Cita.EstadoCita;

/**
 *
 * @author Lorelvis Santos
 */
public class CitaDTO {
    private final LocalDateTime fechaHora;
    private final EstadoCita estado;
    private final String notas;
    private final int solicitudId;
    private final int albergueId;

    public CitaDTO(LocalDateTime fechaHora, EstadoCita estado, String notas, int solicitudId, int albergueId) {
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.notas = notas;
        this.solicitudId = solicitudId;
        this.albergueId = albergueId;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public String getNotas() {
        return notas;
    }

    public int getSolicitudId() {
        return solicitudId;
    }

    public int getAlbergueId() {
        return albergueId;
    }
}
