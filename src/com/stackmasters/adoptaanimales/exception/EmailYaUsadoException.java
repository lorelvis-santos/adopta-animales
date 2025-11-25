package com.stackmasters.adoptaanimales.exception;

/**
 *
 * @author Lorelvis Santos
 */
public class EmailYaUsadoException extends Exception {
    public EmailYaUsadoException() {
        super("El correo ya está en uso");
    }
}
