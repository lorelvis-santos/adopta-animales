package com.stackmasters.adoptaanimales.model;

/**
 *
 * @author Bianca Parra
 */
public class FotoMascota {
    
    private int idFotoMascota;
    private int mascotaId;
    private String urlFoto;
    private boolean esPrincipal;

    public FotoMascota() {
    }

    public FotoMascota(int idFotoMascota, int publicacionId, String urlFoto, boolean esPrincipal) {
        this.idFotoMascota = idFotoMascota;
        this.mascotaId = publicacionId;
        this.urlFoto = urlFoto;
        this.esPrincipal = esPrincipal;
    }

    public int getIdFotoMascota() {
        return idFotoMascota;
    }

    public void setIdFotoMascota(int idFotoMascota) {
        this.idFotoMascota = idFotoMascota;
    }

    public int getPublicacionId() {
        return mascotaId;
    }

    public void setPublicacionId(int publicacionId) {
        this.mascotaId = publicacionId;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public boolean isEsPrincipal() {
        return esPrincipal;
    }

    public void setEsPrincipal(boolean esPrincipal) {
        this.esPrincipal = esPrincipal;
    } 
}
