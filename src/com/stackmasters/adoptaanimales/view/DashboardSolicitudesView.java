package com.stackmasters.adoptaanimales.view;

import com.stackmasters.adoptaanimales.model.Mascota;
import com.stackmasters.adoptaanimales.model.SolicitudAdopcion;
import com.stackmasters.adoptaanimales.router.VistaNavegable;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author Lorelvis Santos
 */
public interface DashboardSolicitudesView extends VistaNavegable, VistaConAlertas {
    /**
     * Muestra las estadísticas generales de las mascotas.
     * (total de solicitudes pendientes, aprobadas y rechazadas/canceladas)
     */
    public void mostrarEstadisticas(int totalSolicitudes, int solicitudesPendientes, int solicitudesAprobadas, int solicitudesRechazadasYCanceladas);
    
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
     * Método para agregar la accion al evento de hacer click en crear.
     * 
     * @param accion Callback a ejecutar
     */
    void onCrear(Runnable accion);
    
    /**
     * Método para agregar la accion al evento de hacer click en editar.
     * 
     * @param accion Callback a ejecutar
     */
    void onEditar(Consumer<Integer> accion);
    
    /**
     * Método para mostrar un mensaje con información o error.
     * 
     * @param mensaje El mensaje a mostrar.
     * @param error True si el mensaje es un error, false si es información.
     */
    void mostrarMensaje(String mensaje, boolean error);
}
