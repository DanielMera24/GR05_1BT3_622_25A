package com.gestorfutbol.dao.interfaces;

import com.gestorfutbol.entity.Jugador;
import com.gestorfutbol.entity.Partido;
import com.gestorfutbol.entity.Tarjeta;
import com.gestorfutbol.service.TarjetaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class TarjetaServiceMockTest {

    private TarjetaDAO mockTarjetaDAO;
    private TarjetaService tarjetaService;
    private Partido partido;
    private Jugador jugador;
    private List<Tarjeta> tarjetasMock;

    @BeforeEach
    public void setUp() {
        mockTarjetaDAO = Mockito.mock(TarjetaDAO.class);

        tarjetaService = new TarjetaService(mockTarjetaDAO);

        partido = new Partido();
        partido.setIdPartido(1);

        jugador = new Jugador("1234567890", "Juan", 25, "Delantero", 10);

        tarjetasMock = new ArrayList<>();
        Jugador jugador2 = new Jugador("0987654321", "Pedro", 28, "Mediocampista", 8);

        Tarjeta tarjeta1 = new Tarjeta("AMARILLA", "Falta grave", partido, jugador);
        tarjeta1.setMinuto(30);

        Tarjeta tarjeta2 = new Tarjeta("ROJA", "Entrada peligrosa", partido, jugador2);
        tarjeta2.setMinuto(75);

        tarjetasMock.add(tarjeta1);
        tarjetasMock.add(tarjeta2);

        when(mockTarjetaDAO.obtenerPorPartido(anyInt())).thenReturn(new ArrayList<>());
    }

    @Test
    public void dado_tarjetasValidas_cuando_guardarTarjeta_entonces_llamarDAO() {
        List<Tarjeta> tarjetas = new ArrayList<>();

        Tarjeta tarjeta1 = new Tarjeta("AMARILLA", "Falta grave", partido, jugador);
        tarjeta1.setMinuto(45);

        Tarjeta tarjeta2 = new Tarjeta("ROJA", "Juego peligroso", partido, jugador);
        tarjeta2.setMinuto(75);

        tarjetas.add(tarjeta1);
        tarjetas.add(tarjeta2);

        when(mockTarjetaDAO.guardarTarjeta(any(Tarjeta.class))).thenReturn(true);

        when(mockTarjetaDAO.obtenerPorPartido(1)).thenReturn(new ArrayList<>());

        boolean resultado = tarjetaService.guardarTarjeta(tarjetas);

        assertTrue(resultado);

        verify(mockTarjetaDAO, times(2)).guardarTarjeta(any(Tarjeta.class));
    }

    @Test
    public void dado_idPartidoValido_cuando_obtenerPorPartido_entonces_retornarListaTarjetas() {
        int idPartido = 1;
        when(mockTarjetaDAO.obtenerPorPartido(idPartido)).thenReturn(tarjetasMock);

        List<Tarjeta> resultado = tarjetaService.obtenerPorPartido(idPartido);

        assertEquals(2, resultado.size());
        assertEquals("AMARILLA", resultado.get(0).getTipoTarjeta());
        assertEquals("ROJA", resultado.get(1).getTipoTarjeta());

        verify(mockTarjetaDAO, times(1)).obtenerPorPartido(idPartido);
    }


}