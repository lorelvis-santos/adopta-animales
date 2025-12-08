package com.stackmasters.adoptaanimales.dto;

import java.time.LocalDate;

/**
 *
 * @author Lorelvis Santos
 */
public class CrearAdoptanteDTO {
    private final String nombre;
    private final String apellido;
    private final LocalDate fechaNacimiento;
    private final String telefono;
    private final String direccion;

    public CrearAdoptanteDTO(String nombre, String apellido, LocalDate fechaNacimiento, String telefono, String direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    } 
    
    public void setNombre(String nombre) {
        setNombre(nombre);
    }

    public void setApellido(String apellido) {
        setApellido(apellido);
    }
    
    public void setTelofono(String telefono) {
        setTelofono(telefono);
    }
}
