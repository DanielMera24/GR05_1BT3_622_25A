package com.gestorfutbol.controller;

import com.gestorfutbol.entity.Torneo;
import com.gestorfutbol.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/torneos")
public class TorneoServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // Obtener todos los torneos de la base de datos
        Query<Torneo> query = session.createQuery("FROM Torneo", Torneo.class);
        List<Torneo> torneos = query.list();

        session.getTransaction().commit();
        session.close();

        // Pasar los torneos al JSP
        request.setAttribute("torneos", torneos);

        // Redirigir a la página JSP para mostrar los torneos
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }
}
