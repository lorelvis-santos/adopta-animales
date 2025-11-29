package com.stackmasters.adoptaanimales.dto;

import com.stackmasters.adoptaanimales.model.Mascota;
import com.stackmasters.adoptaanimales.model.Mascota.Especie;
import com.stackmasters.adoptaanimales.model.Mascota.EstadoMascota;
import com.stackmasters.adoptaanimales.model.Mascota.Sexo;
import com.stackmasters.adoptaanimales.model.Mascota.Tamaño;
import java.time.LocalDate;

/**
 *
 * @author Lorelvis Santos
 */
public class CrearMascotaDTO {
    private final String nombre;
    private final Especie especie;
    private final String raza;
    private final Sexo sexo;
    private final Tamaño tamaño;
    private final double peso;
    private final LocalDate fechaNacimiento;
    private final String descripcion;
    private final boolean estaVacunado;
    private final boolean estaCastrado;
    private final Integer albergueId;  // Nueva variable agregada

    
    public CrearMascotaDTO(
        String nombre,
        Especie especie,
        String raza,
        Sexo sexo,
        Tamaño tamaño,
        double peso,
        LocalDate fechaNacimiento,
        String descripcion,
        boolean estaVacunado,
        boolean estaCastrado,
        Integer albergueId  // Nuevo parámetro
    ) {
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.sexo = sexo;
        this.tamaño = tamaño;
        this.peso = peso;
        this.fechaNacimiento = fechaNacimiento;
        this.descripcion = descripcion;
        this.estaVacunado = estaVacunado;
        this.estaCastrado = estaCastrado;
        this.albergueId = albergueId;  // Inicialización
    }

    public String getNombre() {
        return nombre;
    }

    public Especie getEspecie() {
        return especie;
    }

    public String getRaza() {
        return raza;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public Tamaño getTamaño() {
        return tamaño;
    }

    public double getPeso() {
        return peso;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
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
    
    public Integer getAlbergueId() {  // Nuevo getter
        return albergueId;
    }
}
