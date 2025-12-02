package com.stackmasters.adoptaanimales.security;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Lorelvis Santos
 */
public class BCryptPasswordHasher implements PasswordHasher {
    @Override
    public String hash(String contraseña) {
        return BCrypt.hashpw(contraseña, BCrypt.gensalt(12));
    }
    
    @Override
    public boolean verificar(String contraseñaPlana, String hash) {
        return BCrypt.checkpw(contraseñaPlana, hash);
    }
}
