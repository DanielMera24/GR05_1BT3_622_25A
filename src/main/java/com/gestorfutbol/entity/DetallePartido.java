package com.gestorfutbol.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class DetallePartido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDetallePartido;

    @Column(nullable = false)
    private int dorsal;

    @Column(name = "capitan", nullable = false)
    private boolean esCapitan;

    @ManyToOne
    @JoinColumn(name = "idJugador", nullable = false)
    private Jugador jugador;

    @ManyToOne
    @JoinColumn(name = "idEquipo", nullable = false)
    private Equipo equipo;

    @ManyToOne
    @JoinColumn(name = "idPartido", nullable = false)
    private Partido partido;

    @OneToMany(mappedBy = "detallePartido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Gol> goles;

    public DetallePartido(Jugador jugador1, Partido partido, Equipo equipoA, int dorsal, boolean b) {
        this.jugador = jugador1;
        this.partido = partido;
        this.equipo = equipoA;
        this.dorsal = dorsal;
        this.esCapitan = b;
    }

    public DetallePartido() {

    }

    // Getters y setters
    public int getIdDetallePartido() {
        return idDetallePartido;
    }

    public void setIdDetallePartido(int idDetallePartido) {
        this.idDetallePartido = idDetallePartido;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public boolean isCapitan() {
        return esCapitan;
    }

    public void setEsCapitan(boolean esCapitan) {
        this.esCapitan = esCapitan;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public List<Gol> getGoles() {
        return goles;
    }

    public void setGoles(List<Gol> goles) {
        this.goles = goles;
        if (goles != null) {
            for (Gol g : goles) {
                g.setDetallePartido(this);
            }
        }
    }
}
