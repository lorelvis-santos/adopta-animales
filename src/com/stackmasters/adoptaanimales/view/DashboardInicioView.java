package com.stackmasters.adoptaanimales.view;

import com.stackmasters.adoptaanimales.model.Mascota;
import com.stackmasters.adoptaanimales.router.VistaNavegable;
import java.util.List;

/**
 *
 * @author Lorelvis Santos
 * 
 * Interfaz para el dashboard de inicio de la aplicación.
 * Contendrá las tarjetas de estadísticas, la tabla con solicitudes y las últimas actividades.
 */
public interface DashboardInicioView extends VistaNavegable {
    /**
     * Muestra las estadísticas generales de la aplicación.
     * (total de mascotas, solicitudes pendientes, mascotas adoptadas)
     */
    void mostrarEstadisticas(int totalMascotas, int solicitudesPendientes, int mascotasAdoptadas);
    
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
    
}
