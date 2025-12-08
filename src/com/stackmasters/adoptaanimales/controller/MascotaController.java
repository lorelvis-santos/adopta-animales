package com.stackmasters.adoptaanimales.controller;

import com.stackmasters.adoptaanimales.dto.ActualizarMascotaDTO;
import com.stackmasters.adoptaanimales.dto.CrearMascotaDTO;
import com.stackmasters.adoptaanimales.dto.FiltroMascotaDTO;
import com.stackmasters.adoptaanimales.model.Mascota;
import com.stackmasters.adoptaanimales.router.DashboardRuta;
import com.stackmasters.adoptaanimales.router.Router;
import com.stackmasters.adoptaanimales.router.Ruta;
import com.stackmasters.adoptaanimales.service.MascotaService;
import com.stackmasters.adoptaanimales.service.impl.GestorSesion;
import com.stackmasters.adoptaanimales.view.DashboardMascotasView;
import com.stackmasters.adoptaanimales.view.MascotasFormView;
import java.util.List;
import javax.swing.SwingWorker;

/**
 *
 * @author Lorelvis Santos
 */
public class MascotaController {
    private final DashboardMascotasView dashboard;
    private final MascotasFormView vista;
    private final MascotaService servicio;
    private final Router router;
    
    public MascotaController(DashboardMascotasView dashboard, MascotasFormView vista, MascotaService servicio, Router router) {
        this.dashboard = dashboard;
        this.vista = vista;
        this.servicio = servicio;
        this.router = router;
        
        this.dashboard.onCrear(this::onCrear);
        this.dashboard.onEditar(this::onEditar);
        this.dashboard.onBuscar(this::onBuscar);
        
        this.vista.onAccionPrincipal(this::procesarGuardado);
        this.vista.onCancelar(this::regresarALista);
    }
    
    public void onCrear() {
        router.navegar(Ruta.PRINCIPAL, DashboardRuta.MASCOTAS_CREAR);
    }
    
    public void onEditar(Integer id) {
        router.navegar(Ruta.PRINCIPAL, DashboardRuta.MASCOTAS_EDITAR, id);
    }
    
    public void onBuscar() {
        // Hacer un dto con los parametros de busqueda
        FiltroMascotaDTO filtro = new FiltroMascotaDTO(dashboard.getBusqueda(), dashboard.getEspecie(), null, null, dashboard.getEstadoMascota());
        
        // Hacer la busqueda con SwingWorker y manejo de errores
        vista.setCargando(true);
        
        new SwingWorker<Void, Void>() {
            private List<Mascota> mascotas;
            
            @Override
            protected Void doInBackground() throws Exception {
                mascotas = servicio.buscar(filtro);
                return null;
            }

            @Override
            protected void done() {
                // 1. Quitamos la carga
                vista.setCargando(false);

                try {
                    // 2. Verificamos si hubo errores usando get()
                    get(); // Si hubo una excepción en doInBackground, se relanza aquí envuelta en ExecutionException

                    dashboard.cargarTablaMascotas(mascotas);
                } catch (Exception e) {
                    // 4. ERROR (Capturamos la excepción real del servicio)
                    // e.getCause() nos da la excepción original (ej: SQLException o "Error de conexión")
                    String errorMsg = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
                    dashboard.mostrarMensaje("Error: " + errorMsg, true);
                    e.printStackTrace();
                }
            }
        }.execute();
    }
    
    private void procesarGuardado() {
        // 1. VALIDACIÓN Y PREPARACIÓN DE DATOS (En el Hilo Principal/EDT)
        // Es mejor obtener los DTOs aquí para que si hay error de validación (campos vacíos),
        // salte antes de bloquear la pantalla o crear hilos.

        CrearMascotaDTO crearDto = null;
        ActualizarMascotaDTO actualizarDto = null;

        try {
            if (vista.getIdMascota() == null) {
                // Caso Crear: Obtenemos el ID del usuario actual
                int albergueId = GestorSesion.obtener().getAlbergueId(); 
                crearDto = vista.getCrearMascotaDTO(albergueId); // Esto puede lanzar excepción si faltan datos
            } else {
                // Caso Editar
                actualizarDto = vista.getActualizarMascotaDTO();
            }
        } catch (Exception e) {
            // Si falló la validación (ej: fecha mal formada, campo vacío), mostramos error y NO seguimos.
            vista.mostrarMensaje(e.getMessage(), true);
            return;
        }

        // Variables finales para pasarlas al Worker
        final CrearMascotaDTO dtoParaCrear = crearDto;
        final ActualizarMascotaDTO dtoParaActualizar = actualizarDto;
        final boolean esCreacion = (vista.getIdMascota() == null);

        // 2. ACTIVAR CARGA VISUAL (En la Vista)
        vista.setCargando(true);

        // 3. INICIAR EL SWINGWORKER
        new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                // --- HILO SECUNDARIO (Backgroud) ---
                // Aquí SOLO llamamos al servicio. No tocamos la UI.

                if (esCreacion) {
                    servicio.crear(dtoParaCrear);
                } else {
                    servicio.actualizar(vista.getIdMascota(), dtoParaActualizar);
                }
                return null;
            }

            @Override
            protected void done() {
                // --- HILO PRINCIPAL (EDT) ---
                // Se ejecuta automáticamente al terminar doInBackground

                // 1. Quitamos la carga
                vista.setCargando(false);

                try {
                    // 2. Verificamos si hubo errores usando get()
                    get(); // Si hubo una excepción en doInBackground, se relanza aquí envuelta en ExecutionException

                    // 3. ÉXITO
                    String mensaje = esCreacion ? "Mascota creada con éxito" : "Mascota actualizada con éxito";
                    vista.mostrarMensaje(mensaje, false);
                    regresarALista();

                } catch (Exception e) {
                    // 4. ERROR (Capturamos la excepción real del servicio)
                    // e.getCause() nos da la excepción original (ej: SQLException o "Error de conexión")
                    String errorMsg = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
                    vista.mostrarMensaje("Error: " + errorMsg, true);
                    e.printStackTrace();
                }
            }
        }.execute();
    }
    
    private void regresarALista() {
        router.navegar(Ruta.PRINCIPAL, DashboardRuta.MASCOTAS);
    }
}
