package com.stackmasters.adoptaanimales.exception;

/**
 *
 * @author Lorelvis Santos
 */
public class SolicitudNoAprobadaException extends Exception {
    public SolicitudNoAprobadaException() {
        super("No puedes programar una cita para una solicitud rechazada");
    }
    
    public SolicitudNoAprobadaException(String mensaje) {
        super(mensaje);
    }

    
}
