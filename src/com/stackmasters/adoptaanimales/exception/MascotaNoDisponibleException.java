
package com.stackmasters.adoptaanimales.exception;

/**
 *
 * @author Lorelvis Santos
 */
public class MascotaNoDisponibleException extends Exception {
    public MascotaNoDisponibleException() {
        super("La mascota no está disponible para adoptar");
    }
    
     public MascotaNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
