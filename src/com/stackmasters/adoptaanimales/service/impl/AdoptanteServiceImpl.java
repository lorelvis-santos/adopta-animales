package com.stackmasters.adoptaanimales.service.impl;

import com.stackmasters.adoptaanimales.dto.RegistroAdoptanteDTO;
import com.stackmasters.adoptaanimales.exception.DatosInvalidosException;
import com.stackmasters.adoptaanimales.exception.EmailYaUsadoException;
import com.stackmasters.adoptaanimales.model.Adoptante;
import com.stackmasters.adoptaanimales.repository.AdoptanteRepository;
import com.stackmasters.adoptaanimales.service.AdoptanteService;


/**
 *
 * @author Sky L.
 */

public class AdoptanteServiceImpl implements AdoptanteService {
    
    private final AdoptanteRepository repo = new AdoptanteRepository();
    
    // Registrar Adoptante
    
    @Override
    public Adoptante registrar(RegistroAdoptanteDTO dto)
        throws EmailYaUsadoException, DatosInvalidosException {

   // 1. Validar datos obligatorios
    validarDatosRegistro(dto);
    
        Adoptante nuevo = new Adoptante();
        nuevo.setNombre(dto.getNombre());
        nuevo.setApellido(dto.getApellido());
        nuevo.setFechaNacimiento(dto.getFechaNacimiento());
        nuevo.setTelefono(dto.getTelefono());
        nuevo.setDireccion(dto.getDireccion());
        
        // 4. Insertar en BD
        boolean insertado = repo.insertAdoptante(nuevo);

        if (!insertado) {
            throw new DatosInvalidosException("No se pudo registrar el adoptante.");
        }

        return nuevo;
    }
    
    // obtener adoptante por id
    
    @Override
    public Adoptante obtenerPorId(int id) {
        return repo.findById(id);
    }
    
    // actualizar perfil del adoptante
    
    @Override
    public boolean actualizarPerfil(Adoptante adoptante)
        throws DatosInvalidosException {

    validarDatosActualizacion(adoptante);

        return repo.updateAdoptante(adoptante, adoptante.getIdAdoptante());
    }
    
    // metodos de validación
    
     private void validarDatosRegistro(RegistroAdoptanteDTO dto)
            throws DatosInvalidosException {

        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new DatosInvalidosException("El nombre es obligatorio.");
        }

        if (dto.getApellido() == null || dto.getApellido().isBlank()) {
            throw new DatosInvalidosException("El apellido es obligatorio.");
        }

        if (dto.getFechaNacimiento() == null) {
            throw new DatosInvalidosException("La fecha de nacimiento es obligatoria.");
        }

        if (dto.getTelefono() == null || dto.getTelefono().isBlank()) {
            throw new DatosInvalidosException("El teléfono es obligatorio.");
        }

        if (dto.getDireccion() == null || dto.getDireccion().isBlank()) {
            throw new DatosInvalidosException("La dirección es obligatoria.");
        }
    }

    private void validarDatosActualizacion(Adoptante a)
            throws DatosInvalidosException {

        if (a.getNombre() == null || a.getNombre().isBlank()) {
            throw new DatosInvalidosException("El nombre es obligatorio.");
        }

        if (a.getApellido() == null || a.getApellido().isBlank()) {
            throw new DatosInvalidosException("El apellido es obligatorio.");
        }

        if (a.getTelefono() == null || a.getTelefono().isBlank()) {
            throw new DatosInvalidosException("El teléfono es obligatorio.");
        }

        if (a.getDireccion() == null || a.getDireccion().isBlank()) {
            throw new DatosInvalidosException("La dirección es obligatoria.");
        }
    }
}

