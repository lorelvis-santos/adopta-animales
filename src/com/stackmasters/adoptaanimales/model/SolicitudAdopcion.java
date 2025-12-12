package com.stackmasters.adoptaanimales.model;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 *
 * @author Bianca Parra
 */
public class SolicitudAdopcion {
    
    private int idSolicitud;
    private LocalDate fechaSolicitud;
    private LocalDate fechaRespuesta;
    private String motivoRechazo;
    private int adoptanteId;
    private EstadoSolicitud estado;
    private int mascotaId;
    private LocalDateTime cita;
    
    //Enum para el estado de la solicitud
    public enum EstadoSolicitud{
        Pendiente("Pendiente"),
        Aprobada("Aprobada"),
        Rechazada("Rechazada"),
        Cancelada("Cancelada");

        private final String db;

        EstadoSolicitud(String db){
        this.db = db;
        }

        public String db(){
        return db;
        }
    }
    
    public enum EstadoCita{
        Programada("Programada"),
        Completada("Completada"),
        Cancelada("Cancelada"),
        Pendiente("Pendiente");

        private final String db;

        EstadoCita(String db){
            this.db = db;
        }

        public String db(){
            return db;
        }
    }

    public SolicitudAdopcion() {
    }

    public SolicitudAdopcion(int idSolicitud, LocalDate fechaSolicitud, LocalDate fechaRespuesta, String motivoRechazo, int adoptanteId, EstadoSolicitud estado, int mascotaId, LocalDateTime cita) {
        this.idSolicitud = idSolicitud;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaRespuesta = fechaRespuesta;
        this.motivoRechazo = motivoRechazo;
        this.adoptanteId = adoptanteId;
        this.estado = estado;
        this.mascotaId = mascotaId;
        this.cita = cita;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public LocalDate getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(LocalDate fechaRespuesta) {
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

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    public int getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(int mascotaId) {
        this.mascotaId = mascotaId;
    }
    
    public LocalDateTime getCita() {
        return cita;
    }
    
    public void setCita(LocalDateTime cita) {
        this.cita = cita;
    }
}
