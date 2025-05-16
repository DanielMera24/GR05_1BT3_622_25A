package com.gestorfutbol.entity;

import jakarta.persistence.*;

@Entity
public class Tarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTarjeta;

    @Enumerated(EnumType.STRING)
    private TipoTarjeta tipoTarjeta;


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

    public Tarjeta(TipoTarjeta tipoTarjeta, String motivo, Partido partido, Jugador jugador) {
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

    public TipoTarjeta getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(TipoTarjeta tipoTarjeta) {
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
        this.partido = partido;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }
}
