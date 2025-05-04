package com.gestorfutbol.dto;

import com.gestorfutbol.entity.Equipo;
import com.gestorfutbol.entity.Torneo;

import java.util.Date;

public class TablaPosicionesDTO {
    private int idTablaPosicion;
    private Equipo equipo;
    private Torneo torneo;
    private int puntosAcumulados;
    private int partidosJugados;
    private int partidosGanados;
    private int partidosEmpatados;
    private int partidosPerdidos;
    private int golesAFavor;
    private int golesEnContra;
    private int diferenciaGoles;
    private Date fechaActualizacion;

    public TablaPosicionesDTO(int idTablaPosicion, Equipo equipo, Torneo torneo,
                              int puntosAcumulados, int partidosJugados, int partidosGanados,
                              int partidosEmpatados, int partidosPerdidos, int golesAFavor,
                              int golesEnContra, int diferenciaGoles, Date fechaActualizacion) {
        this.idTablaPosicion = idTablaPosicion;
        this.equipo = equipo;
        this.torneo = torneo;
        this.puntosAcumulados = puntosAcumulados;
        this.partidosJugados = partidosJugados;
        this.partidosGanados = partidosGanados;
        this.partidosEmpatados = partidosEmpatados;
        this.partidosPerdidos = partidosPerdidos;
        this.golesAFavor = golesAFavor;
        this.golesEnContra = golesEnContra;
        this.diferenciaGoles = diferenciaGoles;
        this.fechaActualizacion = fechaActualizacion;
    }

    // Getters
    public int getIdTablaPosicion() { return idTablaPosicion; }
    public Equipo getEquipo() { return equipo; }
    public Torneo getTorneo() { return torneo; }
    public int getPuntosAcumulados() { return puntosAcumulados; }
    public int getPartidosJugados() { return partidosJugados; }
    public int getPartidosGanados() { return partidosGanados; }
    public int getPartidosEmpatados() { return partidosEmpatados; }
    public int getPartidosPerdidos() { return partidosPerdidos; }
    public int getGolesAFavor() { return golesAFavor; }
    public int getGolesEnContra() { return golesEnContra; }
    public int getDiferenciaGoles() { return diferenciaGoles; }
    public Date getFechaActualizacion() { return fechaActualizacion; }
}
