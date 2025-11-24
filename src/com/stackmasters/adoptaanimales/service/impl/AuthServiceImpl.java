package com.stackmasters.adoptaanimales.service.impl;

import com.stackmasters.adoptaanimales.service.AuthService;
import com.stackmasters.adoptaanimales.model.auth.Sesion;
import com.stackmasters.adoptaanimales.dto.LoginDTO;
import com.stackmasters.adoptaanimales.exception.*;
import com.stackmasters.adoptaanimales.repository.AdoptanteRepository;
import com.stackmasters.adoptaanimales.repository.AdminAlbergueRepository;
import com.stackmasters.adoptaanimales.repository.AlbergueRepository;
import com.stackmasters.adoptaanimales.repository.VeterinariaRepository;

/**
 *
 * @author Lorelvis 
 * 
 * Implementación del servicio de autenticación.
 * Esta clase contiene la lógica que valida las credenciales del usuario,
 * determina su rol y construye la Sesion correspondiente.
 *
 * También administra el cierre de sesión mediante el gestor de sesiones.
 */
public class AuthServiceImpl implements AuthService {
    private Adoptante
    
    public AuthServiceImpl() {
        
    }
    
    /**
     * Procesa el inicio de sesión utilizando la información del LoginDTO.
     * Este método debe:
     * 1. Verificar el correo en los repositorios correspondientes.
     * 2. Validar la contraseña.
     * 3. Resolver el rol del usuario (si no se indicó).
     * 4. Crear y devolver una Sesion con el rol y los datos básicos.
     *
     * @param dto Datos de inicio de sesión.
     * @return Sesion activa del usuario.
     * @throws CredencialesInvalidasException Si el correo no existe para el rol elegido
     *                                        o la contraseña no coincide.
     * @throws SeleccioneRolException Si el correo pertenece a más de un rol
     *                                y no se especificó uno en el DTO.
     */
    @Override
    public Sesion iniciarSesion(LoginDTO dto) throws CredencialesInvalidasException, SeleccioneRolException {
        
        
        return null; // Implementación pendiente
    }
    
    /**
     * Cierra la sesión activa.
     * Este método delega al gestor de sesiones para limpiar la información
     * almacenada del usuario actualmente autenticado.
     */
    @Override
    public void cerrarSesion() {
        // Implementación pendiente
    }
}
