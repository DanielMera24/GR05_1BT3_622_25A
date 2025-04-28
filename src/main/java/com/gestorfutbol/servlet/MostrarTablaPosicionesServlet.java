package com.gestorfutbol.servlet;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.entity.TablaPosiciones;
import com.gestorfutbol.entity.Torneo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.List;

@WebServlet("/mostrarTablaPosiciones")
public class MostrarTablaPosicionesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Torneo> torneos = null;
        List<TablaPosiciones> tablaPosiciones = null;
        Integer torneoSeleccionado = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            torneos = session.createQuery("FROM Torneo", Torneo.class).list();

            String idTorneoStr = request.getParameter("torneoId");
            if (idTorneoStr != null && !idTorneoStr.isEmpty()) {
                torneoSeleccionado = Integer.parseInt(idTorneoStr);
                tablaPosiciones = session.createQuery(
                                "FROM TablaPosiciones WHERE torneo.idTorneo = :idTorneo ORDER BY puntosAcumulados DESC",
                                TablaPosiciones.class)
                        .setParameter("idTorneo", torneoSeleccionado)
                        .list();
            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("torneos", torneos);
        request.setAttribute("tablaPosiciones", tablaPosiciones);
        request.setAttribute("torneoSeleccionado", torneoSeleccionado);
        request.getRequestDispatcher("/html/tabla.jsp").forward(request, response);
    }
}
