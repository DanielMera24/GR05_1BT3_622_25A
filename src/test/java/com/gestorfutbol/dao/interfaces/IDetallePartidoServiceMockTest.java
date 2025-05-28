package com.gestorfutbol.dao.interfaces;

import com.gestorfutbol.entity.DetallePartido;
import com.gestorfutbol.entity.Equipo;
import com.gestorfutbol.entity.Gol;
import com.gestorfutbol.entity.Jugador;
import com.gestorfutbol.entity.Partido;
import com.gestorfutbol.service.DetallePartidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IDetallePartidoServiceMockTest {

    @Mock
    private DetallePartidoDAO mockDetallePartidoDAO;

    @InjectMocks
    private DetallePartidoService detallePartidoService;

    private Equipo equipoA;
    private Equipo equipoB;
    private Partido partido;
    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugador3;
    private Jugador jugador4;
    private Jugador jugador5;
    private Jugador jugador6;

    @BeforeEach
    public void setUp() {
        equipoA = new Equipo();
        equipoA.setIdEquipo(1);
        equipoA.setNombre("Equipo A");

        equipoB = new Equipo();
        equipoB.setIdEquipo(2);
        equipoB.setNombre("Equipo B");

        partido = new Partido();
        partido.setIdPartido(1);
        partido.setEstado("En progreso");

        jugador1 = crearJugador("1111111111", "Jugador 1", equipoA, 10);
        jugador2 = crearJugador("2222222222", "Jugador 2", equipoA, 11);
        jugador3 = crearJugador("3333333333", "Jugador 3", equipoB, 9);
        jugador4 = crearJugador("4444444444", "Jugador 4", equipoB, 8);
        jugador5 = crearJugador("5555555555", "Jugador 5", equipoA, 12);
        jugador6 = crearJugador("6666666666", "Jugador 6", equipoB, 7);
    }

    private Jugador crearJugador(String cedula, String nombre, Equipo equipo, int dorsal) {
        Jugador jugador = new Jugador();
        jugador.setCedula(cedula);
        jugador.setNombre(nombre);
        jugador.setEquipo(equipo);
        jugador.setDorsal(dorsal);
        return jugador;
    }

    private DetallePartido crearDetalle(Jugador jugador, boolean esCapitan, List<Gol> goles) {
        DetallePartido detalle = new DetallePartido(jugador, partido, jugador.getEquipo(), jugador.getDorsal(), esCapitan);
        detalle.setGoles(goles);
        return detalle;
    }

    @Test
    public void dadoDetallesValidosConCamposCompletos_cuandoGuardarDetalles_entoncesGuardaTodos() {
        // Arrange
        List<DetallePartido> detalles = List.of(
                crearDetalle(jugador1, true, List.of(new Gol(15, null), new Gol(30, null))),
                crearDetalle(jugador2, false, new ArrayList<>()),
                crearDetalle(jugador3, true, List.of(new Gol(45, null))),
                crearDetalle(jugador4, false, new ArrayList<>()),
                crearDetalle(jugador5, false, new ArrayList<>()),
                crearDetalle(jugador6, false, new ArrayList<>())
        );

        when(mockDetallePartidoDAO.guardar(any(DetallePartido.class))).thenReturn(true);

        // Act
        boolean resultado = detallePartidoService.guardarDetalles(detalles);

        // Assert
        assertTrue(resultado);
        verify(mockDetallePartidoDAO, times(detalles.size())).guardar(any(DetallePartido.class));
    }

    @Test
    public void dadoListaVacia_cuandoGuardarDetalles_entoncesRetornaFalse() {
        // Arrange
        List<DetallePartido> detallesVacios = new ArrayList<>();

        // Act
        boolean resultado = detallePartidoService.guardarDetalles(detallesVacios);

        // Assert
        assertFalse(resultado);
        verify(mockDetallePartidoDAO, never()).guardar(any());
    }

    @Test
    public void dadoErrorAlGuardar_cuandoGuardarDetalles_entoncesRetornaFalse() {
        // Arrange
        List<DetallePartido> detalles = List.of(
                crearDetalle(jugador1, true, new ArrayList<>())
        );

        when(mockDetallePartidoDAO.guardar(any(DetallePartido.class))).thenReturn(false);

        // Act
        boolean resultado = detallePartidoService.guardarDetalles(detalles);

        // Assert
        assertFalse(resultado);
        verify(mockDetallePartidoDAO, times(1)).guardar(any(DetallePartido.class));
    }

    @Test
    public void dadoJugadorDuplicado_cuandoGuardarDetalles_entoncesRetornaFalse() {
        // Arrange
        List<DetallePartido> detalles = List.of(
                crearDetalle(jugador1, true, new ArrayList<>()),
                crearDetalle(jugador1, false, new ArrayList<>()) // Mismo jugador
        );

        // Act
        boolean resultado = detallePartidoService.guardarDetalles(detalles);

        // Assert
        assertFalse(resultado);
        verify(mockDetallePartidoDAO, never()).guardar(any());
    }

    @Test
    public void dadoPartidoFinalizado_cuandoGuardarDetalles_entoncesRetornaFalse() {
        // Arrange
        partido.setEstado("Finalizado");
        List<DetallePartido> detalles = List.of(
                crearDetalle(jugador1, true, new ArrayList<>())
        );

        // Act
        boolean resultado = detallePartidoService.guardarDetalles(detalles);

        // Assert
        assertFalse(resultado);
        verify(mockDetallePartidoDAO, never()).guardar(any());
    }

    @Test
    public void dadoEquipoSinCapitan_cuandoGuardarDetalles_entoncesRetornaFalse() {
        // Arrange
        List<DetallePartido> detalles = List.of(
                crearDetalle(jugador1, false, new ArrayList<>()), // Equipo A sin capitán
                crearDetalle(jugador3, true, new ArrayList<>())   // Equipo B con capitán
        );

        // Act
        boolean resultado = detallePartidoService.guardarDetalles(detalles);

        // Assert
        assertFalse(resultado);
        verify(mockDetallePartidoDAO, never()).guardar(any());
    }

    @Test
    public void dadoGolMinutoInvalido_cuandoGuardarDetalles_entoncesRetornaFalse() {
        // Arrange
        List<DetallePartido> detalles = List.of(
                crearDetalle(jugador1, true, List.of(new Gol(91, null))) // Minuto inválido
        );

        // Act
        boolean resultado = detallePartidoService.guardarDetalles(detalles);

        // Assert
        assertFalse(resultado);
        verify(mockDetallePartidoDAO, never()).guardar(any());
    }

    @Test
    public void dadoJugadorConGolesMismoMinuto_cuandoGuardarDetalles_entoncesRetornaFalse() {
        // Arrange
        List<DetallePartido> detalles = List.of(
                crearDetalle(jugador1, true, List.of(new Gol(15, null), new Gol(15, null))) // Dos goles mismo minuto
        );
        // Act
        boolean resultado = detallePartidoService.guardarDetalles(detalles);
        // Assert
        assertFalse(resultado);
        verify(mockDetallePartidoDAO, never()).guardar(any());
    }
}