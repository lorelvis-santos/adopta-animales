package com.stackmasters.adoptaanimales.model;

import java.time.LocalDateTime;

/**
 *
 * @author Bianca Parra
 */
public class SolicitudAdopcion {
    
    private String idSolicitud;
    private enum estado{
    Pendiente, Aprobada, Rechazada, Cancelada
    }
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaRespuesta;
    private String motivoRechazo;
    private int adoptanteId;
    private int publicacionId;
    private int adminRespuesta;

    public SolicitudAdopcion() {
    }

    public SolicitudAdopcion(String idSolicitud, LocalDateTime fechaSolicitud, LocalDateTime fechaRespuesta, String motivoRechazo, int adoptanteId, int publicacionId, int adminRespuesta) {
        this.idSolicitud = idSolicitud;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaRespuesta = fechaRespuesta;
        this.motivoRechazo = motivoRechazo;
        this.adoptanteId = adoptanteId;
        this.publicacionId = publicacionId;
        this.adminRespuesta = adminRespuesta;
    }

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public LocalDateTime getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(LocalDateTime fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    public int getAdoptanteId() {
        return adoptanteId;
    }

    public void setAdoptanteId(int adoptanteId) {
        this.adoptanteId = adoptanteId;
    }

    public int getPublicacionId() {
        return publicacionId;
    }

    public void setPublicacionId(int publicacionId) {
        this.publicacionId = publicacionId;
    }

    public int getAdminRespuesta() {
        return adminRespuesta;
    }

    public void setAdminRespuesta(int adminRespuesta) {
        this.adminRespuesta = adminRespuesta;
    }
    
      
}
