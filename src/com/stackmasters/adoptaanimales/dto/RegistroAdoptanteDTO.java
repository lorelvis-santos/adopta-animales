package com.stackmasters.adoptaanimales.dto;

import java.time.LocalDate;

/**
 *
 * @author Lorelvis Santos
 */
public class RegistroAdoptanteDTO {
    private final String nombre;
    private final String apellido;
    private final LocalDate fechaNacimiento;
    private final String telefono;
    private final String correo;
    private final String contrasena;
    private final String direccion;
    private final int provinciaId;
    private final int municipioId;
    
    /**
     * Crea un DTO con todos los datos requeridos para el registro.
     *
     * @param nombre          Nombres del adoptante (requerido).
     * @param apellido        Apellidos del adoptante (requerido).
     * @param fechaNacimiento Fecha de nacimiento (requerido).
     * @param telefono        Teléfono de contacto (requerido).
     * @param correo          Correo electrónico (único) (requerido).
     * @param contrasena      Contraseña en texto plano (se hashea en el servicio) (requerido).
     * @param direccion       Dirección (requerido).
     * @param provinciaId     ID de provincia (FK) (requerido).
     * @param municipioId     ID de municipio (FK) (requerido).
     */
    public RegistroAdoptanteDTO(
        String nombre,
        String apellido,
        LocalDate fechaNacimiento,
        String telefono,
        String correo,
        String contrasena,
        String direccion,
        int provinciaId,
        int municipioId
    ) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.correo = correo;
        this.contrasena = contrasena;
        this.direccion = direccion;
        this.provinciaId = provinciaId;
        this.municipioId = municipioId;
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
    
    public String getCorreo() {
        return correo; 
    }
    
    public String getContrasena() { 
        return contrasena; 
    }
    
    public String getDireccion() { 
        return direccion;
    }
    
    public int getProvinciaId() { 
        return provinciaId;
    }
    
    public int getMunicipioId() { 
        return municipioId;
    }
}
