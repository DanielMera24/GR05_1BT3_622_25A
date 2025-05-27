package com.gestorfutbol.dto;

public class DetallePartidoDTO {

    private int idDetallePartido;
    private int dorsal;
    private boolean esCapitan;
    private int idJugador;
    private String nombreJugador;
    private String posicionJugador;
    private int idEquipo;
    private String nombreEquipo;
    private String siglasEquipo;
    private int idPartido;
    private int cantidadGoles;

    // Constructor vacío
    public DetallePartidoDTO() {
    }

    // Constructor completo
    public DetallePartidoDTO(int idDetallePartido, int dorsal, boolean esCapitan,
                             int idJugador, String nombreJugador, String posicionJugador,
                             int idEquipo, String nombreEquipo, String siglasEquipo,
                             int idPartido, int cantidadGoles) {
        this.idDetallePartido = idDetallePartido;
        this.dorsal = dorsal;
        this.esCapitan = esCapitan;
        this.idJugador = idJugador;
        this.nombreJugador = nombreJugador;
        this.posicionJugador = posicionJugador;
        this.idEquipo = idEquipo;
        this.nombreEquipo = nombreEquipo;
        this.siglasEquipo = siglasEquipo;
        this.idPartido = idPartido;
        this.cantidadGoles = cantidadGoles;
    }

    // Constructor básico para crear desde entidad
    public DetallePartidoDTO(int idDetallePartido, int dorsal, boolean esCapitan,
                             String nombreJugador, String nombreEquipo, int cantidadGoles) {
        this.idDetallePartido = idDetallePartido;
        this.dorsal = dorsal;
        this.esCapitan = esCapitan;
        this.nombreJugador = nombreJugador;
        this.nombreEquipo = nombreEquipo;
        this.cantidadGoles = cantidadGoles;
    }

    // Getters y Setters
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

    public boolean isEsCapitan() {
        return esCapitan;
    }

    public void setEsCapitan(boolean esCapitan) {
        this.esCapitan = esCapitan;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public String getPosicionJugador() {
        return posicionJugador;
    }

    public void setPosicionJugador(String posicionJugador) {
        this.posicionJugador = posicionJugador;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public String getSiglasEquipo() {
        return siglasEquipo;
    }

    public void setSiglasEquipo(String siglasEquipo) {
        this.siglasEquipo = siglasEquipo;
    }

    public int getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(int idPartido) {
        this.idPartido = idPartido;
    }

    public int getCantidadGoles() {
        return cantidadGoles;
    }

    public void setCantidadGoles(int cantidadGoles) {
        this.cantidadGoles = cantidadGoles;
    }
}