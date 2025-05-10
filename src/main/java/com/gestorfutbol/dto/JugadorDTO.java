package com.gestorfutbol.dto;

import com.gestorfutbol.entity.Equipo;

public class JugadorDTO {
    private String nombre;
    private int dorsal;
    private int idEquipo;
    private String cedula;
    public JugadorDTO(String cedula, String nombre, int espaldar, int idEquipo) {
        this.nombre = nombre;
        this.dorsal = espaldar;
        this.idEquipo = idEquipo;
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }
    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }
    public String getCedula() {
        return cedula;
    }
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }


}
