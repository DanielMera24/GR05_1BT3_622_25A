package com.gestorfutbol.dto;

public class JugadorDTO {
    private String nombre;
    private int dorsal;
    private int idEquipo;
    public JugadorDTO(String nombre, int espaldar, int idEquipo) {
        this.nombre = nombre;
        this.dorsal = espaldar;
        this.idEquipo = idEquipo;
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


}
