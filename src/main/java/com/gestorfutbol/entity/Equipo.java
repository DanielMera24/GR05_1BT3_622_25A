package com.gestorfutbol.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;


@Entity
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEquipo;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String ciudad;

    @Column(nullable = false)
    private String estadio;

    @Column(nullable = false, length = 3)
    private String siglas;


    @ManyToOne
    @JoinColumn(name = "idTorneo")
    private Torneo torneo;

    @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL)
    private List<Jugador> jugadores;

    @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL)
    private List<TablaPosiciones> posiciones;


    public Equipo() {}

    public Equipo(int id, String nombre, String ciudad, String estadio, String siglas) {
        this.idEquipo = id;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.estadio = estadio;
        this.siglas = siglas;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public List<TablaPosiciones> getPosiciones() {
        return posiciones;
    }

    public void setPosiciones(List<TablaPosiciones> posiciones) {
        this.posiciones = posiciones;
    }

    @Override

    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Equipo equipo = (Equipo) o;

        return Objects.equals(idEquipo, equipo.idEquipo);

    }

    @Override

    public int hashCode() {

        return Objects.hash(idEquipo);

    }


}
