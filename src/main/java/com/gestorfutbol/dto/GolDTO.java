package com.gestorfutbol.dto;

public class GolDTO {
    private int idGol;
    private int minuto;
    private String nombreJugador;
    private String equipoJugador;
    private int dorsalJugador;
    private int idPartido;
    private int idJugador;

    // Constructor vacío
    public GolDTO() {}

    // Constructor completo
    public GolDTO(int idGol, int minuto, String nombreJugador, String equipoJugador,
                  int dorsalJugador, int idPartido, int idJugador) {
        this.idGol = idGol;
        this.minuto = minuto;
        this.nombreJugador = nombreJugador;
        this.equipoJugador = equipoJugador;
        this.dorsalJugador = dorsalJugador;
        this.idPartido = idPartido;
        this.idJugador = idJugador;
    }

    // Constructor sin ID (para nuevos goles)
    public GolDTO(int minuto, String nombreJugador, String equipoJugador,
                  int dorsalJugador, int idPartido, int idJugador) {
        this.minuto = minuto;
        this.nombreJugador = nombreJugador;
        this.equipoJugador = equipoJugador;
        this.dorsalJugador = dorsalJugador;
        this.idPartido = idPartido;
        this.idJugador = idJugador;
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

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public String getEquipoJugador() {
        return equipoJugador;
    }

    public void setEquipoJugador(String equipoJugador) {
        this.equipoJugador = equipoJugador;
    }

    public int getDorsalJugador() {
        return dorsalJugador;
    }

    public void setDorsalJugador(int dorsalJugador) {
        this.dorsalJugador = dorsalJugador;
    }

    public int getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(int idPartido) {
        this.idPartido = idPartido;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    @Override
    public String toString() {
        return "GolDTO{" +
                "idGol=" + idGol +
                ", minuto=" + minuto +
                ", nombreJugador='" + nombreJugador + '\'' +
                ", equipoJugador='" + equipoJugador + '\'' +
                ", dorsalJugador=" + dorsalJugador +
                ", idPartido=" + idPartido +
                ", idJugador=" + idJugador +
                '}';
    }
}