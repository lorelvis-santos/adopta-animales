package com.stackmasters.adoptaanimales.service;

import com.stackmasters.adoptaanimales.model.auth.Sesion;
import com.stackmasters.adoptaanimales.dto.LoginDTO;
import com.stackmasters.adoptaanimales.exception.*;

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
    Sesion iniciarSesion(LoginDTO dto) throws 
        CredencialesInvalidasException, SeleccioneRolException, DatosInvalidosException;
    
    /**
     * Cierra la sesión actualmente activa, si existe.
     * Este método limpia cualquier referencia a la sesión registrada.
     */
    void cerrarSesion();
    
    /**
     * Obliga al usuario a cambiar su contraseña en el primer inicio de sesión.
     * Este método se utiliza cuando el usuario accede por primera vez con una
     * contraseña temporal o por defecto, y debe establecer una nueva antes de
     * continuar usando el sistema.
     *
     * @param correo Correo electrónico del usuario que debe actualizar su contraseña.
     * @param nuevaContraseña Nueva contraseña que reemplazará la temporal.
     * @throws DatosInvalidosException Si la nueva contraseña es nula, vacía o no cumple
     *                                 con los criterios de seguridad mínimos.
     */
    void forzarCambioContraseña(String correo, String nuevaContraseña)
        throws DatosInvalidosException;

}
