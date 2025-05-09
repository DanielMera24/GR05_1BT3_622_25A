package com.gestorfutbol.servlet;

import com.gestorfutbol.dto.TablaPosicionesDTO;
import com.gestorfutbol.dto.TorneoDTO;
import com.gestorfutbol.service.TablaPosicionesService;
import com.gestorfutbol.service.TorneoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/mostrarTablaPosiciones")
public class TablaPosicionesServlet extends HttpServlet {
    private TablaPosicionesService tablaPosicionesService;
    private TorneoService torneoService;
    @Override
    public void init() throws ServletException {
        tablaPosicionesService = new TablaPosicionesService();
        torneoService = new TorneoService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idTorneoStr = request.getParameter("torneoId");
        Integer torneoSeleccionado = null;

        List<TorneoDTO> torneosDTO = torneoService.listarTorneos();
        request.setAttribute("torneos", torneosDTO);

        if (idTorneoStr != null && !idTorneoStr.isEmpty()) {
            try {
                torneoSeleccionado = Integer.parseInt(idTorneoStr);
                List<TablaPosicionesDTO> tablaPosiciones =
                        tablaPosicionesService.obtenerTablaPorTorneo(torneoSeleccionado);

                request.setAttribute("tablaPosiciones", tablaPosiciones);
            } catch (NumberFormatException e) {
                request.setAttribute("tablaPosiciones", null);
            }
        }

        request.setAttribute("torneoSeleccionado", torneoSeleccionado);
        request.getRequestDispatcher("/html/tabla.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idEquipo = Integer.parseInt(request.getParameter("idEquipo"));
        int idTorneo = Integer.parseInt(request.getParameter("idTorneo"));

        tablaPosicionesService.crearRegistro(idEquipo, idTorneo);

        response.setStatus(HttpServletResponse.SC_CREATED);
    }
}
