package com.gestorfutbol.servlet;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.entity.Partido;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;

@WebServlet("/actualizarPartido")
public class ActualizarPartidoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idPartido = Integer.parseInt(request.getParameter("idPartido"));
        int golesLocal = Integer.parseInt(request.getParameter("golesLocal"));
        int golesVisitante = Integer.parseInt(request.getParameter("golesVisitante"));
        String estado = request.getParameter("estadoPartido");

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        try {
            Partido partido = session.get(Partido.class, idPartido);
            if (partido != null) {
                partido.setGolesLocal(golesLocal);
                partido.setGolesVisita(golesVisitante);
                partido.setEstado(estado);
                session.update(partido);
                tx.commit();
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        response.sendRedirect(request.getContextPath() + "/mostrarOpcionesPartido");
    }
}
