package com.stackmasters.adoptaanimales.dto;

import com.stackmasters.adoptaanimales.model.Mascota.EstadoMascota;

/**
 *
 * @author Lorelvis Santos
 */
public class ActualizarMascotaDTO {
    private final String nombre;
    private final String descripcion;
    private final boolean estaVacunado;
    private final boolean estaCastrado;
    private final EstadoMascota estado;

    public ActualizarMascotaDTO(String nombre, String descripcion, boolean estaVacunado, boolean estaCastrado, EstadoMascota estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estaVacunado = estaVacunado;
        this.estaCastrado = estaCastrado;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean getEstaVacunado() {
        return estaVacunado;
    }

    public boolean getEstaCastrado() {
        return estaCastrado;
    }

    public EstadoMascota getEstado() {
        return estado;
    }
}
