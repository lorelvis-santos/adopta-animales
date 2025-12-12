package com.stackmasters.adoptaanimales.view;

import com.stackmasters.adoptaanimales.router.VistaNavegable;

/**
 *
 * @author Lorelvis Santos
 * 
 * Interfaz para el Dashboard principal de la aplicación.
 * 
 */
public interface DashboardView extends VistaNavegable {
    void onIrInicio(Runnable accion);
    void onIrMascotas(Runnable accion);
    void onIrSolicitudes(Runnable accion);
    void onIrAcercaDe(Runnable accion);
    void onCerrarSesion(Runnable accion);
    void setNombreAdmin(String nombre);
}
