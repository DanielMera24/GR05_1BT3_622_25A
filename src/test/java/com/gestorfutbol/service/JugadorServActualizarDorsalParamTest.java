package com.gestorfutbol.service;

import com.gestorfutbol.entity.Equipo;
import com.gestorfutbol.entity.Jugador;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(value = Parameterized.class)
public class JugadorServActualizarDorsalParamTest {
    private int dorsal;
    private Equipo equipo;
    private String cedulaJugadorActual;

    @Parameterized.Parameters
    public static Iterable<Object[]> parameters() {
        List<Object[]> objects = new ArrayList<Object[]>();
        List<Equipo> equipos = new ArrayList<>();
        List<Jugador> jugadores = new ArrayList<>();
        Jugador j1 = new Jugador("0987654321");
        j1.setDorsal(10);
        Jugador j2 = new Jugador("1734557891");
        j1.setDorsal(20);
        Jugador j3 = new Jugador("4987644321");
        j1.setDorsal(30);
        jugadores.add(j1);jugadores.add(j2);jugadores.add(j3);
        Equipo equipo = new Equipo();
        equipo.setJugadores(jugadores);
        objects.add(new Object[]{10, equipo, "0987654321"});
        objects.add(new Object[]{10, equipo, "1734557891"});
        objects.add(new Object[]{10, equipo, "4987644321"});
        return objects;
    }
    public JugadorServActualizarDorsalParamTest(int dorsal, Equipo equipo, String cedulaJugadorActual) {
        this.dorsal = dorsal;
        this.equipo = equipo;
        this.cedulaJugadorActual = cedulaJugadorActual;
    }

    @Test
    public void cuando_jugadorMantieneDorsal_enActualizacion_retornarFalso(){
        JugadorService jugadorService = new JugadorService();
        assertFalse(jugadorService.validarDorsalParaActualizar(dorsal, equipo, cedulaJugadorActual));
    }

}
