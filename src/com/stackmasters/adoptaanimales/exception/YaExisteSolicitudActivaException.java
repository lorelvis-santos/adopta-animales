package com.stackmasters.adoptaanimales.exception;

/**
 *
 * @author Lorelvis Santos
 */
public class YaExisteSolicitudActivaException extends Exception {
    public YaExisteSolicitudActivaException() {
        super("Ya existe una solicitud activa");
    }
}
