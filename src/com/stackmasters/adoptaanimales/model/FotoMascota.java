package com.stackmasters.adoptaanimales.model;

/**
 *
 * @author Bianca Parra
 */
public class FotoMascota {
    
    private int idFotoMascota;
    private int publicacionId;
    private String urlFoto;
    private boolean esPrincipal;

    public FotoMascota() {
    }

    public FotoMascota(int idFotoMascota, int publicacionId, String urlFoto, boolean esPrincipal) {
        this.idFotoMascota = idFotoMascota;
        this.publicacionId = publicacionId;
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
        return publicacionId;
    }

    public void setPublicacionId(int publicacionId) {
        this.publicacionId = publicacionId;
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
