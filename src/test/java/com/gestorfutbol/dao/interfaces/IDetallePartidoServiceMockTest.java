package com.gestorfutbol.dao.interfaces;

import com.gestorfutbol.entity.DetallePartido;
import com.gestorfutbol.entity.Equipo;
import com.gestorfutbol.entity.Gol;
import com.gestorfutbol.entity.Jugador;
import com.gestorfutbol.entity.Partido;
import com.gestorfutbol.service.DetallePartidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class IDetallePartidoServiceMockTest {

    private DetallePartidoDAO mockDetallePartidoDAO;
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
        mockDetallePartidoDAO = Mockito.mock(DetallePartidoDAO.class);
        detallePartidoService = new DetallePartidoService(mockDetallePartidoDAO);

        equipoA = new Equipo();
        equipoA.setIdEquipo(1);
        equipoA.setNombre("Equipo A");

        equipoB = new Equipo();
        equipoB.setIdEquipo(2);
        equipoB.setNombre("Equipo B");

        partido = new Partido();
        partido.setIdPartido(1);
        partido.setEstado("En progreso");

        jugador1 = new Jugador();
        jugador1.setCedula("1111111111");
        jugador1.setNombre("Jugador 1");
        jugador1.setEquipo(equipoA);

        jugador2 = new Jugador();
        jugador2.setCedula("2222222222");
        jugador2.setNombre("Jugador 2");
        jugador2.setEquipo(equipoA);

        jugador3 = new Jugador();
        jugador3.setCedula("3333333333");
        jugador3.setNombre("Jugador 3");
        jugador3.setEquipo(equipoB);

        jugador4 = new Jugador();
        jugador4.setCedula("4444444444");
        jugador4.setNombre("Jugador 4");
        jugador4.setEquipo(equipoB);

        jugador5 = new Jugador();
        jugador5.setCedula("5555555555");
        jugador5.setNombre("Jugador 5");
        jugador5.setEquipo(equipoA);

        jugador6 = new Jugador();
        jugador6.setCedula("6666666666");
        jugador6.setNombre("Jugador 6");
        jugador6.setEquipo(equipoB);
    }

    @Test
    public void dadoDetallesValidosConCamposCompletos_cuandoGuardarDetalles_entoncesGuardaTodos() {
        List<DetallePartido> detalles = new ArrayList<>();

        DetallePartido dp1 = new DetallePartido(jugador1, partido, equipoA, jugador1.getDorsal(), true);
        List<Gol> goles1 = new ArrayList<>();
        goles1.add(new Gol(15, dp1));
        goles1.add(new Gol(30, dp1));
        dp1.setGoles(goles1);

        DetallePartido dp2 = new DetallePartido(jugador2, partido, equipoA, 11, false);
        dp2.setGoles(new ArrayList<>());

        DetallePartido dp3 = new DetallePartido(jugador3, partido, equipoB, 9, true);
        List<Gol> goles3 = new ArrayList<>();
        goles3.add(new Gol(45, dp3));
        dp3.setGoles(goles3);

        DetallePartido dp4 = new DetallePartido(jugador4, partido, equipoB, 8, false);
        dp4.setGoles(new ArrayList<>());

        DetallePartido dp5 = new DetallePartido(jugador5, partido, equipoA, 12, false);
        dp5.setGoles(new ArrayList<>());

        DetallePartido dp6 = new DetallePartido(jugador6, partido, equipoB, 7, false);
        dp6.setGoles(new ArrayList<>());




        detalles.add(dp1);
        detalles.add(dp2);
        detalles.add(dp3);
        detalles.add(dp4);
        detalles.add(dp5);
        detalles.add(dp6);

        // Mock DAO para que siempre devuelva true
        when(mockDetallePartidoDAO.guardar(any(DetallePartido.class))).thenReturn(true);

        boolean resultado = detallePartidoService.guardarDetalles(detalles);

        assertTrue(resultado, "Debe retornar true cuando todo es válido y se guardan todos");

        verify(mockDetallePartidoDAO, times(detalles.size())).guardar(any(DetallePartido.class));
    }
}
