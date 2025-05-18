package com.gestorfutbol.dto;

public class TarjetaDTO {
    private int idTarjeta;
    private String tipoTarjeta;
    private int minuto;
    private String motivo;

    // Información del jugador
    private int idJugador;
    private String nombreJugador;
    private String equipoJugador;
    private int dorsalJugador;

    // Información del partido
    private int idPartido;
    private String siglaEquipoLocal;
    private String siglaEquipoVisitante;

    public TarjetaDTO(int idTarjeta, String tipoTarjeta, int minuto, String motivo,
                      int idJugador, String nombreJugador, String equipoJugador, int dorsalJugador,
                      int idPartido, String siglaEquipoLocal, String siglaEquipoVisitante) {
        this.idTarjeta = idTarjeta;
        this.tipoTarjeta = tipoTarjeta;
        this.minuto = minuto;
        this.motivo = motivo;
        this.idJugador = idJugador;
        this.nombreJugador = nombreJugador;
        this.equipoJugador = equipoJugador;
        this.dorsalJugador = dorsalJugador;
        this.idPartido = idPartido;
        this.siglaEquipoLocal = siglaEquipoLocal;
        this.siglaEquipoVisitante = siglaEquipoVisitante;
    }

    // Getters
    public int getIdTarjeta() { return idTarjeta; }
    public String getTipoTarjeta() { return tipoTarjeta; }
    public int getMinuto() { return minuto; }
    public String getMotivo() { return motivo; }
    public int getIdJugador() { return idJugador; }
    public String getNombreJugador() { return nombreJugador; }
    public String getEquipoJugador() { return equipoJugador; }
    public int getDorsalJugador() { return dorsalJugador; }
    public int getIdPartido() { return idPartido; }
    public String getSiglaEquipoLocal() { return siglaEquipoLocal; }
    public String getSiglaEquipoVisitante() { return siglaEquipoVisitante; }
}