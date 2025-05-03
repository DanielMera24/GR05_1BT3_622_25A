package com.gestorfutbol.servlet;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.entity.Equipo;
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

@WebServlet("/mostrarEquipos")
public class EnviadorEquipoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Iniciando doGet para mostrar equipos!!!!!!!!!!!!!!!!!!!!!!!!!");

        List<Equipo> equipos = null;
        List<Torneo> torneos = null; // 👈 agregamos torneos

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            equipos = session.createQuery("FROM Equipo", Equipo.class).list();
            torneos = session.createQuery("FROM Torneo", Torneo.class).list(); // 👈 también consultamos torneos
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("equipos", equipos);
        request.setAttribute("torneos", torneos); // 👈 enviamos torneos al JSP
        request.getRequestDispatcher("/html/equipos.jsp").forward(request, response);
    }
}
