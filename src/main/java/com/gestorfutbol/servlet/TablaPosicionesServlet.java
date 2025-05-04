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

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Equipo equipo = session.get(Equipo.class, idEquipo);
            Torneo torneo = session.get(Torneo.class, idTorneo);

            TablaPosiciones tabla = new TablaPosiciones();
            tabla.setEquipo(equipo);
            tabla.setTorneo(torneo);
            tabla.setPuntosAcumulados(0);
            tabla.setPartidosJugados(0);
            tabla.setPartidosGanados(0);
            tabla.setPartidosEmpatados(0);
            tabla.setPartidosPerdidos(0);
            tabla.setGolesAFavor(0);
            tabla.setGolesEnContra(0);
            tabla.setDiferenciaGoles(0);
            tabla.setFechaActualizacion(new Date());

            session.persist(tabla);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
