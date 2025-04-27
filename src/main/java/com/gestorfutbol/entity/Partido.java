package com.gestorfutbol.entity;


import jakarta.persistence.*;

import java.util.Date;
import java.util.List;


@Entity
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPartido;

    private int golesLocal;

    private int golesVisita;

    @Temporal(TemporalType.DATE)
    private Date fechaPartido;

    private String Estado;

    private int jornadaActual;

    @OneToOne
    @JoinColumn(name = "idEquipoLocal")
    private Equipo equipoLocal;

    @OneToOne
    @JoinColumn(name = "idEquipoVisita")
    private Equipo equipoVisita;


    @ManyToOne()
    @JoinColumn(name = "idTorneo")
    private Torneo torneo;

    public Partido() {}


    public int getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(int idPartido) {
        this.idPartido = idPartido;
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public void setGolesLocal(int golesLocal) {
        this.golesLocal = golesLocal;
    }

    public int getGolesVisita() {
        return golesVisita;
    }

    public void setGolesVisita(int golesVisita) {
        this.golesVisita = golesVisita;
    }

    public Date getFechaPartido() {
        return fechaPartido;
    }

    public void setFechaPartido(Date fechaPartido) {
        this.fechaPartido = fechaPartido;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public int getJornadaActual() {
        return jornadaActual;
    }

    public void setJornadaActual(int jornadaActual) {
        this.jornadaActual = jornadaActual;
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(Equipo equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public Equipo getEquipoVisita() {
        return equipoVisita;
    }

    public void setEquipoVisita(Equipo equipoVisita) {
        this.equipoVisita = equipoVisita;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }
}
