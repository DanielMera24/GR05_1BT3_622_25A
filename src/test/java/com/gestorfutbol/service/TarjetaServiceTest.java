package com.gestorfutbol.service;

import com.gestorfutbol.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TarjetaServiceTest {

    /*1.- Minutos no negativos y menores que 90
      2.- No más de 2 tarjetas amarillas al mismo jugador por partido
      3.- No más de 1 tarjetas rojas al mismo jugador por partido
      4.- Que no se pueda enviar algo que no sea "AMARILLA" o "ROJA" //AZUL (MAL)
*/
    private TarjetaService tarjetaService;
    private Equipo equipoLocal;
    private Equipo equipoVisita;
    private Partido partido;
    private Torneo torneo;

    @BeforeEach
    public void setUp() {
        tarjetaService = new TarjetaService();

        // Configurar torneo
        torneo = new Torneo();
        torneo.setNombre("Liga Profesional");

        // Configurar equipos
        equipoLocal = new Equipo(1, "Barcelona", "Guayaquil", "Monumental", "BSC");
        equipoLocal.setTorneo(torneo);

        equipoVisita = new Equipo(2, "Emelec", "Guayaquil", "Capwell", "CSE");
        equipoVisita.setTorneo(torneo);
        // Configurar jugador

        // Configurar partido
        partido = new Partido();
        partido.setEquipoLocal(equipoLocal);
        partido.setEquipoVisita(equipoVisita);
        partido.setFechaPartido(new java.util.Date());
        partido.setEstado("EN_PROGRESO");
        partido.setJornadaActual(5);
        partido.setTorneo(torneo);
    }

    // Prueba 1: Validar que los minutos no sean negativos
    @Test
    public void cuandoMinutoEsNegativo_entoncesNoPermitirTarjeta() {
        // Arrange (Configuración clara y minimalista)
        Jugador jugador = new Jugador("0503867723", "ASDRUBAL", 25, "DEFENSA", 4);
        int minutoNegativo = -5;
        // Act (Ejecución única y directa)
        boolean resultado = tarjetaService.esMinutoValido(minutoNegativo);
        // Assert (Verificación específica)
        assertFalse(resultado, "Debería rechazar tarjetas con minutos negativos");
    }
    // Prueba 2: Validar que los minutos no sean mayores a 90
    @Test
    public void dado_minutoMayorA90_cuando_guardarTarjeta_entonces_retornarFalso() {
        // Arrange
        Jugador jugador = new Jugador("0503867723", "ASDRUBAL", 25, "DEFENSA", 4);
        int minutoMayorQue90 = 95;
        // Act
        boolean resultado = tarjetaService.esMinutoValido(minutoMayorQue90);

        // Assert
        assertFalse(resultado, "Debería rechazar tarjetas con minutos mayores a 90");
    }

    @Test
    public void dado_tresTarjetasAmarillas_cuando_registrarTarjeta_entonces_retornarFalso() {
        // Arrange
        Jugador jugador = new Jugador("0503867723", "ASDRUBAL", 25, "DEFENSA", 4);
        List<Tarjeta> tarjetas = new ArrayList<>();
        tarjetas.add(new Tarjeta("AMARILLA", "Falta leve", partido, jugador));
        tarjetas.add(new Tarjeta("AMARILLA", "Falta leve", partido, jugador));
        tarjetas.add(new Tarjeta("AMARILLA", "Falta media grave", partido, jugador));
        // Act
        boolean resultado = tarjetaService.esValidoCantidadTarjetasAmarillasAJugador(tarjetas);
        // Assert
        assertFalse(resultado, "Debería rechazar más de 2 tarjetas amarillas al mismo jugador por partido");
    }

   // 3.- No más de 1 tarjetas rojas al mismo jugador por partido

    @Test
    public void dado_dosTarjetasRojasAMismoJugador_cuando_registrarTarjeta_entonces_retornarFalso() {
        // Arrange
        Jugador jugador = new Jugador("0503867723", "ASDRUBAL", 25, "DEFENSA", 4);
        List<Tarjeta> tarjetas = new ArrayList<>();
        tarjetas.add(new Tarjeta("ROJA", "Falta leve", partido, jugador));
        tarjetas.add(new Tarjeta("ROJA", "Falta media grave", partido, jugador));
        // Act
        boolean resultado = tarjetaService.esValidoCantidadRojasAJugador(tarjetas);
        // Assert
        assertFalse(resultado, "Debería rechazar más de 1 tarjeta roja al mismo jugador por partido");
    }

    // 4.- Que no se pueda enviar algo que no sea "AMARILLA" o "ROJA"
    @Test
    public void dado_tipoTarjetaInvalido_cuandoRegistrarTarjeta_entoncesRetornarFalso() {
        Jugador jugador = new Jugador("0503867723", "Cesar", 10, "DEFENSA", 4);
        Tarjeta tarjeta = new Tarjeta("Verde", "Falta leve", partido, jugador);

        boolean resultado = tarjetaService.validarTipoTarjeta(tarjeta);
        assertFalse(resultado, "Debería rechazar tarjetas de tipo inválido");

    }
}
