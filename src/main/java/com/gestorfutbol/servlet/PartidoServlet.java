package com.gestorfutbol.servlet;

import com.gestorfutbol.dto.EquipoDTO;
import com.gestorfutbol.dto.JugadorDTO;
import com.gestorfutbol.dto.PartidoDTO;
import com.gestorfutbol.dto.TorneoDTO;
import com.gestorfutbol.service.EquipoService;
import com.gestorfutbol.service.JugadorService;
import com.gestorfutbol.service.PartidoService;
import com.gestorfutbol.service.TorneoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@WebServlet("/partidos")
public class PartidoServlet extends HttpServlet {

    private EquipoService equipoService;

    private PartidoService partidoService;

    private TorneoService torneoService;

    private JugadorService jugadorService;


    @Override
    public void init() throws ServletException {
        equipoService = new EquipoService();
        torneoService = new TorneoService();
        partidoService = new PartidoService();
        jugadorService = new JugadorService();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<EquipoDTO> equipos = null;
        List<TorneoDTO> torneos = null;
        List<PartidoDTO> partidos = null;

        torneos = torneoService.listarTorneos();
        partidos = partidoService.listarPartidos();
        equipos = equipoService.listarEquipos();

        if (equipos != null) {
            for (EquipoDTO equipo : equipos) {
                // List<JugadorDTO> jugadoresEquipo = jugadorService.obtenerJugadoresPorEquipo(equipo.getIdEquipo());
                List<JugadorDTO> jugadoresEquipoT = jugadorService.obtenerJugadoresPorEquipo(equipo.getIdEquipo());
                request.setAttribute("jugadores_" + equipo.getIdEquipo(), jugadoresEquipoT);
            }
        }

        request.setAttribute("equipos", equipos);
        request.setAttribute("torneos", torneos);
        request.setAttribute("partidos", partidos);
        try {
            request.getRequestDispatcher("/html/partidos.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fechaStr = request.getParameter("fecha");
        String estadio = request.getParameter("estadio");
        int jornadaActual = Integer.parseInt(request.getParameter("jornada"));
        String torneo = request.getParameter("torneo");
        String nombreEquipoLocal = request.getParameter("equipoLocal");
        String nombreEquipoVisita = request.getParameter("equipoVisitante");
        PartidoDTO partido = new PartidoDTO(fechaStr, estadio, jornadaActual, nombreEquipoLocal, nombreEquipoVisita, torneo);

        partidoService.crearPartido(partido);
        response.sendRedirect(request.getContextPath() + "/partidos");
    }
}
