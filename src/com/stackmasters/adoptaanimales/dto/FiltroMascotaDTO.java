package com.stackmasters.adoptaanimales.dto;

import com.stackmasters.adoptaanimales.model.Mascota.Especie;
import com.stackmasters.adoptaanimales.model.Mascota.Sexo;
import com.stackmasters.adoptaanimales.model.Mascota.Tamaño;
import com.stackmasters.adoptaanimales.model.Mascota.EstadoMascota;

/**
 *
 * @author Lorelvis Santos
 */
public class FiltroMascotaDTO {
    private final String nombre;
    private final Especie especie;
    private final Sexo sexo;
    private final Tamaño tamaño;
    private final EstadoMascota estado;

    public FiltroMascotaDTO(String nombre, Especie especie, Sexo sexo, Tamaño tamaño, EstadoMascota estado) {
        this.nombre = nombre;
        this.especie = especie;
        this.sexo = sexo;
        this.tamaño = tamaño;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public Especie getEspecie() {
        return especie;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public Tamaño getTamaño() {
        return tamaño;
    }

    public EstadoMascota getEstado() {
        return estado;
    }
}
