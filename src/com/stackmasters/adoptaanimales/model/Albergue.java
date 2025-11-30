package com.stackmasters.adoptaanimales.model;


/**
 *
 * @author Bianca Parra
 */
public class Albergue {
    
    private int idAlbergue;
    private String nombre;
    private int provinciaId;
    private int municipioId;
    private String direccion;
    private String correo;
    private String telefono;

    public Albergue() {
    }
    
    public Albergue(int idAlbergue, String nombre, int provinciaId, int municipioId, String direccion,  String correo, String telefono) {
        this.idAlbergue = idAlbergue;
        this.nombre = nombre;
        this.provinciaId = provinciaId;
        this.municipioId = municipioId;
        this.direccion = direccion;
        this.correo = correo;
        this.telefono = telefono;
    }

    public int getIdAlbergue() {
        return idAlbergue;
    }

    public void setIdAlbergue(int idAlbergue) {
        this.idAlbergue = idAlbergue;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getProvinciaId() {
        return provinciaId;
    }

    public void setProvinciaId(int provinciaId) {
        this.provinciaId = provinciaId;
    }

    public int getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(int municipioId) {
        this.municipioId = municipioId;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
