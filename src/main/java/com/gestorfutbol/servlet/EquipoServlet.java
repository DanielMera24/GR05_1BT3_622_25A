package com.gestorfutbol.servlet;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.entity.Equipo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;


@WebServlet("/crearEquipo")
public class EquipoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Creando Equipo");

        String nombreEquipo = request.getParameter("nombreNuevoEquipo");
        String inicialesEquipo = request.getParameter("inicialesNuevoEquipo");
        String ciudadEquipo = request.getParameter("ciudadNuevoEquipo");
        String estadioEquipo = request.getParameter("estadioNuevoEquipo");
        int idTorneo = Integer.parseInt(request.getParameter("torneoPerteneciente"));

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        com.gestorfutbol.entity.Torneo torneo = session.get(com.gestorfutbol.entity.Torneo.class, idTorneo);

        Equipo equipo = new Equipo();
        equipo.setCiudad(ciudadEquipo);
        equipo.setNombre(nombreEquipo);
        equipo.setEstadio(estadioEquipo);
        equipo.setTorneo(torneo);

        session.persist(equipo);
        tx.commit();
        session.close();

        response.sendRedirect(request.getContextPath() + "/mostrarEquipos");
    }
}


