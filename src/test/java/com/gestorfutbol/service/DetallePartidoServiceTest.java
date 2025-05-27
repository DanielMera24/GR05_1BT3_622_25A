package com.gestorfutbol.service;

import org.junit.jupiter.api.BeforeAll;

public class DetallePartidoServiceTest {
    // Validar que exista un capitan por equipo
    // Validar que no haya más de un capitan por equipo
    // Validar mínimo y maxim de jugadores por equipo
    // Validar que los equipos tengan el mismo número de jugadores asociados
    // Validar que el registro de jugadores se realice previo al partido.
    // Validar que no existan jugadores duplicados en el registro.

    // Validar el minuto en la cual anoto gol este dentro del rango válido.
    // Validar que un jugador no anota dos goles en el mismo minuto.
    // Validar que no existan goles de diferentes jugadores (equipo) en el mismo minuto.
    private static DetallePartidoService detallePartidoService;

    @BeforeAll
    public static void setUp() {
        detallePartidoService = new DetallePartidoService();
    }


}
