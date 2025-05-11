package com.gestorfutbol.servlet;

import com.gestorfutbol.dto.EquipoDTO;
import com.gestorfutbol.dto.JugadorDTO;
import com.gestorfutbol.service.EquipoService;
import com.gestorfutbol.service.JugadorService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/jugadores")
public class JugadorServlet extends HttpServlet {

    private JugadorService jugadorService;
    private EquipoService equipoService;

    @Override
    public void init() throws ServletException {
        jugadorService = new JugadorService();
        equipoService = new EquipoService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String equipoIdStr = request.getParameter("equipoId");

            List<JugadorDTO> jugadores;
            List<EquipoDTO> equipos = equipoService.listarEquipos();

            if (equipoIdStr != null && !equipoIdStr.isEmpty()) {
                int equipoId = Integer.parseInt(equipoIdStr);
                jugadores = jugadorService.listarJugadoresPorEquipo(equipoId);
            } else {
                jugadores = jugadorService.listarJugadores();
            }

            request.setAttribute("jugadores", jugadores);
            request.setAttribute("equipos", equipos);
            request.setAttribute("equipoSeleccionado", equipoIdStr);

            request.getRequestDispatcher("/html/jugadores.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error al procesar la solicitud", e);
        }
    }


}