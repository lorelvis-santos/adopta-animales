package com.stackmasters.adoptaanimales.view;

import com.stackmasters.adoptaanimales.dto.ActualizarSolicitudDTO;
import com.stackmasters.adoptaanimales.dto.CrearAdoptanteDTO;
import com.stackmasters.adoptaanimales.model.Mascota;
import com.stackmasters.adoptaanimales.model.SolicitudAdopcion;
import com.stackmasters.adoptaanimales.router.VistaNavegable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

public interface SolicitudesFormView extends VistaNavegable, VistaConAlertas {
    // Acciones básicas
    void onAccionPrincipal(Runnable accion);
    void onCancelar(Runnable accion);   
    void onCargarSolicitud(Consumer<Integer> accion);
    
    // Carga de datos (Llenar el dropdown de mascotas disponibles)
    void setListaMascotas(List<Mascota> mascotas);
    
    // Obtención de datos
    Mascota getMascotaSeleccionada();
    CrearAdoptanteDTO getDatosAdoptante();
    LocalDateTime getFechaHoraCita();
    
    void setSolicitudParaEditar(SolicitudAdopcion solicitud);
    Integer getIdSolicitud(); // Para saber si estamos creando (null) o editando (ID)
    ActualizarSolicitudDTO getActualizarSolicitudDTO(); // Para obtener los datos a guardar
    
    // Feedback
    void mostrarMensaje(String msg, boolean error);
    void setCargando(boolean activo);
    
    // Prepara el formulario visualmente
    void setModoEdicion(boolean esEdicion);
    void limpiarFormulario();
}