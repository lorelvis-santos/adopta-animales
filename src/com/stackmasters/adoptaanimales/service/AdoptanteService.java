package com.stackmasters.adoptaanimales.service;

import com.stackmasters.adoptaanimales.dto.RegistroAdoptanteDTO;
import com.stackmasters.adoptaanimales.exception.DatosInvalidosException;
import com.stackmasters.adoptaanimales.exception.EmailYaUsadoException;
import com.stackmasters.adoptaanimales.model.Adoptante;

/**
 *
 * @author Lorelvis Santos
 */

/**
 * Servicio encargado de gestionar a los adoptantes dentro del sistema.
 *
 * Este servicio maneja operaciones como registrar nuevos usuarios,
 * obtener información de un adoptante existente y actualizar su perfil.
 * Su propósito es validar los datos recibidos, aplicar las reglas de negocio
 * necesarias y delegar la persistencia a los repositorios correspondientes.
 */
public interface AdoptanteService {
    /**
     * Registra un nuevo adoptante utilizando los datos del DTO.
     * Se valida que el correo no esté en uso y que los campos requeridos
     * tengan un formato adecuado. La contraseña debe ser procesada
     * (por ejemplo, convertida en un hash) dentro de la implementación.
     *
     * @param dto Datos necesarios para crear al adoptante.
     * @return El adoptante registrado, incluyendo su identificador generado.
     * @throws EmailYaUsadoException Si el correo ya pertenece a otro usuario.
     * @throws DatosInvalidosException Si algún dato obligatorio es inválido.
     */
    Adoptante registrar(RegistroAdoptanteDTO dto)
        throws EmailYaUsadoException, DatosInvalidosException;

    /**
     * Obtiene un adoptante según su identificador.
     *
     * @param id ID del adoptante.
     * @return El adoptante correspondiente o null si no existe.
     */
    Adoptante obtenerPorId(int id);

    /**
     * Actualiza el perfil de un adoptante.
     * Se valida la información recibida antes de guardarla.
     *
     * @param adoptante Entidad con los datos actualizados.
     * @return true si se aplicaron los cambios; false si no hubo modificaciones.
     * @throws DatosInvalidosException Si los datos proporcionados no son válidos.
     */
    boolean actualizarPerfil(Adoptante adoptante) throws DatosInvalidosException;
}
