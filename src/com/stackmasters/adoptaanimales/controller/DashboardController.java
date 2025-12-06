package com.stackmasters.adoptaanimales.controller;

import com.stackmasters.adoptaanimales.router.Router;
import com.stackmasters.adoptaanimales.view.DashboardView;

/**
 *
 * @author Lorelvis Santos
 */
public class DashboardController {
    public DashboardController(DashboardView vista, Router router) {
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
        System.out.println("Cerrando sesion");
    }
}
