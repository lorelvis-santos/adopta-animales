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
   // private final String correo;
    private final String direccion;

    public CrearAdoptanteDTO(String nombre, String apellido, LocalDate fechaNacimiento, String telefono, String correo, String direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
       // this.correo = correo;
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

    // public String getCorreo() {
       // return correo;
    // }

    public String getDireccion() {
        return direccion;
    } 

    public void setApellido(String andújar) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setTelefono(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setNombre(String sky) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
