package com.gestorfutbol.entity;

import jakarta.persistence.*;

@Entity
public class Gol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idGol;

    @Column(nullable = false)
    private int minuto;

    // Relación con DetallePartido (contiene jugador, partido, equipo)
    @ManyToOne
    @JoinColumn(name = "idDetallePartido", nullable = false)
    private DetallePartido detallePartido;

    // Constructores
    public Gol() {}

    public Gol(int minuto, DetallePartido detallePartido) {
        this.minuto = minuto;
        this.detallePartido = detallePartido;
    }

    // Getters y Setters
    public int getIdGol() {
        return idGol;
    }

    public void setIdGol(int idGol) {
        this.idGol = idGol;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public DetallePartido getDetallePartido() {
        return detallePartido;
    }

    public void setDetallePartido(DetallePartido detallePartido) {
        this.detallePartido = detallePartido;
    }

    // Métodos de conveniencia para acceder a información del jugador
    public Jugador getJugador() {
        return detallePartido != null ? detallePartido.getJugador() : null;
    }

    public Equipo getEquipo() {
        return detallePartido != null ? detallePartido.getEquipo() : null;
    }

    public int getDorsal() {
        return detallePartido != null ? detallePartido.getDorsal() : 0;
    }
}