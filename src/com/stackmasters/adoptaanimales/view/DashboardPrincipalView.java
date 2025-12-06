package com.stackmasters.adoptaanimales.view;

import com.stackmasters.adoptaanimales.router.VistaNavegable;
import com.stackmasters.adoptaanimales.model.Mascota;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author Lorelvis Santos
 * 
 * Interfaz para el Dashboard principal de la aplicación.
 * 
 */
public interface DashboardPrincipalView extends VistaNavegable {
    void onIrHome(Runnable accion);
    void onIrMascotas(Runnable accion);
    void onIrSolicitudes(Runnable accion);
    // void onCerrarSesion(Runnable accion);
    
    void registrarSubvista(String nombre, JPanel vista);
    void mostrarSubvista(String nombre);
}
