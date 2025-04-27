package com.gestorfutbol.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class JugadorPartido {

    @Id
    @ManyToOne
    @JoinColumn(name = "idPartido")
    private Partido partido;

    @Id
    @ManyToOne
    @JoinColumn(name = "idJugador")
    private Jugador jugador;


    private int numTarjetasAmarillas;

    private int numTarjetasRojas;

    public JugadorPartido() {}


    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public int getNumTarjetasAmarillas() {
        return numTarjetasAmarillas;
    }

    public void setNumTarjetasAmarillas(int numTarjetasAmarillas) {
        this.numTarjetasAmarillas = numTarjetasAmarillas;
    }

    public int getNumTarjetasRojas() {
        return numTarjetasRojas;
    }

    public void setNumTarjetasRojas(int numTarjetasRojas) {
        this.numTarjetasRojas = numTarjetasRojas;
    }
}
