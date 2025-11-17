package com.stackmasters.adoptaanimales.model;

import java.time.LocalDate;

/**
 *
 * @author Bianca Parra
 */
public class Mascota {
    
    private int idMascota;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String raza;
    private String tamaño;
    private String peso;
    private String especie;
    private enum sexo{
    Macho, Hembra
    }
    private String descripcion;
    private boolean estaCastrado;
    private boolean estaVacunado;
    private String condicionEspecial;
    private enum estado{
    EnAlbergue, EnProcesoDeAdopcion, Adoptada
    }
    private int veterinariaId;
    private int albergueId;

    public Mascota() {
    }

    public Mascota(int idMascota, String nombre, LocalDate fechaNacimiento, String raza, String tamaño, String peso, String especie, String descripcion, boolean estaCastrado, boolean estaVacunado, String condicionEspecial, int veterinariaId, int albergueId) {
        this.idMascota = idMascota;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.raza = raza;
        this.tamaño = tamaño;
        this.peso = peso;
        this.especie = especie;
        this.descripcion = descripcion;
        this.estaCastrado = estaCastrado;
        this.estaVacunado = estaVacunado;
        this.condicionEspecial = condicionEspecial;
        this.veterinariaId = veterinariaId;
        this.albergueId = albergueId;
    }

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEstaCastrado() {
        return estaCastrado;
    }

    public void setEstaCastrado(boolean estaCastrado) {
        this.estaCastrado = estaCastrado;
    }

    public boolean isEstaVacunado() {
        return estaVacunado;
    }

    public void setEstaVacunado(boolean estaVacunado) {
        this.estaVacunado = estaVacunado;
    }

    public String getCondicionEspecial() {
        return condicionEspecial;
    }

    public void setCondicionEspecial(String condicionEspecial) {
        this.condicionEspecial = condicionEspecial;
    }

    public int getVeterinariaId() {
        return veterinariaId;
    }

    public void setVeterinariaId(int veterinariaId) {
        this.veterinariaId = veterinariaId;
    }

    public int getAlbergueId() {
        return albergueId;
    }

    public void setAlbergueId(int albergueId) {
        this.albergueId = albergueId;
    }

}
