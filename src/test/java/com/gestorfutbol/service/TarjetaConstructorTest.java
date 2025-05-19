package com.gestorfutbol.service;

import com.gestorfutbol.entity.Jugador;
import com.gestorfutbol.entity.Partido;
import com.gestorfutbol.entity.Tarjeta;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(value = Parameterized.class)
public class TarjetaConstructorTest {
    private String tipo;
    private String motivo;
    private Partido partido;
    private Jugador jugador;


    @Parameterized.Parameters
    public static Iterable<Object[]> parameters() {

        List<Object[]> objects = new ArrayList<Object[]>();

        objects.add(new Object[]{null, "patada", new Partido(), new Jugador()});
        objects.add(new Object[]{"ROJA", null, new Partido(), new Jugador()});
        objects.add(new Object[]{"AMARILLA", "codazo", null, new Jugador()});
        objects.add(new Object[]{"AMARILLA", "patada", new Partido(), null});
        objects.add(new Object[]{null, null, null, null});

        return objects;
    }

    public TarjetaConstructorTest(String tipo, String motivo, Partido partido, Jugador jugador) {
        this.tipo = tipo;
        this.motivo = motivo;
        this.partido = partido;
        this.jugador = jugador;
    }

    @Test
    public void cuando_algunArgumentoEsNuloEnElContructor_entonces_lanzarExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Tarjeta(tipo, motivo, partido, jugador);
        });
    }
}
