package com.stackmasters.adoptaanimales.controller;

import com.stackmasters.adoptaanimales.router.Router;
import com.stackmasters.adoptaanimales.router.Ruta;
import com.stackmasters.adoptaanimales.service.AuthService;
import com.stackmasters.adoptaanimales.view.DashboardView;

/**
 *
 * @author Lorelvis Santos
 */
public class DashboardController {
    private DashboardView vista;
    private AuthService authService;
    private Router router;
    
    public DashboardController(DashboardView vista, AuthService authService, Router router) {
        this.vista = vista;
        this.authService = authService;
        this.router = router;
        
        vista.onIrInicio(this::mostrarInicio);
        vista.onIrMascotas(this::mostrarMascotas);
        vista.onIrSolicitudes(this::mostrarSolicitudes);
        vista.onCerrarSesion(this::cerrarSesion);
    }
    
    private void mostrarInicio() {
        System.out.println("Inicio");
    }
    
    private void mostrarMascotas() {
        System.out.println("Mascotas");
    }
    
    private void mostrarSolicitudes() {
        System.out.println("Solicitudes");
    }
    
    private void cerrarSesion() {
        authService.cerrarSesion();
        router.navegar(Ruta.AUTENTICACION, null);
    }
}
