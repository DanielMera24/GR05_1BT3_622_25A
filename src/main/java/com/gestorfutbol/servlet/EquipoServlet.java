package com.gestorfutbol.servlet;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.entity.Equipo;
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
import java.util.Date;
import java.util.List;

@WebServlet("/equipos")
public class EquipoServlet extends HttpServlet {

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

        // Verificar si ya existe un equipo con el mismo nombre
        /*
        ValidadorEquipo validador = new ValidadorEquipo();
        if (validador.equipoYaExiste(nombreEquipo, session)) {
            // Si ya existe, devolver un código de estado HTTP 400 (Bad Request)
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            response.getWriter().write("Error: Ya existe un equipo con ese nombre.");
            return; // No continuar con la persistencia
        }
        */

        Equipo equipo = new Equipo();
        equipo.setCiudad(ciudadEquipo);
        equipo.setNombre(nombreEquipo);
        equipo.setEstadio(estadioEquipo);
        equipo.setTorneo(torneo);
        session.persist(equipo);
        // Crear automáticamente la fila en TablaPosiciones
        TablaPosiciones tablaPosiciones = new TablaPosiciones();
        tablaPosiciones.setEquipo(equipo);
        tablaPosiciones.setTorneo(torneo);
        tablaPosiciones.setPuntosAcumulados(0);
        tablaPosiciones.setPartidosJugados(0);
        tablaPosiciones.setPartidosGanados(0);
        tablaPosiciones.setPartidosEmpatados(0);
        tablaPosiciones.setPartidosPerdidos(0);
        tablaPosiciones.setGolesAFavor(0);
        tablaPosiciones.setGolesEnContra(0);
        tablaPosiciones.setDiferenciaGoles(0);
        tablaPosiciones.setFechaActualizacion(new Date());
        session.persist(tablaPosiciones);
        tx.commit();
        session.close();
        response.sendRedirect(request.getContextPath() + "/equipos");
    }
}
