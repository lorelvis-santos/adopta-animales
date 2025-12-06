package com.stackmasters.adoptaanimales.view;

import com.stackmasters.adoptaanimales.model.auth.Rol;
import com.stackmasters.adoptaanimales.router.VistaNavegable;

/**
 *
 * @author Lorelvis Santos
 */
public interface AuthView extends VistaNavegable {
    String getCorreo();
    String getContraseña();
    Rol getRolSeleccionado(); // Puede ser null
    
    void onLogin(Runnable accion);
    void setCargando(boolean valor);
    void mostrarMensaje(String mensaje, boolean error);
    void limpiarCampos();
}
