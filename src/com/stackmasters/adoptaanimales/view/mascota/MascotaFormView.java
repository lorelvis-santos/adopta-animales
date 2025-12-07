package com.stackmasters.adoptaanimales.view.mascota;

import com.stackmasters.adoptaanimales.dto.ActualizarMascotaDTO;
import com.stackmasters.adoptaanimales.dto.CrearMascotaDTO;
import com.stackmasters.adoptaanimales.model.Mascota;

/**
 *
 * @author Lorelvis Santos
 */
public interface MascotaFormView {
    // Prepara el formulario visualmente
    void setModoEdicion(boolean esEdicion);
    
    // Carga los datos en los campos (para cuando vas a editar)
    void setDatosFormulario(Mascota mascota);
    
    // Limpia todo (para cuando vas a crear)
    void limpiarFormulario();

    // Obtención de datos según el caso
    CrearMascotaDTO getCrearMascotaDTO(Integer albergueId); 
    
    ActualizarMascotaDTO getActualizarMascotaDTO();

    // Gestión de eventos y feedback
    void onAccionPrincipal(Runnable accion);
    void onCancelar(Runnable accion);
    void mostrarMensaje(String msg, boolean error);
    void setCargando(boolean activo);
}
