
package com.gestorfutbol.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class DetallePartido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDetallePartido; // Corrección: era "idDatallePartido"

    private boolean esCapitan;

    // Dorsal específico para este partido (puede cambiar entre partidos)
    private int dorsal;

    @ManyToOne
    @JoinColumn(name = "idJugador")
    private Jugador jugador;

    @ManyToOne
    @JoinColumn(name = "idPartido")
    private Partido partido;

    // Equipo al que pertenece en este partido específico
    @ManyToOne
    @JoinColumn(name = "idEquipo")
    private Equipo equipo;

    @OneToMany(mappedBy = "detallePartido", cascade = CascadeType.ALL)
    private List<Gol> goles;

    // Constructores
    public DetallePartido() {}

    public DetallePartido(Jugador jugador, Partido partido, Equipo equipo, int dorsal, boolean esCapitan) {
        this.jugador = jugador;
        this.partido = partido;
        this.equipo = equipo;
        this.dorsal = dorsal;
        this.esCapitan = esCapitan;
    }

    // Getters y Setters
    public int getIdDetallePartido() {
        return idDetallePartido;
    }

    public void setIdDetallePartido(int idDetallePartido) {
        this.idDetallePartido = idDetallePartido;
    }

    public boolean isCapitan() {
        return esCapitan;
    }

    public void setEsCapitan(boolean esCapitan) {
        this.esCapitan = esCapitan;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public List<Gol> getGoles() {
        return goles;
    }

    public void setGoles(List<Gol> goles) {
        this.goles = goles;
    }

}