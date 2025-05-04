package com.gestorfutbol.servlet;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.dto.TablaPosicionesDTO;
import com.gestorfutbol.entity.Equipo;
import com.gestorfutbol.entity.TablaPosiciones;
import com.gestorfutbol.entity.Torneo;
import com.gestorfutbol.service.TablaPosicionesService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet("/mostrarTablaPosiciones")
public class TablaPosicionesServlet extends HttpServlet {

    private TablaPosicionesService tablaPosicionesService;

    @Override
    public void init() throws ServletException {
        tablaPosicionesService = new TablaPosicionesService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idTorneoStr = request.getParameter("torneoId");
        Integer torneoSeleccionado = null;

        List<Torneo> torneos = tablaPosicionesService.obtenerTorneos();
        request.setAttribute("torneos", torneos);

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
