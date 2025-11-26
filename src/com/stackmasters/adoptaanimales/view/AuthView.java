package com.stackmasters.adoptaanimales.view;

import com.stackmasters.adoptaanimales.model.auth.Rol;

/**
 *
 * @author Lorelvis Santos
 */
public interface AuthView {
    String getCorreo();
    String getContraseña();
    Rol getRolSeleccionado(); // Puede ser null
    
    void onLogin(Runnable accion);
    void setCargando(boolean valor);
    void limpiarContraseña();
    void mostrarMensaje(String mensaje);
}
