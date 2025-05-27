package com.gestorfutbol.service;

import com.gestorfutbol.dao.interfaces.DetallePartidoDAO;
import com.gestorfutbol.entity.DetallePartido;
import com.gestorfutbol.entity.Equipo;
import com.gestorfutbol.entity.Gol;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class DetallePartidoService {
    private DetallePartidoDAO detallePartidoDAO;

    public DetallePartidoService(DetallePartidoDAO detallePartidoDAO) {
        this.detallePartidoDAO = detallePartidoDAO;
    }



    public DetallePartidoService() {

    }
    public boolean guardarDetalles(List<DetallePartido> detalles) {
        if (detalles == null || detalles.isEmpty()) {
            return false;
        }

        if (!validarCapitanesPorEquipo(detalles)) {
            return false;
        }

        if (!cumpleEquipoNumeroDeJugadores(detalles)) {
            return false;
        }

        if (poseeJugadoresDuplicados(detalles)) {

            return false;
        }

        if (!partidoNoFinalizado(detalles)) {
            return false;
        }

        for (DetallePartido detalle : detalles) {
            if (detalle.getGoles() != null) {
                for (Gol gol : detalle.getGoles()) {
                    if (!validarMinutoGol(gol.getMinuto())) {
                        return false;
                    }
                }
            }
        }

        for (DetallePartido detalle : detalles) {
            if (marcoJugadorMismoMinuto(detalle)) {
                return false;
            }
        }

        // Validar que no se registre un jugador dos veces
        if (jugadorYaHaSidoRegistrado(detalles)) {
            return false;
        }

        // Si todas las validaciones pasaron, guardar todos
        for (DetallePartido detalle : detalles) {
            boolean guardado = detallePartidoDAO.guardar(detalle);
            if (!guardado) {
                return false;
            }
        }


        return true;
    }



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




    public boolean validarMinutoGol(int minuto) {
        if (minuto <= 0){
            return false;
        }
        if (minuto > 90){
            return false;
        }

        return true;
    }

    public boolean marcoJugadorMismoMinuto(DetallePartido detallePartido) {
        Set<Integer> minutosRegistrados = new HashSet<>();

        for (int i = 0; i < detallePartido.getGoles().size(); i++) {
            if (!minutosRegistrados.add(detallePartido.getGoles().get(i).getMinuto())) {
                return true;
            }
        }
        return false;
    }

    public boolean jugadorYaHaSidoRegistrado(List<DetallePartido> detallePartidos) {
        Set<String> cedulaJugadoresRegistrado = new HashSet<>();

        for (int i = 0; i < detallePartidos.size(); i++) {
            if (!cedulaJugadoresRegistrado.add(detallePartidos.get(i).getJugador().getCedula())) {
                return true;
            }
        }
        return false;
    }

    public boolean partidoNoFinalizado(List<DetallePartido> detallePartidos) {
        for (int i = 0; i < detallePartidos.size(); i++) {
            if ("Finalizado".equals(detallePartidos.get(i).getPartido().getEstado())) {
                return false;
            }
        }
        return true;
    }







}
