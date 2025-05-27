package com.gestorfutbol.service;

import com.gestorfutbol.entity.Tarjeta;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class TarjetaIdTest {

    private final int id;

    public TarjetaIdTest(int id) {
        this.id = id;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0},
                {1},
                {123},
                {Integer.MAX_VALUE}
        });
    }

    @Test
    public void testSetyGetIdTarjeta() {
        Tarjeta t = new Tarjeta();
        t.setIdTarjeta(id);
        assertEquals(id, t.getIdTarjeta());
    }
}