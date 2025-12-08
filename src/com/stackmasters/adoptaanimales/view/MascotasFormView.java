package com.stackmasters.adoptaanimales.view;

import com.stackmasters.adoptaanimales.dto.ActualizarMascotaDTO;
import com.stackmasters.adoptaanimales.dto.CrearMascotaDTO;
import com.stackmasters.adoptaanimales.model.Mascota;
import com.stackmasters.adoptaanimales.router.VistaNavegable;

/**
 *
 * @author Lorelvis Santos
 */
public interface MascotasFormView extends VistaNavegable, VistaConAlertas {
    // Prepara el formulario visualmente
    void setModoEdicion(boolean esEdicion);
    
    // Carga los datos en los campos
    void setDatosFormulario(Mascota mascota);
    
    // Limpia el formulario
    void limpiarFormulario();
    
    // Para obtener el id de la mascota
    public Integer getIdMascota();

    // Obtención de datos según el caso
    CrearMascotaDTO getCrearMascotaDTO(Integer albergueId); 
    
    ActualizarMascotaDTO getActualizarMascotaDTO();

    // Gestión de eventos y feedback
    void onAccionPrincipal(Runnable accion);
    void onCancelar(Runnable accion);
    void mostrarMensaje(String msg, boolean error);
    void setCargando(boolean activo);
}
