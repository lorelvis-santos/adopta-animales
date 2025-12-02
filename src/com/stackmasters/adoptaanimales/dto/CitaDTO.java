package com.stackmasters.adoptaanimales.dto;

import java.time.LocalDateTime;
import com.stackmasters.adoptaanimales.model.Cita.CitaAdopcion;
import com.stackmasters.adoptaanimales.model.Cita;

/**
 *
 * @author Lorelvis Santos
 */
public class CitaDTO {
    private final LocalDateTime fechaHora;
    private final CitaAdopcion estado;
    private final String notas;
    private final int solicitudId;
    private final int albergueId;

    public CitaDTO(LocalDateTime fechaHora, CitaAdopcion estado, String notas, int solicitudId, int albergueId) {
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.notas = notas;
        this.solicitudId = solicitudId;
        this.albergueId = albergueId;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public CitaAdopcion getEstado() {
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
