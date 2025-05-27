package com.gestorfutbol.service;

import com.gestorfutbol.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class DetallePartidoServiceTest {

    private DetallePartidoService detallePartidoService;

    private Torneo torneo;
    private Equipo equipoA;
    private Equipo equipoB;
    private Partido partido;

    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugador3;
    private Jugador jugador4;

    private List<DetallePartido> detallesPartido;

    @BeforeEach
    public void setUp() {
        detallePartidoService = new DetallePartidoService();
        // Configuración común para todas las pruebas
        torneo = new Torneo();
        torneo.setIdTorneo(4);
        torneo.setNombre("AEIS-CUP");
        torneo.setFechaInicio(new Date());
        torneo.setNumFechas(12);

        equipoA = new Equipo(1, "Liga de Quito", "Quito", "Rodrigo Paz", "LDU");
        equipoA.setTorneo(torneo);
        equipoB = new Equipo(2, "Barcelona", "Guayaquil", "Monumental", "BSC");
        equipoB.setTorneo(torneo);

        partido = new Partido();
        partido.setEquipoLocal(equipoA);
        partido.setEquipoVisita(equipoB);
        partido.setEstado("En progreso");
        partido.setIdPartido(15);
        partido.setFechaPartido(new Date());
        partido.setJornadaActual(1);
        partido.setTorneo(torneo);

        this.jugador1 = new Jugador("0503867723", "Juan Naranjo", 15, "DELANTERO", 23);
        this.jugador1.setEquipo(equipoA);
        this.jugador2 = new Jugador("0503867735", "Eduardo Ramos", 24, "DELANTERO", 7);
        this.jugador2.setEquipo(equipoA);
        this.jugador3 = new Jugador("0503867736", "Gonzalo Peña", 22, "DELANTERO", 9);
        this.jugador3.setEquipo(equipoB);
        this.jugador4 = new Jugador("0503867737", "Cristian Flores", 26, "DELANTERO", 11);
        this.jugador4.setEquipo(equipoB);

    }

    @Test
    public void dadoJugadores_cuandoUnCapitanPorEquipo_entoncesRetornaVerdadero() {
        // Definición de Jugadores
        detallesPartido = List.of(
                new DetallePartido(jugador1, partido, jugador1.getEquipo(), jugador1.getDorsal(), false),
                new DetallePartido(jugador2, partido, jugador2.getEquipo(), jugador2.getDorsal(), true),
                new DetallePartido(jugador3, partido, jugador3.getEquipo(), jugador3.getDorsal(), false),
                new DetallePartido(jugador4, partido, jugador4.getEquipo(), jugador4.getDorsal(), true));

        // Act
        boolean resultado = detallePartidoService.validarCapitanesPorEquipo(detallesPartido);

        // Assert
        assertTrue(resultado, "Debería retornar verdadero cuando cada equipo tiene exactamente un capitán");
    }

    @Test
    public void dadoJugadores_cuandoEquipoSinCapitan_entoncesRetornaFalso() {
        // Arrange
        // Modificamos los detalles para que equipoA no tenga capitán
        detallesPartido = List.of(
                new DetallePartido(jugador1, partido, equipoA, jugador1.getDorsal(), false),
                new DetallePartido(jugador2, partido, equipoA, jugador2.getDorsal(), false),
                new DetallePartido(jugador3, partido, equipoB, jugador3.getDorsal(), false),
                new DetallePartido(jugador4, partido, equipoB, jugador4.getDorsal(), false));
        // Assert
        boolean resultado = detallePartidoService.validarCapitanesPorEquipo(detallesPartido);
        assertFalse(resultado, "Debería retornar falso cuando un equipo no tiene capitán");
    }

    @Test
    public void dadoListaDetallesPartido_cuandoEquipoConMultiplesCapitanes_entoncesRetornaFalso() {

        // Modificamos los detalles para que equipoA tenga dos capitanes
        detallesPartido = List.of(
                new DetallePartido(jugador1, partido, equipoA, jugador1.getDorsal(), true),
                new DetallePartido(jugador2, partido, equipoA, jugador2.getDorsal(), true),
                new DetallePartido(jugador3, partido, equipoB, jugador3.getDorsal(), false),
                new DetallePartido(jugador4, partido, equipoB, jugador4.getDorsal(), false));
        boolean resultado = detallePartidoService.validarCapitanesPorEquipo(detallesPartido);
        // Assert
        assertFalse(resultado, "Debería retornar falso cuando un equipo tiene múltiples capitanes");
    }

    @Test
    public void dadoListaDetallesPartido_cuandoNoCumpleNumeroJugadores_entoncesRetornaFalso() {
        detallesPartido = List.of(
                new DetallePartido(jugador1, partido, equipoA, jugador1.getDorsal(), false),
                new DetallePartido(jugador2, partido, equipoA, jugador2.getDorsal(), true),
                new DetallePartido(jugador3, partido, equipoB, jugador3.getDorsal(), false),
                new DetallePartido(jugador4, partido, equipoB, jugador4.getDorsal(), true));

        boolean resultado = detallePartidoService.cumpleEquipoNumeroDeJugadores(detallesPartido);
        assertFalse(resultado, "Debería retornar falso cuando un equipo no posee los suficientes jugadores para iniciar un partido");

    }

    @Test
    public void dadoListaDetallesPartido_cuandoExistenRegistrosDuplicados_entoncesRetornarVerdadero() {
        detallesPartido = List.of(
                new DetallePartido(jugador1, partido, equipoA, jugador1.getDorsal(), false),
                new DetallePartido(jugador2, partido, equipoA, jugador2.getDorsal(), true),
                new DetallePartido(jugador3, partido, equipoB, jugador3.getDorsal(), false),
                new DetallePartido(jugador3, partido, equipoB, jugador4.getDorsal(), true));

        boolean resultado = detallePartidoService.poseeJugadoresDuplicados(detallesPartido);
        assertTrue(resultado, "Debería retornar verdadero cuando se repitan jugadores en el registro");
    }

}