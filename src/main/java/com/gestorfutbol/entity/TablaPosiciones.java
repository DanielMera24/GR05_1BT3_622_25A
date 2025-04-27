package com.gestorfutbol.entity;


import jakarta.persistence.*;

import java.util.Date;


@Entity
public class TablaPosiciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTablaPosicion;

    private int puntosAcumulados;

    @Temporal(TemporalType.DATE)
    private Date fechaActualizacion;

    @OneToOne
    @JoinColumn(name = "idTorneo")
    private Torneo torneo;

    public TablaPosiciones() {}



    public int getIdTablaPosicion() {
        return idTablaPosicion;
    }

    public void setIdTablaPosicion(int idTablaPosicion) {
        this.idTablaPosicion = idTablaPosicion;
    }

    public int getPuntosAcumulados() {
        return puntosAcumulados;
    }

    public void setPuntosAcumulados(int puntosAcumulados) {
        this.puntosAcumulados = puntosAcumulados;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }
}
