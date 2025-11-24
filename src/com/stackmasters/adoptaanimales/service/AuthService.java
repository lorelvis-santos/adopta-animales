package com.stackmasters.adoptaanimales.service;

import com.stackmasters.adoptaanimales.model.auth.Sesion;
import com.stackmasters.adoptaanimales.dto.LoginDTO;
import com.stackmasters.adoptaanimales.exception.CredencialesInvalidasException;
import com.stackmasters.adoptaanimales.exception.SeleccioneRolException;

/**
 *
 * @author Lorelvis Santos
 */
public interface AuthService {
    /**
     * Inicia sesión con las credenciales indicadas.
     * Si las credenciales no coinciden o si es necesario seleccionar un rol,
     * se lanzan las excepciones correspondientes.
     *
     * @param dto Datos de acceso (correo, contraseña y rol preferido).
     * @return Sesion activa del usuario autenticado.
     * @throws CredencialesInvalidasException Cuando correo o contraseña no son válidos.
     * @throws SeleccioneRolException Cuando el mismo correo existe en más de un rol
     *                                y el usuario no especifica cuál desea usar.
     */
    Sesion iniciarSesion(LoginDTO dto) throws CredencialesInvalidasException, SeleccioneRolException;
    
    /**
     * Cierra la sesión actualmente activa, si existe.
     * Este método limpia cualquier referencia a la sesión registrada.
     */
    void cerrarSesion();
}
