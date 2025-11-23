package com.stackmasters.adoptaanimales.model;

import java.time.LocalDate;

/**
 *
 * @author Bianca Parra
 */
public class Veterinaria {
    private int idVeterinaria;
    private String nombre;
    private int provinciaId;
    private int municipioId;
    private String direccion;
    private String contraseña;
    private String correo;
    private String telefono;
    private LocalDate fechaCreacion;

    public Veterinaria() {
    }

    public Veterinaria(int idVeterinaria, String nombre, int provinciaId, int municipioId, String direccion, String contraseña, String correo, String telefono, LocalDate fechaCreacion) {
        this.idVeterinaria = idVeterinaria;
        this.nombre = nombre;
        this.provinciaId = provinciaId;
        this.municipioId = municipioId;
        this.direccion = direccion;
        this.contraseña = contraseña;
        this.correo = correo;
        this.telefono = telefono;
        this.fechaCreacion = fechaCreacion;
    }

    public int getIdVeterinaria() {
        return idVeterinaria;
    }

    public void setIdVeterinaria(int idVeterinaria) {
        this.idVeterinaria = idVeterinaria;
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

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
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

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
         
       
}
