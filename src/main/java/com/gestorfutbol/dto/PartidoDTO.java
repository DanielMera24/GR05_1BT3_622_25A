package com.gestorfutbol.dto;

import com.gestorfutbol.entity.Equipo;
import com.gestorfutbol.entity.Torneo;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Date;

public class PartidoDTO {

    private int golesLocal;

    private int golesVisita;

    private String fechaPartido;

    private String estado;

    private int jornadaActual;

    private String equipoLocal;

    private String equipoVisita;

    private String torneo;

    public PartidoDTO(int golesLocal, int golesVisita, String fechaPartido, String estado, int jornadaActual, String equipoLocal, String equipoVisita, String torneo) {
        this.golesLocal = golesLocal;
        this.golesVisita = golesVisita;
        this.fechaPartido = fechaPartido;
        this.estado = estado;
        this.jornadaActual = jornadaActual;
        this.equipoLocal = equipoLocal;
        this.equipoVisita = equipoVisita;
        this.torneo = torneo;
    }

    public PartidoDTO(String fechaPartido, String estado, int jornadaActual, String equipoLocal, String equipoVisita, String torneo){
        this.fechaPartido = fechaPartido;
        this.estado = estado;
        this.jornadaActual = jornadaActual;
        this.equipoLocal = equipoLocal;
        this.equipoVisita = equipoVisita;
        this.torneo = torneo;
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public int getGolesVisita() {
        return golesVisita;
    }

    public String getFechaPartido() {
        return fechaPartido;
    }

    public String getEstado() {
        return estado;
    }

    public int getJornadaActual() {
        return jornadaActual;
    }


    public String getEquipoLocal() {
        return equipoLocal;
    }

    public String getEquipoVisita() {
        return equipoVisita;
    }

    public String getTorneo() {
        return torneo;
    }

    public void setFechaPartido(String fechaPartido) {
        this.fechaPartido = fechaPartido;
    }
}
