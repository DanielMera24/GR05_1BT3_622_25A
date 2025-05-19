package com.gestorfutbol.servlet;

import com.gestorfutbol.dto.TorneoDTO;
import com.gestorfutbol.service.TorneoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/listarTorneos")
public class TorneoServlet extends HttpServlet {

    private TorneoService torneoService;
    @Override
    public void init() throws ServletException {
        torneoService = new TorneoService();
        System.out.println("Ruta exacta de la BD: " + new java.io.File("GestorTorneos.db").getAbsolutePath());

    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<TorneoDTO> torneos = torneoService.listarTorneos();
        request.setAttribute("torneos", torneos);
        request.getRequestDispatcher("/jsp/listarTorneos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombreTorneo");
        String fechaInicioStr = request.getParameter("fechaInicio");

        if (fechaInicioStr == null || fechaInicioStr.trim().isEmpty()) {
            request.setAttribute("errorMensaje", "Por favor, ingrese una fecha de inicio para el torneo.");
            doGet(request, response);
            return;
        }

        boolean fueCreadoTorneo = torneoService.crearTorneo(nombre, fechaInicioStr);

        if (!fueCreadoTorneo) {
            request.setAttribute("errorMensaje", "Ya existe un torneo con ese nombre.");
            doGet(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/listarTorneos");
    }
}
