package com.gestorfutbol.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class JugadorPartido {

    @EmbeddedId
    private JugadorPartidoId id;

    private int numTarjetasAmarillas;
    private int numTarjetasRojas;

    public JugadorPartido() {}

    public JugadorPartidoId getId() {
        return id;
    }

    public void setId(JugadorPartidoId id) {
        this.id = id;
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

    // Métodos auxiliares para acceder a los objetos relacionados
    @Transient
    public Partido getPartido() {
        return id.getPartido();
    }

    public void setPartido(Partido partido) {
        if (id == null) {
            id = new JugadorPartidoId();
        }
        id.setPartido(partido);
    }

    @Transient
    public Jugador getJugador() {
        return id.getJugador();    }

    public void setJugador(Jugador jugador) {
        if (id == null) {
            id = new JugadorPartidoId();
        }
        id.setJugador(jugador);
    }
}