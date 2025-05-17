package com.gestorfutbol.service;

import com.gestorfutbol.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void dado_minutoNegativo_cuando_asignarTarjeta_entonces_retornarFalso() {
        // Arrange
        Jugador jugador = new Jugador("1715435234", "Juan Pérez", 25, "Defensa", 4);
        jugador.setEquipo(equipoLocal);

        int minuto = -5;
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            tarjetaService.asignarTarjeta(jugador, partido, TipoTarjeta.AMARILLA, "Falta táctica", minuto);
        }, "No se puede asignar una tarjeta en un minuto negativo");
    }

}
