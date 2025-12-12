package com.stackmasters.adoptaanimales.model.auth;

/**
 *
 * @author Lorelvis Santos
 * 
 * Representa la sesión activa de un usuario dentro del sistema.
 * 
 * Una sesión contiene el rol con el cual el usuario accedió,
 * su identificador único según su tabla correspondiente
 * (adoptante, administrador de albergue o veterinaria)
 * y un nombre para mostrar en la interfaz gráfica.
 * 
 * Esta clase es inmutable excepto por el campo 'nombre', que se puede
 * modificar si es necesario actualizar la información mostrada.
 */
public class Sesion {
    private final Rol rol;
    private final int id; // id_adoptante / id_admin / id_veterinaria
    private final int albergueId;
    private String nombre; // para mostrar en UI
    
    /**
     * Crea una nueva sesión de usuario.
     *
     * @param rol Rol con el que el usuario inició sesión.
     * @param id Identificador específico del usuario autenticado.
     * @param nombre Nombre a mostrar en la interfaz.
     */
    public Sesion(Rol rol, int id, String nombre, int albergueId) {
        this.rol = rol;
        this.id = id;
        this.nombre = nombre;
        this.albergueId = albergueId;
    }
    
    /**
     * Obtiene el rol asociado a esta sesión.
     *
     * @return Rol del usuario.
     */
    public Rol getRol() { 
        return rol; 
    }
    
    /**
     * Devuelve el identificador específico del usuario autenticado.
     *
     * @return ID según su tabla correspondiente.
     */
    public int getId() {
        return id; 
    }
    
    /**
     * Devuelve el identificador específico del albergue del usuario autenticado.
     *
     * @return ID según su tabla correspondiente.
     */
    public int getAlbergueId() {
        return albergueId; 
    }
    
    /**
     * Obtiene el nombre que se mostrará en la UI.
     *
     * @return Nombre visible.
     */
    public String getNombre() {
        return nombre; 
    }
}
