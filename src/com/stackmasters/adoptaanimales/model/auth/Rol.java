package com.stackmasters.adoptaanimales.model.auth;

/**
 *
 * @author Lorelvis Santos
 * 
 * Enumeración que define los distintos roles que pueden iniciar sesión en el sistema.
 * 
 * Los roles determinan qué vistas, controles y operaciones están disponibles
 * para cada tipo de usuario.
 *
 * ADOPTANTE       → Acceso al catálogo, solicitudes, favoritos.
 * ADMIN_ALBERGUE  → Gestión de publicaciones, mascotas y solicitudes.
 * VETERINARIA     → Registro o actualización del estado sanitario de mascotas.
 * ALBERGUE        → Rol alternativo según tu modelo (si se usa directamente).
 */
public enum Rol {
    ADOPTANTE,
    ADMIN_ALBERGUE,
    VETERINARIA,
    ALBERGUE
}
