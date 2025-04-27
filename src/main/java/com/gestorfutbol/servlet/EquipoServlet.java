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
        System.out.println("CREANDO EQUIPO-CREANDO EQUIPO-CREANDO EQUIPO-CREANDO EQUIPO");
        String nombreEquipo = request.getParameter("nombreNuevoEquipo");
        String inicialesEquipo = request.getParameter("inicialesNuevoEquipo");
        String ciudadEquipo = request.getParameter("ciudadNuevoEquipo");
        String estadioEquipo = request.getParameter("estadioNuevoEquipo");

        System.out.println(nombreEquipo);
        System.out.println(ciudadEquipo);
        System.out.println(estadioEquipo);
        System.out.println(inicialesEquipo);

        Equipo equipo = new Equipo();
        equipo.setCiudad(ciudadEquipo);
        equipo.setNombre(nombreEquipo);
        equipo.setEstadio(estadioEquipo);

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(equipo);
        tx.commit();
        session.close();

        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
