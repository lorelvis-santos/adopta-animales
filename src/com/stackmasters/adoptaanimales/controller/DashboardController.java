package com.stackmasters.adoptaanimales.controller;

import com.stackmasters.adoptaanimales.router.DashboardRuta;
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
        router.navegar(Ruta.PRINCIPAL, DashboardRuta.INICIO);
    }
    
    private void mostrarMascotas() {
        router.navegar(Ruta.PRINCIPAL, DashboardRuta.MASCOTAS);
    }
    
    private void mostrarSolicitudes() {
        router.navegar(Ruta.PRINCIPAL, DashboardRuta.SOLICITUDES);
    }
    
    private void cerrarSesion() {
        authService.cerrarSesion();
        router.navegar(Ruta.AUTENTICACION, new Object[0]);
    }
}
