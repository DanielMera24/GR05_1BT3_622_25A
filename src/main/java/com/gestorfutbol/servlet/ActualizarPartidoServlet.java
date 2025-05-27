package com.gestorfutbol.servlet;

import com.gestorfutbol.entity.*;
import com.gestorfutbol.service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/actualizarPartido")
public class ActualizarPartidoServlet extends HttpServlet {

    PartidoService partidoService;
    TarjetaService tarjetaService;
    JugadorService jugadorService;
    DetallePartidoService detallePartidoService;
    EquipoService equipoService;

    @Override
    public void init() throws ServletException {
        partidoService = new PartidoService();
        tarjetaService = new TarjetaService();
        jugadorService = new JugadorService();
        detallePartidoService = new DetallePartidoService();
        equipoService = new EquipoService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String estado = request.getParameter("estadoPartido");
        int golesLocal = Integer.parseInt(request.getParameter("golesLocal"));
        int golesVisitante = Integer.parseInt(request.getParameter("golesVisitante"));
        int idPartido = Integer.parseInt(request.getParameter("idPartido"));

        // Obtener partido
        Partido partido = partidoService.obtenerPartidoPorId(idPartido);

        // Procesar jugadores participantes con sus goles (DetallePartido + Gol)
        String cantidadJugadoresStr = request.getParameter("cantidadJugadores");
        if (cantidadJugadoresStr != null && !cantidadJugadoresStr.isEmpty()) {
            int cantidadJugadores = Integer.parseInt(cantidadJugadoresStr);

            List<DetallePartido> detallesPartido = new ArrayList<>();

            for (int i = 0; i < cantidadJugadores; i++) {
                String jugadorIdStr = request.getParameter("jugador_" + i + "_id");
                String equipoIdStr = request.getParameter("jugador_" + i + "_equipoId");
                String dorsalStr = request.getParameter("jugador_" + i + "_dorsal");
                String esCapitanStr = request.getParameter("jugador_" + i + "_capitan");

                if (jugadorIdStr != null && equipoIdStr != null && dorsalStr != null) {
                    DetallePartido detalle = new DetallePartido();

                    // Obtener jugador y equipo
                    Jugador jugador = jugadorService.obtenerJugadorPorId(Integer.parseInt(jugadorIdStr));
                    Equipo equipo = equipoService.obtenerEquipoPorId(Integer.parseInt(equipoIdStr));

                    detalle.setJugador(jugador);
                    detalle.setPartido(partido);
                    detalle.setEquipo(equipo);
                    detalle.setDorsal(Integer.parseInt(dorsalStr));
                    detalle.setEsCapitan("true".equals(esCapitanStr));

                    // Procesar goles de este jugador
                    List<Gol> golesJugador = new ArrayList<>();
                    String cantidadGolesJugadorStr = request.getParameter("jugador_" + i + "_cantidadGoles");
                    if (cantidadGolesJugadorStr != null && !cantidadGolesJugadorStr.isEmpty()) {
                        int cantidadGolesJugador = Integer.parseInt(cantidadGolesJugadorStr);

                        for (int j = 0; j < cantidadGolesJugador; j++) {
                            String minutoGolStr = request.getParameter("jugador_" + i + "_gol_" + j + "_minuto");

                            if (minutoGolStr != null && !minutoGolStr.isEmpty()) {
                                Gol gol = new Gol();
                                gol.setMinuto(Integer.parseInt(minutoGolStr));
                                gol.setDetallePartido(detalle);
                                golesJugador.add(gol);
                            }
                        }
                    }

                    System.out.println("➡ Detalle creado para jugador: " + jugador.getNombre() + " (ID: " + jugador.getIdJugador() + ")");
                    System.out.println("   Equipo: " + equipo.getNombre());
                    System.out.println("   Dorsal: " + detalle.getDorsal());
                    System.out.println("   Capitán: " + detalle.isCapitan());

                    if (golesJugador.isEmpty()) {
                        System.out.println("   ⚠ No se registraron goles para este jugador.");
                    } else {
                        for (Gol g : golesJugador) {
                            System.out.println("   🟢 Gol minuto: " + g.getMinuto());
                        }
                    }

                    detalle.setGoles(golesJugador);
                    detallesPartido.add(detalle);
                }
            }

            System.out.println("TESTEANDO");
            // Guardar detalles del partido (incluye goles internamente)
            if (!detallesPartido.isEmpty()) {
                System.out.println("TESTEANDO2");
                detallePartidoService.guardarDetalles(detallesPartido);
            }
        }

        // Procesar tarjetas (código existente)
        String cantidadTarjetasStr = request.getParameter("cantidadTarjetas");
        if (cantidadTarjetasStr != null && !cantidadTarjetasStr.isEmpty()) {
            int cantidadTarjetas = Integer.parseInt(cantidadTarjetasStr);

            List<Tarjeta> tarjetas = new ArrayList<>();

            for (int i = 0; i < cantidadTarjetas; i++) {
                String tipo = request.getParameter("tarjeta_" + i + "_tipo");
                String jugadorIdStr = request.getParameter("tarjeta_" + i + "_jugadorId");
                String minutoStr = request.getParameter("tarjeta_" + i + "_minuto");
                String motivo = request.getParameter("tarjeta_" + i + "_motivo");

                if (tipo != null && jugadorIdStr != null && minutoStr != null && motivo != null) {
                    Tarjeta tarjeta = new Tarjeta();
                    tarjeta.setTipoTarjeta(tipo);
                    tarjeta.setMotivo(motivo);
                    tarjeta.setMinuto(Integer.parseInt(minutoStr));

                    // Obtener jugador
                    Jugador jugador = jugadorService.obtenerJugadorPorId(Integer.parseInt(jugadorIdStr));

                    tarjeta.setJugador(jugador);
                    tarjeta.setPartido(partido);

                    tarjetas.add(tarjeta);
                }
            }

            // Guardar las tarjetas
            if (!tarjetas.isEmpty()) {
                tarjetaService.guardarTarjeta(tarjetas);
            }
        }

        // Actualizar partido
        partidoService.actualizarPartido(partido, estado, golesLocal, golesVisitante);

        response.sendRedirect(request.getContextPath() + "/partidos");
    }
}