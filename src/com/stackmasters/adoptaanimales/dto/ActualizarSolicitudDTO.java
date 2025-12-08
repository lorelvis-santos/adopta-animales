package com.stackmasters.adoptaanimales.dto;

import com.stackmasters.adoptaanimales.model.SolicitudAdopcion.EstadoSolicitud;
import java.time.LocalDateTime;

public class ActualizarSolicitudDTO {
    
    private Integer mascotaId;
    private CrearAdoptanteDTO datosAdoptante;
    private LocalDateTime fechaCita;
    private EstadoSolicitud estado;

    public ActualizarSolicitudDTO() {
    }

    public ActualizarSolicitudDTO(Integer mascotaId, CrearAdoptanteDTO datosAdoptante, LocalDateTime fechaCita, EstadoSolicitud estado) {
        this.mascotaId = mascotaId;
        this.datosAdoptante = datosAdoptante;
        this.fechaCita = fechaCita;
        this.estado = estado;
    }

    public Integer getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(Integer mascotaId) {
        this.mascotaId = mascotaId;
    }

    public CrearAdoptanteDTO getDatosAdoptante() {
        return datosAdoptante;
    }

    public void setDatosAdoptante(CrearAdoptanteDTO datosAdoptante) {
        this.datosAdoptante = datosAdoptante;
    }

    public LocalDateTime getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(LocalDateTime fechaCita) {
        this.fechaCita = fechaCita;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }
}