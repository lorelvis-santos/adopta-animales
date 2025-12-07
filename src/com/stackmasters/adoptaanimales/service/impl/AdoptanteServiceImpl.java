package com.stackmasters.adoptaanimales.service.impl;

import com.stackmasters.adoptaanimales.dto.RegistroAdoptanteDTO;
import com.stackmasters.adoptaanimales.exception.DatosInvalidosException;
import com.stackmasters.adoptaanimales.exception.EmailYaUsadoException;
import com.stackmasters.adoptaanimales.model.Adoptante;
import com.stackmasters.adoptaanimales.model.RespuestaBD;
import com.stackmasters.adoptaanimales.repository.AdoptanteRepository;
import com.stackmasters.adoptaanimales.service.AdoptanteService;

/**
 *
 * @author Sky L.
 */

public class AdoptanteServiceImpl implements AdoptanteService {
    
    private final AdoptanteRepository repo;

    public AdoptanteServiceImpl(
        AdoptanteRepository repo
    ) {
    this.repo = repo;
} 

    // Registrar Adoptante   
    @Override
    public Adoptante registrar(RegistroAdoptanteDTO dto)
        throws EmailYaUsadoException, DatosInvalidosException {

   // Validar datos obligatorios
    validarDatosRegistro(dto);
    
        Adoptante nuevo = new Adoptante();
        nuevo.setNombre(dto.getNombre());
        nuevo.setApellido(dto.getApellido());
        nuevo.setFechaNacimiento(dto.getFechaNacimiento());
        nuevo.setTelefono(dto.getTelefono());
        nuevo.setDireccion(dto.getDireccion());
        
        //  Insertar en BD
        RespuestaBD insertado = repo.insertAdoptante(nuevo);

        if (!insertado.isOk()) {
            throw new DatosInvalidosException("No se pudo registrar el adoptante.");
        }
        System.out.println(insertado.getId());
        return nuevo;
    }
    
    // Obtener adoptante por id
    @Override
    public Adoptante obtenerPorId(int id) {
        return repo.findById(id);
    }
    
    // Actualizar perfil del adoptante
    @Override
    public boolean actualizarPerfil(Adoptante adoptante)
        throws DatosInvalidosException {

    validarDatosActualizacion(adoptante);

        return repo.updateAdoptante(adoptante, adoptante.getIdAdoptante());
    }
    
    // Validación para registro
    private void validarDatosRegistro(RegistroAdoptanteDTO dto)
         throws DatosInvalidosException {
   // Campos obligatorios
        validarCamposObligatorios(dto.getNombre(), dto.getApellido(), dto.getTelefono(), dto.getDireccion());
        if (dto.getFechaNacimiento() == null) {
            throw new DatosInvalidosException("La fecha de nacimiento es obligatoria.");
        }
    }
    // Validación para actualización
    private void validarDatosActualizacion(Adoptante a)
        throws DatosInvalidosException {
        validarCamposObligatorios(a.getNombre(), a.getApellido(), a.getTelefono(), a.getDireccion());
    }
    // Validación para campos comunes
    private void validarCamposObligatorios(String nombre, String apellido, String telefono, String direccion)
            throws DatosInvalidosException {
        if (nombre == null || nombre.isBlank()) {
            throw new DatosInvalidosException("El nombre es obligatorio.");
        }
        if (apellido == null || apellido.isBlank()) {
            throw new DatosInvalidosException("El apellido es obligatorio.");
        }
        if (telefono == null || telefono.isBlank()) {
            throw new DatosInvalidosException("El teléfono es obligatorio.");
        }
        if (direccion == null || direccion.isBlank()) {
            throw new DatosInvalidosException("La dirección es obligatoria.");
        }
           }
       }