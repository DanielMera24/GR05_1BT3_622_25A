package com.gestorfutbol.entity;

import jakarta.persistence.*;

@Entity
public class Tarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTarjeta;

    private String tipoTarjeta;


    private String motivo;

    private int minuto;

    @ManyToOne
    @JoinColumn(name = "idPartido")
    private Partido partido;

    @ManyToOne
    @JoinColumn(name = "idJugador")
    private Jugador jugador;

    public Tarjeta() {
    }

    public Tarjeta(String tipoTarjeta, String motivo, Partido partido, Jugador jugador) {
        if(tipoTarjeta == null || motivo == null || partido == null || jugador == null) {
            throw new IllegalArgumentException("no se pueden poner valores nulos");
        }
        this.tipoTarjeta = tipoTarjeta;
        this.motivo = motivo;
        this.partido = partido;
        this.jugador = jugador;
    }

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        if(partido == null) throw new IllegalArgumentException("Partido no puede ser nulo");

        this.partido = partido;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        if(jugador == null) throw new IllegalArgumentException("Jugador no puede ser nulo");
        this.jugador = jugador;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }
}
