package com.stackmasters.adoptaanimales.view;

import com.stackmasters.adoptaanimales.model.Mascota;
import com.stackmasters.adoptaanimales.model.SolicitudAdopcion;
import com.stackmasters.adoptaanimales.router.VistaNavegable;
import java.util.List;

/**
 *
 * @author Lorelvis Santos
 */
public interface DashboardSolicitudesView extends VistaNavegable, VistaConAlertas {
    /**
     * Muestra las estadísticas generales de las mascotas.
     * (total de mascotas, mascotas en albergue y adoptadas)
     */
    void mostrarEstadisticas(int totalSolicitudes, int totalSolicitudesPendientes, int SolicitudesResueltas);
    
    /**
     * Carga la tabla de animales en el dashboard.
     * Cada fila debe permitir editar y eliminar.
     * 
     * @param mascotas Lista de mascotas a mostrar en la tabla
     */
    void cargarTablaSolicitudes(List<SolicitudAdopcion> solicitudes);
    
    /**
     * Método para mostrar un spinner de carga en el UI.
     * 
     * @param cargando True si está cargando, false si no
     */
    void setCargando(boolean cargando);
    
    /**
     * Método para agregar la accion al evento de abrir el formularioCrear.
     * 
     * @param accion Callback a ejecutar
     */
    void onFormularioCrear(Runnable accion);
    
    /**
     * Método para mostrar un mensaje con información o error.
     * 
     * @param mensaje El mensaje a mostrar.
     * @param error True si el mensaje es un error, false si es información.
     */
    void mostrarMensaje(String mensaje, boolean error);
}
