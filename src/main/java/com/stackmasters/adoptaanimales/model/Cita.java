package main.java.com.stackmasters.adoptaanimales.model;

import java.time.LocalDateTime;

/**
 *
 * @author Bianca Parra
 */
public class Cita {
    
    private int idCita;
    private LocalDateTime fechaHora;
    private enum estado{
    Programada, Completada, Cancelada
    }
    private String notas;
    private int solicitudId;
    private int albergueId;

    public Cita() {
    }

    public Cita(int idCita, LocalDateTime fechaHora, String notas, int solicitudId, int albergueId) {
        this.idCita = idCita;
        this.fechaHora = fechaHora;
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
