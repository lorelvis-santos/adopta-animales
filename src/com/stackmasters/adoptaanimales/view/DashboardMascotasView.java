package com.stackmasters.adoptaanimales.view;

import com.stackmasters.adoptaanimales.model.Mascota;
import com.stackmasters.adoptaanimales.router.VistaNavegable;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author Lorelvis Santos
 */
public interface DashboardMascotasView extends VistaNavegable, VistaConAlertas {
    /**
     * Muestra las estadísticas generales de las mascotas.
     * (total de mascotas, mascotas en albergue y adoptadas)
     */
    void mostrarEstadisticas(int totalMascotas, int totalMascotasEnAlbergue, int totalMascotasAdoptadas);
    
    /**
     * Carga la tabla de animales en el dashboard.
     * Cada fila debe permitir editar y eliminar.
     * 
     * @param mascotas Lista de mascotas a mostrar en la tabla
     */
    void cargarTablaMascotas(List<Mascota> mascotas);
    
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
