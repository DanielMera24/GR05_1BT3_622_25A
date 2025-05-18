package com.gestorfutbol.entity;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idJugador;


    private String cedula;
    private String nombre;

    private int dorsal;

    private int edad;

    private String posicion;

    @ManyToOne()
    @JoinColumn(name = "idEquipo")
    private Equipo equipo;

    public Jugador(String cedula){
        this.cedula = cedula;
    }

    public Jugador(String cedula , String nombre, int edad, String posicion, int dorsal) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.edad = edad;
        this.dorsal = dorsal;
        this.posicion = posicion;
        this.equipo = equipo;
    }


    public Jugador() {

    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCedula() {
        return cedula;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        // Comparar por ID (o cédula, si es único)
        return Objects.equals(idJugador, jugador.idJugador);
    }

    @Override
    public int hashCode() {
        // Coherencia con equals()
        return Objects.hash(idJugador);
    }

}
