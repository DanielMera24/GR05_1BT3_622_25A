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


}
