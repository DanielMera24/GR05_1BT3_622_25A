package com.gestorfutbol.dto;

import com.gestorfutbol.entity.Equipo;

public class JugadorDTO {
    private String nombre;
    private int dorsal;
    private EquipoDTO equipo;
    public JugadorDTO(String nombre, int espaldar, EquipoDTO equipo) {
        this.nombre = nombre;
        this.dorsal = espaldar;
        this.equipo = equipo;
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
    public EquipoDTO getIdEquipo() {
        return equipo;
    }


}
