package com.stackmasters.adoptaanimales.controller;

import com.stackmasters.adoptaanimales.view.AuthView;
import com.stackmasters.adoptaanimales.router.Router;
import com.stackmasters.adoptaanimales.dto.LoginDTO;
import com.stackmasters.adoptaanimales.service.AuthService;
import com.stackmasters.adoptaanimales.router.Ruta;
import javax.swing.SwingWorker;

public class AuthController {
    public AuthController(AuthView vista, AuthService auth, Router router) {
        vista.onLogin(() -> {
            var dto = new LoginDTO(vista.getCorreo(), vista.getContraseña(), vista.getRolSeleccionado());
            
            vista.setCargando(true);
            
            new SwingWorker<Void, Void>() {
                private Exception error;
                
                @Override protected Void doInBackground() {
                    try { 
                        auth.iniciarSesion(dto);
                    } catch (Exception e) {
                        error = e; 
                    }
                    
                    return null;
                }
                
                @Override protected void done() {
                    vista.setCargando(false);
                    vista.limpiarContraseña();
                    
                    if (error != null) {
                        vista.mostrarMensaje(error.getMessage());
                        return; 
                    }
                    
                    router.navegar(Ruta.PRINCIPAL, "Bienvenido, esto es una prueba");
                }
            }.execute();
        });
    }
}
