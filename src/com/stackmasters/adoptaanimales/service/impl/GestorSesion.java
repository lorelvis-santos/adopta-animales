package com.stackmasters.adoptaanimales.service.impl;

import com.stackmasters.adoptaanimales.model.auth.Sesion;

/**
 *
 * @author Lorelvis 
 * 
 * Clase encargada de manejar la sesión activa del sistema.
 * 
 * Funciona como un gestor global donde los servicios y controladores
 * pueden consultar o modificar la sesión actual.
 *
 * Esta clase almacena una única instancia de Sesion en memoria,
 * correspondiente al usuario autenticado en ese momento.
 */
public class GestorSesion {
    private static Sesion sesion = null;
    
    /**
     * Establece una nueva sesión como activa.
     *
     * @param nuevaSesion Sesión creada luego de un inicio de sesión exitoso.
     */
    public static void establecer(Sesion nuevaSesion) {
        sesion = nuevaSesion;
    }
    
    /**
     * Obtiene la sesión actualmente activa.
     *
     * @return Sesión activa o null si no hay un usuario autenticado.
     */
    public static Sesion obtener() {
        return sesion;
    }
    
    /**
     * Elimina la sesión activa y deja el sistema sin usuario autenticado.
     */

    public static void limpiar() {
        sesion = null;
    }
}
