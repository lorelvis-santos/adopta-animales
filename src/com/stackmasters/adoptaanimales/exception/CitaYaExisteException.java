package com.stackmasters.adoptaanimales.exception;

/**
 *
 * @author Lorelvis Santos
 */
public class CitaYaExisteException extends Exception {
    public CitaYaExisteException() {
        super("Ya existe una cita para esa solicitud");
    }
    
    // Constructor para mensaje personalizado
    public CitaYaExisteException(String mensaje) {
        super(mensaje);
    }
}
