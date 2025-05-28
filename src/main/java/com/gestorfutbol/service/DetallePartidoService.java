package com.gestorfutbol.service;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.dao.implementation.DetallePartidoDAOImpl;
import com.gestorfutbol.dao.implementation.GolDAOImpl;
import com.gestorfutbol.dao.interfaces.DetallePartidoDAO;
import com.gestorfutbol.dao.interfaces.GolDAO;
import com.gestorfutbol.dto.DetallePartidoDTO;
import com.gestorfutbol.entity.DetallePartido;
import com.gestorfutbol.entity.Equipo;
import com.gestorfutbol.entity.Gol;
import org.hibernate.SessionFactory;

import java.util.*;
import java.util.stream.Collectors;

public class DetallePartidoService {
    private DetallePartidoDAO detallePartidoDAO;
    private GolDAO golDAO;

    public DetallePartidoService() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        this.detallePartidoDAO = new DetallePartidoDAOImpl(sessionFactory);
        this.golDAO = new GolDAOImpl(sessionFactory);
    }

    public DetallePartidoService(DetallePartidoDAO mockDetallePartidoDAO) {
        this.detallePartidoDAO = mockDetallePartidoDAO;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        this.golDAO = new GolDAOImpl(sessionFactory);
    }

    public boolean guardarDetalles(List<DetallePartido> detalles) {
        if (detalles == null || detalles.isEmpty()) {
            System.out.println("Error: Lista de detalles del partido no puede ser nula o vacía");
            return false;
        }


        /*if (!cumpleEquipoNumeroDeJugadores(detalles)) {
            return false;
        }
        */

        if (poseeJugadoresDuplicados(detalles)) {
            System.out.println("Error: Hay jugadores duplicados en los detalles del partido");
            return false;
        }

        if (!partidoNoFinalizado(detalles)) {
            System.out.println("Error: El partido ya ha sido finalizado, no se pueden registrar más detalles");
            return false;
        }

        // Validar goles antes de guardar
        for (DetallePartido detalle : detalles) {
            if (detalle.getGoles() != null) {
                for (Gol gol : detalle.getGoles()) {
                    if (!validarMinutoGol(gol.getMinuto())) {
                        System.out.println("Error:  El minuto del gol no es válido: " + gol.getMinuto());
                        return false;
                    }
                }
            }
        }

        for (DetallePartido detalle : detalles) {
            if (detalle.getGoles() != null && marcoJugadorMismoMinuto(detalle)) {
                System.out.println("Error: Un jugador ha marcado más de un gol en el mismo minuto");
                return false;
            }
        }

        // Validar que no se registre un jugador dos veces
        if (jugadorYaHaSidoRegistrado(detalles)) {
            System.out.println("Error: Un jugador ya ha sido registrado en los detalles del partido");
            return false;
        }


        if (!validarCapitanesPorEquipo(detalles)) {
            System.out.println("Error: No se cumple la validación de capitanes por equipo");
            return false;
        }



        // Si todas las validaciones pasaron, guardar detalles y goles
        for (DetallePartido detalle : detalles) {
            // Primero guardar el detalle del partido

            boolean detalleGuardado = detallePartidoDAO.guardar(detalle);
            System.out.println("➡ Guardando DetallePartido con goles:");
            for (Gol gol : detalle.getGoles()) {
                System.out.println("  - Gol minuto: " + gol.getMinuto());
            }

            if (!detalleGuardado) {
                System.out.println("Error: Error al guardar el detalle del partido: " + detalle);
                return false;
            }
            System.out.println("GUARDANDO DETALLE: " + detalle);
        }

        return true;
    }

    public boolean validarCapitanesPorEquipo(List<DetallePartido> jugadoresRegistrados) {
        // Validación de parámetro de entrada
        if (jugadoresRegistrados == null) {
            throw new IllegalArgumentException("La lista de jugadores registrados no puede ser nula");
        }

        // Si no hay jugadores registrados, no hay equipos que validar
        if (jugadoresRegistrados.isEmpty()) {
            return false;
        }
        for (DetallePartido detallePartido: jugadoresRegistrados) {
            System.out.println("Detalle: " + detallePartido.getJugador().getNombre() +
                               ", Equipo: " + detallePartido.getEquipo().getNombre() +
                               ", Capitán: " + detallePartido.isCapitan());
        }

        // Estructuras para el conteo
        Map<Equipo, Integer> conteoCapitanes = new HashMap<>();
        Set<Equipo> equiposPresentes = new HashSet<>();

        // Primera pasada: identificar equipos y contar capitanes
        for (DetallePartido jugador : jugadoresRegistrados) {
            Equipo equipo = jugador.getEquipo();

            equiposPresentes.add(equipo);

            if (jugador.isCapitan()) {
                conteoCapitanes.put(equipo, conteoCapitanes.getOrDefault(equipo, 0) + 1);
            }
        }

        // Segunda pasada: verificar condiciones
        for (Equipo equipo : equiposPresentes) {
            Integer numCapitanes = conteoCapitanes.get(equipo);
            // Verificar que cada equipo tenga exactamente 1 capitán
            if (numCapitanes == null || numCapitanes != 1) {
                return false;
            }
        }

        return true;
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

        // Mapa para rastrear cédulas por equipo
        Map<Equipo, Set<String>> cedulasPorEquipo = new HashMap<>();

        for (DetallePartido detalle : listaJugadores) {
            Equipo equipo = detalle.getEquipo();
            String cedula = detalle.getJugador().getCedula();

            // Inicializar el conjunto de cédulas si es la primera vez que vemos este equipo
            cedulasPorEquipo.putIfAbsent(equipo, new HashSet<>());

            // Intentar agregar la cédula al conjunto del equipo
            if (!cedulasPorEquipo.get(equipo).add(cedula)) {
                // Si no se puede agregar (porque ya existe), tenemos un duplicado
                return true;
            }
        }

        return false;
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
        if (detallePartido.getGoles() == null || detallePartido.getGoles().isEmpty()) {
            return false;
        }

        Set<Integer> minutosRegistrados = new HashSet<>();

        for (Gol gol : detallePartido.getGoles()) {
            if (!minutosRegistrados.add(gol.getMinuto())) {
                return true;
            }
        }
        return false;
    }

    public boolean jugadorYaHaSidoRegistrado(List<DetallePartido> detallePartidos) {
        Set<String> cedulaJugadoresRegistrado = new HashSet<>();

        for (DetallePartido detalle : detallePartidos) {
            if (!cedulaJugadoresRegistrado.add(detalle.getJugador().getCedula())) {
                return true;
            }
        }
        return false;
    }

    public boolean partidoNoFinalizado(List<DetallePartido> detallePartidos) {
        for (DetallePartido detalle : detallePartidos) {
            if ("Finalizado".equals(detalle.getPartido().getEstado())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Obtiene todos los detalles de un partido específico
     */
    public List<DetallePartidoDTO> listarDetallesPorPartido(int idPartido) {
        try {
            List<DetallePartido> detalles = detallePartidoDAO.listarPorPartido(idPartido);
            List<DetallePartidoDTO> detallesDTO = new ArrayList<>();

            for (DetallePartido detalle : detalles) {
                DetallePartidoDTO dto = convertirADTO(detalle);
                detallesDTO.add(dto);
            }

            return detallesDTO;
        } catch (Exception e) {
            System.err.println("Error al listar detalles del partido: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Obtiene los detalles de un equipo específico en un partido
     */
    public List<DetallePartidoDTO> listarDetallesPorPartidoYEquipo(int idPartido, int idEquipo) {
        try {
            List<DetallePartido> detalles = detallePartidoDAO.listarPorPartidoYEquipo(idPartido, idEquipo);
            List<DetallePartidoDTO> detallesDTO = new ArrayList<>();

            for (DetallePartido detalle : detalles) {
                DetallePartidoDTO dto = convertirADTO(detalle);
                detallesDTO.add(dto);
            }

            return detallesDTO;
        } catch (Exception e) {
            System.err.println("Error al listar detalles del partido por equipo: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }



    /**
     * Convierte una entidad DetallePartido a DTO
     */
    private DetallePartidoDTO convertirADTO(DetallePartido detalle) {
        DetallePartidoDTO dto = new DetallePartidoDTO();

        dto.setIdDetallePartido(detalle.getIdDetallePartido());
        dto.setDorsal(detalle.getDorsal());
        dto.setEsCapitan(detalle.isCapitan());

        // Información del jugador
        if (detalle.getJugador() != null) {
            dto.setIdJugador(detalle.getJugador().getIdJugador());
            dto.setNombreJugador(detalle.getJugador().getNombre());
            dto.setPosicionJugador(detalle.getJugador().getPosicion());
        }

        // Información del equipo
        if (detalle.getEquipo() != null) {
            dto.setIdEquipo(detalle.getEquipo().getIdEquipo());
            dto.setNombreEquipo(detalle.getEquipo().getNombre());
            dto.setSiglasEquipo(detalle.getEquipo().getSiglas());
        }

        // Información del partido
        if (detalle.getPartido() != null) {
            dto.setIdPartido(detalle.getPartido().getIdPartido());
        }

        // Contar goles del jugador en este partido
        if (detalle.getGoles() != null) {
            dto.setCantidadGoles(detalle.getGoles().size());
        } else {
            dto.setCantidadGoles(0);
        }

        return dto;
    }
}