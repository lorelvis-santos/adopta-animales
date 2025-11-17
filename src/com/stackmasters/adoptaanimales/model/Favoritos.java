package com.stackmasters.adoptaanimales.model;

import java.time.LocalDateTime;

/**
 *
 * @author Bianca Parra
 */
public class Favoritos {
    
    private int idFavorito;
    private int adoptanteId;
    private int mascotaId;
    private LocalDateTime fechaAgregado;

    public Favoritos() {
    }

    public Favoritos(int idFavorito, int adoptanteId, int mascotaId, LocalDateTime fechaAgregado) {
        this.idFavorito = idFavorito;
        this.adoptanteId = adoptanteId;
        this.mascotaId = mascotaId;
        this.fechaAgregado = fechaAgregado;
    }

    public int getIdFavorito() {
        return idFavorito;
    }

    public void setIdFavorito(int idFavorito) {
        this.idFavorito = idFavorito;
    }

    public int getAdoptanteId() {
        return adoptanteId;
    }

    public void setAdoptanteId(int adoptanteId) {
        this.adoptanteId = adoptanteId;
    }

    public int getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(int mascotaId) {
        this.mascotaId = mascotaId;
    }

    public LocalDateTime getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(LocalDateTime fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }
    
    
}
