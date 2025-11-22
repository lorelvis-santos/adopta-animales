package com.stackmasters.adoptaanimales.model;

import java.time.LocalDateTime;

/**
 *
 * @author Bianca Parra
 */
public class Publicacion {
    
    private int idPublicacion;
    private String titulo;
    private String descripcion;
    private int contadorLikes;
    private LocalDateTime fechaPublicacion;
    private EstadoPublicacion estado;
    private int mascotaId;
    private int albergueId;
    private int adminId;
    
    public enum EstadoPublicacion{
    Activa, Pausada, Adoptada
    }

    public Publicacion() {
    }

    public Publicacion(int idPublicacion, String titulo, String descripcion, int contadorLikes, LocalDateTime fechaPublicacion, EstadoPublicacion estadoPublicacion, int mascotaId, int albergueId, int adminId) {
        this.idPublicacion = idPublicacion;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.contadorLikes = contadorLikes;
        this.fechaPublicacion = fechaPublicacion;
        this.estado = estadoPublicacion;
        this.mascotaId = mascotaId;
        this.albergueId = albergueId;
        this.adminId = adminId;
    }

    public int getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getContadorLikes() {
        return contadorLikes;
    }

    public void setContadorLikes(int contadorLikes) {
        this.contadorLikes = contadorLikes;
    }

    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
    
    //Getters y Setters del Enum EstadoPublicacion
    public EstadoPublicacion getEstadoPublicacion(){
        return estado;
    }
    
    public void setEstadoPublicacion(EstadoPublicacion estadoPublicacion){
        this.estado = estadoPublicacion;
    }

    public int getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(int mascotaId) {
        this.mascotaId = mascotaId;
    }

    public int getAlbergueId() {
        return albergueId;
    }

    public void setAlbergueId(int albergueId) {
        this.albergueId = albergueId;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
    
    
}
