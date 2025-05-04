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

        boolean creado = torneoService.crearTorneo(nombre, fechaInicioStr);

        if (!creado) {
            request.setAttribute("errorMensaje", "Ya existe un torneo con ese nombree.");
            doGet(request, response);
            return;
        }


        response.sendRedirect(request.getContextPath() + "/listarTorneos");
    }
}
