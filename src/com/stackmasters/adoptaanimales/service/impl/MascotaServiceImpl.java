/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stackmasters.adoptaanimales.service.impl;

import com.stackmasters.adoptaanimales.service.MascotaService;
import com.stackmasters.adoptaanimales.repository.MascotaRepository;
import com.stackmasters.adoptaanimales.model.Mascota;
import com.stackmasters.adoptaanimales.dto.CrearMascotaDTO;
import com.stackmasters.adoptaanimales.dto.ActualizarMascotaDTO;
import com.stackmasters.adoptaanimales.dto.FiltroMascotaDTO;
import com.stackmasters.adoptaanimales.exception.DatosInvalidosException;
import com.stackmasters.adoptaanimales.model.Mascota.EstadoMascota;
import com.stackmasters.adoptaanimales.model.Mascota.EstadoMascota;



import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;


/**
 * Clase que implementa la lógica de negocio para las mascotas del sistema.
 * Se encarga de:
 *  - Validar datos de entrada (DTOs)
 *  - Aplicar reglas de negocio básicas
 *  - Llamar al repositorio para acceder a la base de datos
 */


/**
 *
 * @author LENOVO
 */

public class MascotaServiceImpl implements MascotaService {

    private final MascotaRepository repo;

    /**
     * Constructor donde inicializamos el repositorio.
     */
    public MascotaServiceImpl() {
        this.repo = new MascotaRepository();
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
       // AGREGAR ALBERGUE_ID AL MODELO (asegúrate que exista setAlbergueId en Mascota.java)
        m.setAlbergueId(dto.getAlbergueId());

        // Estado inicial: la mascota está en el albergue
        m.setEstado(EstadoMascota.EnAlbergue);

        // 10. Guardar en BD usando el repositorio.
        // BaseRepository.insert(String sql, Object... params) devuelve boolean
        boolean insertado = repo.insert(
            "INSERT INTO mascota " +
            "(nombre, raza, especie, sexo, tamaño, peso, fecha_nacimiento, descripcion, esta_vacunado, esta_castrado, estado, albergue_id) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            m.getNombre(),
            m.getRaza(),
            m.getEspecie().toString(),
            m.getSexo().toString(),
            m.getTamaño().toString(),
            m.getPeso(),
            m.getFechaNacimiento() != null ? java.sql.Date.valueOf(m.getFechaNacimiento()) : null,
            m.getDescripcion(),
            m.isEstaVacunado(),
            m.isEstaCastrado(),
            m.getEstado().toString(),
            m.getAlbergueId()  // <- PARÁMETRO AGREGADO AQUÍ
        );

        if (!insertado) {
            // Si insert devuelve false, algo falló al guardar
            throw new DatosInvalidosException("No se pudo guardar la mascota en la base de datos.");
        }

        // Nota: aquí no estamos trayendo el ID generado desde la BD.
        // Si en el futuro necesitan el ID, se podría buscar por algún criterio único.
        // Por ahora, devolvemos el objeto m, que representa la mascota creada.
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
        boolean actualizado = repo.update(
            "UPDATE mascota SET nombre = ?, descripcion = ?, esta_vacunado = ?, esta_castrado = ?, estado = ? WHERE id_mascota = ?",
            dto.getNombre(),
            dto.getDescripcion(),
            dto.getEstaVacunado(),
            dto.getEstaCastrado(),
            dto.getEstado() != null ? dto.getEstado().toString() : null,
            mascotaId
        );

        return actualizado;
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
     */
    @Override
    public List<Mascota> buscar(FiltroMascotaDTO filtro) {

        // Si no hay filtro, puedes decidir:
        //  - devolver todas
        //  - o devolver lista vacía.
        // Aquí devolveremos todas si filtro es null.
        if (filtro == null) {
            return repo.findAll();
        }

        // 1. Consultar todos los registros
        List<Mascota> todas = repo.findAll();
        List<Mascota> filtradas = new ArrayList<>();

        // 2. Recorrer y aplicar filtros opcionales
        for (Mascota m : todas) {

            // Filtro por nombre (contiene)
            if (filtro.getNombre() != null &&
                (m.getNombre() == null || !m.getNombre().contains(filtro.getNombre()))) {
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