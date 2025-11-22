package com.stackmasters.adoptaanimales.model;

import java.time.LocalDateTime;

/**
 *
 * @author Bianca Parra
 */
public class Cita {
    
    private int idCita;
    private LocalDateTime fechaHora;
    private CitaAdopcion cita;
    private String notas;
    private int solicitudId;
    private int albergueId;
    
    //Enum para la cita
    public enum CitaAdopcion{
    Programada, Completada, Cancelada
    }
    
    public Cita() {
    }

    public Cita(int idCita, LocalDateTime fechaHora, CitaAdopcion cita, String notas, int solicitudId, int albergueId) {
        this.idCita = idCita;
        this.fechaHora = fechaHora;
        this.cita = cita;
        this.notas = notas;
        this.solicitudId = solicitudId;
        this.albergueId = albergueId;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
   
    //Getters y Setters para el Enum CitaAdopcion
    public CitaAdopcion getCitaAdopcion(){
        return cita;
    }
    
    public void setCitaAdopcion(CitaAdopcion cita){
        this.cita = cita;
    }
    
    public int getSolicitudId() {
        return solicitudId;
    }

    public void setSolicitudId(int solicitudId) {
        this.solicitudId = solicitudId;
    }

    public int getAlbergueId() {
        return albergueId;
    }

    public void setAlbergueId(int albergueId) {
        this.albergueId = albergueId;
    }
}
