package com.gestorfutbol.servlet;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.entity.Equipo;
import com.gestorfutbol.entity.Partido;
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

@WebServlet("/mostrarOpcionesPartido")
public class MostrarPartidoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Equipo> equipos = null;
        List<Torneo> torneos = null;
        List<Partido> partidos = null; // <-- NUEVO

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            equipos = session.createQuery("FROM Equipo", Equipo.class).list();
            torneos = session.createQuery("FROM Torneo", Torneo.class).list();
            partidos = session.createQuery("FROM Partido", Partido.class).list(); // <-- NUEVO
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("equipos", equipos);
        request.setAttribute("torneos", torneos);
        request.setAttribute("partidos", partidos); // <-- NUEVO

        try {
            request.getRequestDispatcher("/html/partidos.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }
}
