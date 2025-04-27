package com.gestorfutbol.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JugadorPartidoId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "idPartido")
    private Partido partido;

    @ManyToOne
    @JoinColumn(name = "idJugador")
    private Jugador jugador;

    public JugadorPartidoId() {}

    public JugadorPartidoId(Partido partido, Jugador jugador) {
        this.partido = partido;
        this.jugador = jugador;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JugadorPartidoId that = (JugadorPartidoId) o;
        return Objects.equals(partido.getIdPartido(), that.partido.getIdPartido()) &&
                Objects.equals(jugador.getIdJugador(), that.jugador.getIdJugador());
    }

    @Override
    public int hashCode() {
        return Objects.hash(partido.getIdPartido(), jugador.getIdJugador());
    }
}