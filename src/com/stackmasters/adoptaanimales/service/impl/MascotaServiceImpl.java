package com.stackmasters.adoptaanimales.service.impl;

import com.stackmasters.adoptaanimales.service.MascotaService;
import com.stackmasters.adoptaanimales.repository.MascotaRepository;
import com.stackmasters.adoptaanimales.model.Mascota;
import com.stackmasters.adoptaanimales.dto.CrearMascotaDTO;
import com.stackmasters.adoptaanimales.dto.ActualizarMascotaDTO;
import com.stackmasters.adoptaanimales.dto.FiltroMascotaDTO;
import com.stackmasters.adoptaanimales.exception.DatosInvalidosException;
import com.stackmasters.adoptaanimales.model.Mascota.EstadoMascota;
import com.stackmasters.adoptaanimales.model.RespuestaBD;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Sky L.
 * 
 * Clase que implementa la lógica de negocio para las mascotas del sistema.
 * Se encarga de:
 *  - Validar datos de entrada (DTOs)
 *  - Aplicar reglas de negocio básicas
 *  - Llamar al repositorio para acceder a la base de datos
 */

public class MascotaServiceImpl implements MascotaService {

    private final MascotaRepository repo;

    /**
     * Constructor donde inicializamos el repositorio.
     */
    public MascotaServiceImpl(MascotaRepository mascotaRepo) {
        this.repo = mascotaRepo;
    }

    /**
     * Crear una nueva mascota, validar datos, y guardarla en la BD.
     */
    @Override
    public Mascota crear(CrearMascotaDTO dto) throws DatosInvalidosException {

        // 1. Validar que el DTO no sea null
        if (dto == null) {
            throw new DatosInvalidosException("No se enviaron datos para crear la mascota.");
        }

        // 2. Validar nombre
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new DatosInvalidosException("El nombre de la mascota es obligatorio.");
        }

        // 3. Validar raza
        if (dto.getRaza() == null || dto.getRaza().isBlank()) {
            throw new DatosInvalidosException("La raza es obligatoria.");
        }

        // 4. Validar especie (es enum, pero podría venir null)
        if (dto.getEspecie() == null) {
            throw new DatosInvalidosException("La especie de la mascota es obligatoria.");
        }

        // 5. Validar sexo (puede ser obligatorio según reglas del sistema)
        if (dto.getSexo() == null) {
            throw new DatosInvalidosException("El sexo de la mascota es obligatorio.");
        }

        // 6. Validar tamaño
        if (dto.getTamaño() == null) {
            throw new DatosInvalidosException("El tamaño de la mascota es obligatorio.");
        }

        // 7. Validar peso (no negativo)
        if (dto.getPeso() < 0) {
            throw new DatosInvalidosException("El peso de la mascota no puede ser negativo.");
        }

        // 8. Validar fecha no futura
        if (dto.getFechaNacimiento() != null &&
            dto.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new DatosInvalidosException("La fecha de nacimiento no puede ser futura.");
        }
        
        // VALIDAR ALBERGUE_ID (NUEVO)
        if (dto.getAlbergueId() == null || dto.getAlbergueId() <= 0) {
            throw new DatosInvalidosException("El ID del albergue es obligatorio.");
        }

        // 9. Construir el modelo Mascota a partir del DTO
        Mascota m = new Mascota();
        m.setNombre(dto.getNombre());
        m.setRaza(dto.getRaza());
        m.setEspecie(dto.getEspecie());
        m.setSexo(dto.getSexo());
        m.setTamaño(dto.getTamaño());
        m.setPeso(dto.getPeso());
        m.setFechaNacimiento(dto.getFechaNacimiento());
        m.setDescripcion(dto.getDescripcion());
        m.setEstaVacunado(dto.getEstaVacunado());
        m.setEstaCastrado(dto.getEstaCastrado());
        m.setAlbergueId(dto.getAlbergueId());

        // Estado inicial: la mascota está en el albergue
        m.setEstado(EstadoMascota.EnAlbergue);

        // Insertar la mascota en la base de datos
        RespuestaBD insertado = repo.insertMascota(m);
           
        // Verificar si el registro fue exitoso
        if (!insertado.isOk()) {
            throw new DatosInvalidosException("No se pudo guardar la mascota en la base de datos.");
        }
        // Asignar el ID generado por la BD al objeto en memoria
        m.setIdMascota(insertado.getId());
        
       // Devolver la mascota ya creada con su ID real
        return m;
    }

    /**
     * Actualizar los datos básicos de una mascota según su ID.
     */
    @Override
    public boolean actualizar(int mascotaId, ActualizarMascotaDTO dto) throws DatosInvalidosException {

        // 1. Validar dto null o mascotaId inválido
        if (dto == null || mascotaId <= 0) {
            throw new DatosInvalidosException("Datos inválidos para actualizar la mascota.");
        }

        // 2. Validar nombre si se usa como obligatorio en actualización
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new DatosInvalidosException("El nombre de la mascota es obligatorio para actualizar.");
        }

        // 3. Ejecutar la actualización usando el repositorio.
        // BaseRepository.update(...) devuelve boolean y maneja SQLException internamente.
        
          //  Obtener la mascota desde la BD por ID 
          Mascota m = repo.findById(mascotaId);
          
          // Verificar que la mascota exista, sino, lanzar excepción.
          if (m == null) {
              throw new DatosInvalidosException("La mascota no existe.");
          } 

        // Modificar los campos de la mascota actual con los del dto.
            m.setNombre(dto.getNombre());
            m.setDescripcion(dto.getDescripcion());
            m.setEstaVacunado(dto.getEstaVacunado());
            m.setEstaCastrado(dto.getEstaCastrado());
            m.setEstado(dto.getEstado());          
            
        // Actualizar con el método updateMascota.
           return repo.updateMascota(m, mascotaId);   
         }

    /**
     * Marcar una mascota como adoptada.
     */
    @Override
    public boolean marcarAdoptada(int mascotaId) {

        // 1. Validar ID
        if (mascotaId <= 0) {
            return false;
        }

        // 2. Cambiar el estado a "Adoptada"
        boolean actualizado = repo.update(
            "UPDATE mascota SET estado = ? WHERE id_mascota = ?",
            EstadoMascota.Adoptada.toString(),
            mascotaId
        );

        return actualizado;
    }

    /**
     * Obtener una mascota por ID.
     */
    @Override
    public Mascota obtenerPorId(int mascotaId) {

        if (mascotaId <= 0) {
            return null;
        }

        return repo.findById(mascotaId);
    }

    /**
     * Buscar mascotas según filtros en memoria.
     * Se obtienen todas de la BD y se filtran en Java.
     * @param filtro
     */
    @Override
    public List<Mascota> buscar(FiltroMascotaDTO filtro) {

        // Si no hay filtro, se devolverán todas.
        if (filtro == null) {
            return repo.findAll();
        }

        // 1. Consultar todos los registros
        List<Mascota> todas = repo.findAll();
        List<Mascota> filtradas = new ArrayList<>();

        // 2. Recorrer y aplicar filtros opcionales
        for (Mascota m : todas) {

            // Filtro por nombre (contiene)
            if (filtro.getNombre() != null && (m.getNombre() == null || !m.getNombre().toLowerCase().contains(filtro.getNombre().toLowerCase()))) {
                    continue;
            }

            // Filtro por especie
            if (filtro.getEspecie() != null &&
                m.getEspecie() != filtro.getEspecie()) {
                continue;
            }

            // Filtro por sexo
            if (filtro.getSexo() != null &&
                m.getSexo() != filtro.getSexo()) {
                continue;
            }

            // Filtro por tamaño
            if (filtro.getTamaño() != null &&
                m.getTamaño() != filtro.getTamaño()) {
                continue;
            }

            // Filtro por estado
            if (filtro.getEstado() != null &&
                m.getEstado() != filtro.getEstado()) {
                continue;
            }

            // Si pasó todos los filtros, se agrega a la lista
            filtradas.add(m);
        }

        return filtradas;
    }
}