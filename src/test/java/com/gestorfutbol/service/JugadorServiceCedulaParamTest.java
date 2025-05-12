package com.gestorfutbol.service;

import com.gestorfutbol.entity.Equipo;
import com.gestorfutbol.entity.Jugador;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(value = Parameterized.class)
public class JugadorServiceCedulaParamTest {
    private String cedula;
    private Equipo equipo;

    @Parameterized.Parameters
    public static Iterable<Object[]> parameters() {
        List<Equipo> equipos = new ArrayList<>();
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Jugador("1234567890"));
        jugadores.add(new Jugador("0987654321"));
        jugadores.add(new Jugador("1734557891"));
        jugadores.add(new Jugador("4987644321"));
        Equipo equipo = new Equipo();
        equipo.setJugadores(jugadores);
        List<Object[]> objects = new ArrayList<Object[]>();
        objects.add(new Object[]{"1234567890", equipo});
        objects.add(new Object[]{"0987654321", equipo});
        objects.add(new Object[]{"1734557891", equipo});
        objects.add(new Object[]{"4987644321", equipo});
        return objects;
    }

    public JugadorServiceCedulaParamTest(String cedula, Equipo equipo) {
        this.cedula = cedula;
        this.equipo = equipo;
    }
    @Test
    public void cuando_ExisteJugador_porCedula_enEquipo_entonces_noRetornarNull(){
        JugadorService jugadorService = new JugadorService();
        Jugador jugador  = jugadorService.buscarJugadorEnEquipoPorCedula(cedula, equipo);

        assertNotNull(jugador);
    }
}
