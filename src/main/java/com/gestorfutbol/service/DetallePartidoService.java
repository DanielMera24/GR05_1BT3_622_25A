package com.gestorfutbol.service;

import com.gestorfutbol.entity.DetallePartido;
import com.gestorfutbol.entity.Equipo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class DetallePartidoService {
    public boolean validarCapitanesPorEquipo(List<DetallePartido> jugadoresRegistrados) {
        // Validación más estricta del parámetro de entrada
        if (jugadoresRegistrados == null) {
            throw new IllegalArgumentException("La lista de jugadores registrados no puede ser nula");
        }

        if (jugadoresRegistrados.isEmpty()) {
            return false;
        }

        // Mapear el número de capitanes que existe por equipo
        Map<Equipo, Long> conteoCapitanesPorEquipo = jugadoresRegistrados.stream()
                .filter(DetallePartido::isCapitan)
                .collect(Collectors.groupingBy(
                        DetallePartido::getEquipo,
                        Collectors.counting()
                ));

        // Verificar que todos los equipos tengan exactamente un capitán
        boolean todosValidos = conteoCapitanesPorEquipo.values().stream()
                .allMatch(count -> count == 1);

        // Verificar que no haya equipos sin capitanes
        Set<Equipo> equiposConJugadores = jugadoresRegistrados.stream()
                .map(DetallePartido::getEquipo)
                .collect(Collectors.toSet());

        boolean todosEquiposPresentes = equiposConJugadores.stream()
                .allMatch(conteoCapitanesPorEquipo::containsKey);

        return todosValidos && todosEquiposPresentes;

    }

    public boolean cumpleEquipoNumeroDeJugadores(List<DetallePartido> jugadoresRegistrados) {
        // Configuración de límites (pueden ser parámetros o constantes)
        final int MIN_JUGADORES = 3;  // Mínimo para un partido oficial (solo jugadores principales
        final int MAX_JUGADORES = 5; // Máximo para un partido estándar (con suplentes)

        if (jugadoresRegistrados == null || jugadoresRegistrados.isEmpty()) {
            return false;
        }

        // Agrupar y contar jugadores por equipo
        Map<Equipo, Long> conteoPorEquipo = jugadoresRegistrados.stream()
                .collect(Collectors.groupingBy(
                        DetallePartido::getEquipo,
                        Collectors.counting()
                ));

        // Verificar que todos los equipos cumplan con los límites
        return conteoPorEquipo.values().stream()
                .allMatch(count -> count >= MIN_JUGADORES && count <= MAX_JUGADORES);
    }

    public boolean poseeJugadoresDuplicados(List<DetallePartido> listaJugadores) {

        if (listaJugadores == null || listaJugadores.isEmpty()) {
            return false;
        }

        // Agrupar jugadores por equipo y por cédula
        Map<Equipo, Map<String, Long>> conteoPorEquipoYCedula = listaJugadores.stream()
                .collect(Collectors.groupingBy(
                        DetallePartido::getEquipo,
                        Collectors.groupingBy(
                                dp -> dp.getJugador().getCedula(),
                                Collectors.counting()
                        )
                ));

        // Verificar si algún equipo tiene cédulas repetidas
        return conteoPorEquipoYCedula.values().stream()
                .anyMatch(cedulaMap -> cedulaMap.values().stream()
                        .anyMatch(count -> count > 1));
    }


    // Validar que no haya más de un capitan por equipo
    // Validar mínimo y maxim de jugadores por equipo
    // Validar que los equipos tengan el mismo número de jugadores asociados
    // Validar que el registro de jugadores se realice previo al partido.
    // Validar que no existan jugadores duplicados en el registro.

    // Validar el minuto en la cual anoto gol este dentro del rango válido.
    // Validar que un jugador no anota dos goles en el mismo minuto.
    // Validar que no existan goles de diferentes jugadores (equipo) en el mismo minuto.

    // Validar que exista un capitan por equipo





}
