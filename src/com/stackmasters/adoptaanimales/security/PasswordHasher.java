package com.stackmasters.adoptaanimales.security;

/**
 *
 * @author Lorelvis Santos
 */
public interface PasswordHasher {
    String hash(String contraseña);
    boolean verificar(String contraseñaPlana, String hash);
}
