package com.stackmasters.adoptaanimales.dto;

import com.stackmasters.adoptaanimales.model.auth.Rol;

/**
 * Objeto de transferencia que contiene los datos necesarios
 * para iniciar sesión en el sistema.
 *
 * Este DTO encapsula el correo, la contraseña y, opcionalmente,
 * el rol con el que el usuario desea autenticarse.
 * 
 * En caso de que el rol no se especifique, el servicio de autenticación
 * podrá determinarlo automáticamente buscando coincidencias del correo
 * en los distintos tipos de usuario registrados.
 */
public class LoginDTO {

    private final String correo;
    private final String contraseña;
    private final Rol rolPreferido; // Puede ser null

    /**
     * Crea un nuevo DTO con la información necesaria para autenticar al usuario.
     *
     * @param correo       Correo electrónico del usuario.
     * @param contraseña   Contraseña ingresada por el usuario.
     * @param rolPreferido Rol elegido para el inicio de sesión,
     *                     o null si se desea autodetección.
     */
    public LoginDTO(String correo, String contraseña, Rol rolPreferido) {
        this.correo = correo;
        this.contraseña = contraseña;
        this.rolPreferido = rolPreferido;
    }

    /**
     * Obtiene el correo ingresado durante el inicio de sesión.
     *
     * @return Correo electrónico.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Obtiene la contraseña ingresada.
     * La verificación del hash debe realizarse en el servicio de autenticación.
     *
     * @return Contraseña en texto plano (tal como fue ingresada).
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * Obtiene el rol preferido para iniciar sesión.
     * Puede ser null si el usuario no seleccionó uno.
     *
     * @return Rol preferido o null.
     */
    public Rol getRolPreferido() {
        return rolPreferido;
    }
}
