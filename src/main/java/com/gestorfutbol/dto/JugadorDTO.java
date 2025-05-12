package com.gestorfutbol.dto;

public class JugadorDTO {
    private int idJugador;
    private String cedula;
    private String nombre;
    private int dorsal;
    private int edad;
    private String posicion;
    private String nombreEquipo;
    private String abreviaturaEquipo;

    public JugadorDTO(int idJugador, String cedula, String nombre, int dorsal,
                      int edad, String posicion, String nombreEquipo, String abreviaturaEquipo) {
        this.idJugador = idJugador;
        this.cedula = cedula;
        this.nombre = nombre;
        this.dorsal = dorsal;
        this.edad = edad;
        this.posicion = posicion;
        this.nombreEquipo = nombreEquipo;
        this.abreviaturaEquipo = abreviaturaEquipo;
    }

    public JugadorDTO(String cedula, String nombre, int dorsal, int edad,
                      String posicion, String nombreEquipo, String abreviaturaEquipo) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.dorsal = dorsal;
        this.edad = edad;
        this.posicion = posicion;
        this.nombreEquipo = nombreEquipo;
        this.abreviaturaEquipo = abreviaturaEquipo;
    }

    public int getIdJugador() { return idJugador; }
    public String getCedula() { return cedula; }
    public String getNombre() { return nombre; }
    public int getDorsal() { return dorsal; }
    public int getEdad() { return edad; }
    public String getPosicion() { return posicion; }
    public String getNombreEquipo() { return nombreEquipo; }
    public String getAbreviaturaEquipo() { return abreviaturaEquipo; }
}