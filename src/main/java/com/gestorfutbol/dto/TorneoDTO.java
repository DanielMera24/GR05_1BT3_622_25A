package com.gestorfutbol.dto;

import java.util.Date;

public class TorneoDTO {
    private int idTorneo;
    private String nombre;
    private Date fechaInicio;

    public TorneoDTO(int idTorneo, String nombre, Date fechaInicio) {
        this.idTorneo = idTorneo;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
    }

    public int getIdTorneo() { return idTorneo; }
    public String getNombre() { return nombre; }
    public Date getFechaInicio() { return fechaInicio; }
}
