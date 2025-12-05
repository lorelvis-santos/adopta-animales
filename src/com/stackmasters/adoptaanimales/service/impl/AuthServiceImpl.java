package com.stackmasters.adoptaanimales.service.impl;

import com.stackmasters.adoptaanimales.service.AuthService;
import com.stackmasters.adoptaanimales.model.auth.Sesion;
import com.stackmasters.adoptaanimales.model.auth.Rol;
import com.stackmasters.adoptaanimales.dto.LoginDTO;
import com.stackmasters.adoptaanimales.exception.*;
import com.stackmasters.adoptaanimales.repository.AdoptanteRepository;
import com.stackmasters.adoptaanimales.repository.AdminAlbergueRepository;
import com.stackmasters.adoptaanimales.security.PasswordHasher;
import com.stackmasters.adoptaanimales.model.AdminAlbergue;
import java.sql.SQLException;
/**
 *
 * @author Lorelvis Santos
 * 
 * Implementación del servicio de autenticación.
 * Esta clase contiene la lógica que valida las credenciales del usuario,
 * determina su rol y construye la Sesion correspondiente.
 *
 * También administra el cierre de sesión mediante el gestor de sesiones.
 */
public class AuthServiceImpl implements AuthService {
    private AdoptanteRepository adoptanteRepo;
    private AdminAlbergueRepository adminAlbergueRepo;
    private final PasswordHasher hasher;
    
    public AuthServiceImpl(
        AdoptanteRepository adoptanteRepo, 
        AdminAlbergueRepository adminAlbergueRepo,
        PasswordHasher hasher
    ) {
        this.adoptanteRepo = adoptanteRepo;
        this.adminAlbergueRepo = adminAlbergueRepo;
        this.hasher = hasher;
    }
    
    /**
     * Procesa el inicio de sesión utilizando la información del LoginDTO.
     * Este método debe:
     * 1. Validar campos.
     * 2. Resolver el rol del usuario (si no se indicó).
     * 3. Crear y devolver una Sesion con el rol y los datos básicos.
     *
     * @param dto Datos de inicio de sesión.
     * @return Sesion activa del usuario.
     * @throws CredencialesInvalidasException Si el correo no existe para el rol elegido
     *                                        o la contraseña no coincide.
     * @throws SeleccioneRolException Si el correo pertenece a más de un rol
     *                                y no se especificó uno en el DTO.
     */
    @Override
    public Sesion iniciarSesion(LoginDTO dto) throws CredencialesInvalidasException, SeleccioneRolException, DatosInvalidosException {
        // Validamos campos
        if (dto == null || dto.getCorreo() == null || dto.getCorreo().isBlank()
                || dto.getContraseña() == null || dto.getContraseña().isBlank()) {
            throw new DatosInvalidosException("Ambos campos son obligatorios");
        }
        
        if (dto.getContraseña().length() < 8) {
            throw new DatosInvalidosException("La contraseña debe tener mínimo 8 carácteres");
        }
        
        String correo = dto.getCorreo();
        String contraseña = dto.getContraseña();
        Rol rol = dto.getRolPreferido();
        
        // Si hay un rol seleccionado, llamamos el login correspondiente
        if (rol != null) {
            return switch (rol) {
                case ADMIN_ALBERGUE -> loginAdmin(correo, contraseña);
                default -> throw new CredencialesInvalidasException("Rol no soportado para autenticación");
            };
        }
        
        // Si no tiene rol seleccionado, hacemos la detección automáticamente
        AdminAlbergue admin = buscarAdminSeguro(correo);
        
        if (admin == null) {
            throw new CredencialesInvalidasException("Cuenta inexistente");
        }
        
        return loginAdmin(correo, contraseña);
    }
    
    /**
     * Cierra la sesión activa.
     * Este método delega al gestor de sesiones para limpiar la información
     * almacenada del usuario actualmente autenticado.
     */
    @Override
    public void cerrarSesion() {
        GestorSesion.limpiar();
    }
    
    // Métodos privados...    
    private Sesion loginAdmin(String correo, String contraseña) throws CredencialesInvalidasException {
        AdminAlbergue admin = buscarAdminSeguro(correo);
        
        // Verificamos que exista el adoptante
        if (admin == null) {
            throw new CredencialesInvalidasException("Cuenta inexistente");
        }
        
        // Comprobamos contraseñas
        if (!hasher.verificar(contraseña, admin.getContraseña())) {
            throw new CredencialesInvalidasException("Contraseña incorrecta");
        }
        
        // Creamos la nueva sesión
        Sesion sesion = new Sesion(
            Rol.ADMIN_ALBERGUE,
            admin.getIdAdmin(),
            admin.getNombre() + " " + admin.getApellido()
        );
        
        GestorSesion.establecer(sesion);
        
        return sesion;
    }
    
   @Override
    public void forzarCambioContraseña(String correo, String nuevaContraseña) throws DatosInvalidosException {
        // 1. Validar parámetros
    if (correo == null || correo.isBlank()) {
        throw new DatosInvalidosException("El correo es obligatorio");
    }
    
    if (nuevaContraseña == null || nuevaContraseña.isBlank()) {
        throw new DatosInvalidosException("La nueva contraseña es obligatoria");
    }
    
    if (nuevaContraseña.length() < 8) {
        throw new DatosInvalidosException("La contraseña debe tener mínimo 8 caracteres");
    }
    
    // Buscar admin por correo
    AdminAlbergue admin = buscarAdminSeguro(correo);
    if (admin == null) {
        throw new DatosInvalidosException("No existe un usuario admin con ese correo");
    }
    
    // Hashear nueva contraseña
    String nuevaHasheada = hasher.hash(nuevaContraseña);
    
     // Actualizar en repositorio
        boolean actualizado = adminAlbergueRepo.actualizarContraseña(correo, nuevaHasheada);
        if (!actualizado) {
            throw new DatosInvalidosException("No se pudo actualizar la contraseña");
        }
    }
    
    private AdminAlbergue buscarAdminSeguro(String correo) {
        try {
            return adminAlbergueRepo.buscarPorCorreo(correo);
        } catch (SQLException e) {
            return null;
        }
    }
}
