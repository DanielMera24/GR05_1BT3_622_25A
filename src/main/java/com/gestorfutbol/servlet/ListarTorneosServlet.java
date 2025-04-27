package com.gestorfutbol.servlet;

import com.gestorfutbol.config.HibernateUtil;
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
@WebServlet("/listarTorneos")
public class ListarTorneosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Torneo> torneos = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            torneos = session.createQuery("FROM Torneo", Torneo.class).list();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("torneos", torneos);
        request.getRequestDispatcher("/jsp/listarTorneos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recoger datos del formulario
        String nombre = request.getParameter("nombreTorneo");
        String fechaInicioStr = request.getParameter("fechaInicio");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Torneo nuevoTorneo = new Torneo();
            nuevoTorneo.setNombre(nombre);

            if (fechaInicioStr != null && !fechaInicioStr.isEmpty()) {
                java.sql.Date fechaInicio = java.sql.Date.valueOf(fechaInicioStr); // convierte String a java.sql.Date
                nuevoTorneo.setFechaInicio(fechaInicio);
            }

            nuevoTorneo.setNumFechas(0); // Puedes poner 0 o el valor que decidas manejar

            session.persist(nuevoTorneo);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Después de guardar, redirigir al GET para listar de nuevo
        response.sendRedirect(request.getContextPath() + "/listarTorneos");
    }
}

