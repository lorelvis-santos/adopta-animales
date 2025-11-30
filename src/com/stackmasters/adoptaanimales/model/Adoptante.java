package com.stackmasters.adoptaanimales.model;

import java.time.LocalDate;

/**
 *
 * @author Bianca Parra
 */
public class Adoptante {
    
    private int idAdoptante;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String direccion;
    private int provinciaId;
    private int municipioId;

    public Adoptante() {
    }

    public Adoptante(int idAdoptante, String nombre, String apellido, LocalDate fechanacimiento, String telefono,  String direccion, int provinciaId, int municipioId) {
        this.idAdoptante = idAdoptante;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechanacimiento;
        this.telefono = telefono;
        this.direccion = direccion;
        this.provinciaId = provinciaId;
        this.municipioId = municipioId;
    }

    public int getIdAdoptante() {
        return idAdoptante;
    }

    public void setIdAdoptante(int idAdoptante) {
        this.idAdoptante = idAdoptante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFechanacimiento() {
        return fechaNacimiento;
    }

    public void setFechanacimiento(LocalDate fechanacimiento) {
        this.fechaNacimiento = fechanacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
    
    
    
}
