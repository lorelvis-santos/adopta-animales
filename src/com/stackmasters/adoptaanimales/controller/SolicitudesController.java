package com.stackmasters.adoptaanimales.controller;

import com.stackmasters.adoptaanimales.dto.ActualizarSolicitudDTO;
import com.stackmasters.adoptaanimales.dto.CrearAdoptanteDTO;
import com.stackmasters.adoptaanimales.dto.FiltroSolicitudDTO;
import com.stackmasters.adoptaanimales.model.Mascota;
import com.stackmasters.adoptaanimales.model.SolicitudAdopcion;
import com.stackmasters.adoptaanimales.router.DashboardRuta;
import com.stackmasters.adoptaanimales.router.Router;
import com.stackmasters.adoptaanimales.router.Ruta;
import com.stackmasters.adoptaanimales.service.SolicitudService;
import com.stackmasters.adoptaanimales.view.DashboardSolicitudesView;
import com.stackmasters.adoptaanimales.view.SolicitudesFormView;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.SwingWorker;

/**
 *
 * @author Lorelvis Santos
 */
public class SolicitudesController {
    private final DashboardSolicitudesView dashboard;
    private final SolicitudesFormView vista;
    private final SolicitudService solicitudService;
    private final Router router;
    
    // Nota: Eliminé MascotaService de aquí porque ahora la Vista lo maneja internamente
    // para llenar su propio ComboBox, como vimos en el paso anterior.
    
    public SolicitudesController(DashboardSolicitudesView dashboard, 
                                 SolicitudesFormView vista, 
                                 SolicitudService solicitudService,
                                 Router router) {
        this.dashboard = dashboard;
        this.vista = vista;
        this.solicitudService = solicitudService;
        this.router = router;
        
        this.dashboard.onCrear(this::onCrear);
        this.dashboard.onEditar(this::onEditar);
        this.dashboard.onBuscar(this::onBuscar);
        
        this.vista.onAccionPrincipal(this::procesarGuardado);
        this.vista.onCancelar(this::regresarALista);
        
        // Conectamos la petición de datos de la vista con el controlador
        this.vista.onCargarSolicitud(this::cargarDatosParaEditar);
    }
    
    public void onCrear() {
        // 1. Navegar a la vista
        router.navegar(Ruta.PRINCIPAL, DashboardRuta.SOLICITUDES_CREAR);
        // La vista se encarga de limpiarse y cargar mascotas en su método 'alMostrar'
    }
    
    public void onEditar(Integer id) {
        router.navegar(Ruta.PRINCIPAL, DashboardRuta.SOLICITUDES_EDITAR, id);
    }
    
    public void onBuscar() {
        // Hacer un dto con los parametros de busqueda
        FiltroSolicitudDTO filtro = new FiltroSolicitudDTO(dashboard.getBusqueda(), dashboard.getEstadoSolicitud(), null, 0, null, null);
        
        // Hacer la busqueda con SwingWorker y manejo de errores
        vista.setCargando(true);
        
        new SwingWorker<Void, Void>() {
            private List<SolicitudAdopcion> solicitudes;
            
            @Override
            protected Void doInBackground() throws Exception {
                solicitudes = solicitudService.listar(filtro);
                return null;
            }

            @Override
            protected void done() {
                // 1. Quitamos la carga
                vista.setCargando(false);

                try {
                    // 2. Verificamos si hubo errores usando get()
                    get(); // Si hubo una excepción en doInBackground, se relanza aquí envuelta en ExecutionException

                    dashboard.cargarTablaSolicitudes(solicitudes);
                } catch (Exception e) {
                    // 4. ERROR (Capturamos la excepción real del servicio)
                    String errorMsg = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
                    dashboard.mostrarMensaje("Error: " + errorMsg, true);
                    e.printStackTrace();
                }
            }
        }.execute();
    }
    
    private void procesarGuardado() {
        // 1. DETECTAR MODO
        Integer idSolicitud = vista.getIdSolicitud();
        boolean esCreacion = (idSolicitud == null);

        // Variables para captura de datos
        Mascota mascota = null;
        CrearAdoptanteDTO adoptanteDto = null;
        LocalDateTime fechaCita = null;
        ActualizarSolicitudDTO actualizarDto = null;

        // 2. EXTRACCIÓN Y VALIDACIÓN (UI Thread)
        try {
            if (esCreacion) {
                // --- CREACIÓN ---
                mascota = vista.getMascotaSeleccionada();
                if (mascota == null) throw new IllegalArgumentException("Seleccione una mascota.");
                
                adoptanteDto = vista.getDatosAdoptante(); 
                fechaCita = vista.getFechaHoraCita();
                
            } else {
                // --- EDICIÓN ---
                // Aquí obtenemos el DTO completo (Estado, Cita, etc.)
                actualizarDto = vista.getActualizarSolicitudDTO();
            }
        } catch (Exception e) {
            vista.mostrarMensaje(e.getMessage(), true);
            return;
        }

        // Variables finales para el Worker
        final boolean modoCrear = esCreacion;
        final Integer idFinal = idSolicitud;
        
        final Mascota mFinal = mascota;
        final CrearAdoptanteDTO aFinal = adoptanteDto;
        final LocalDateTime fFinal = fechaCita;
        final ActualizarSolicitudDTO uFinal = actualizarDto;

        // 3. PROCESO EN SEGUNDO PLANO
        vista.setCargando(true);

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                if (modoCrear) {
                    // CREAR
                    solicitudService.crear(mFinal.getIdMascota(), aFinal, fFinal);
                } else {
                    // ACTUALIZAR
                    solicitudService.actualizarEstado(idFinal, uFinal.getEstado(), "No implementado");
                }
                return null;
            }

            @Override
            protected void done() {
                vista.setCargando(false);
                try {
                    get(); // Verificar errores
                    String msg = modoCrear ? "Solicitud creada con éxito." : "Solicitud actualizada con éxito.";
                    vista.mostrarMensaje(msg, false);
                    regresarALista();
                } catch (Exception e) {
                    String errorMsg = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
                    vista.mostrarMensaje("Error: " + errorMsg, true);
                    e.printStackTrace();
                }
            }
        }.execute();
    }
    
    private void cargarDatosParaEditar(Integer id) {
        vista.setCargando(true);
        
        new SwingWorker<SolicitudAdopcion, Void>() {
            @Override
            protected SolicitudAdopcion doInBackground() throws Exception {
                return solicitudService.obtener(id); 
            }

            @Override
            protected void done() {
                vista.setCargando(false);
                try {
                    SolicitudAdopcion solicitud = get();
                    if (solicitud != null) {
                        vista.setSolicitudParaEditar(solicitud);
                    } else {
                        vista.mostrarMensaje("No se encontró la solicitud.", true);
                        regresarALista();
                    }
                } catch (Exception e) {
                    vista.mostrarMensaje("Error cargando solicitud: " + e.getMessage(), true);
                    e.printStackTrace();
                }
            }
        }.execute();
    }
    
    private void regresarALista() {
        router.navegar(Ruta.PRINCIPAL, DashboardRuta.SOLICITUDES);
    }
}