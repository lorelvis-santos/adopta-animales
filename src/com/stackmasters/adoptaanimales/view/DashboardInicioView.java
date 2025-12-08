package com.stackmasters.adoptaanimales.view;

import com.stackmasters.adoptaanimales.model.Mascota;
import com.stackmasters.adoptaanimales.router.VistaNavegable;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author Lorelvis Santos
 * 
 * Interfaz para el dashboard de inicio de la aplicación.
 * Contendrá las tarjetas de estadísticas, la tabla con solicitudes y las últimas actividades.
 */
public interface DashboardInicioView extends VistaNavegable, VistaConAlertas {
    /**
     * Muestra las estadísticas generales de la aplicación.
     * (total de mascotas, solicitudes pendientes, mascotas adoptadas)
     */
    void mostrarEstadisticas(int totalMascotas, int totalSolicitudes, int solicitudesPendientes, int mascotasAdoptadas);
    
    /**
     * Carga la tabla de animales en el dashboard.
     * Cada fila debe permitir editar y eliminar.
     * 
     * @param mascotas Lista de mascotas a mostrar en la tabla
     */
    void cargarTablaMascotas(List<Mascota> mascotas);
    
    /**
     * Muestra las últimas actividades realizadas en la aplicación.
     * 
     * @param actividades Lista de actividades recientes
     * 
     * to do: preparar una tabla en la bd que guarde el tipo de evento y fecha.
     */
    void mostrarActividadesRecientes(List<String> actividades);
    
    /**
     * Método para mostrar un spinner de carga en el UI.
     * 
     * @param cargando True si está cargando, false si no
     */
    void setCargando(boolean cargando);
    
    /**
     * Método para agregar la accion al evento de hacer click en editar.
     * 
     * @param accion Callback a ejecutar
     */
    void onEditar(Consumer<Integer> accion);
}
