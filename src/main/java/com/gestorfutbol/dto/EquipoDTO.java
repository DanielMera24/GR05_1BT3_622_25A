package com.gestorfutbol.dto;

public class EquipoDTO {
    private int idEquipo;
    private String nombre;
    private String ciudad;
    private String estadio;
    private String siglas;
    private int idTorneo;

    public EquipoDTO(String nombre, String ciudad, String estadio, String siglas) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.estadio = estadio;
        this.siglas = siglas;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }

    public int getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(int idTorneo) {
        this.idTorneo = idTorneo;
    }

}
