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
import java.util.Date;
import java.util.List;

@WebServlet("/partidos")
public class PartidoServlet extends HttpServlet {
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










    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("CREANDO PARTIDITO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");

        String fechaStr = request.getParameter("fecha"); // Ejemplo formato "2024-04-27"
        String estadio = request.getParameter("estadio");
        int jornadaActual = Integer.parseInt(request.getParameter("jornada"));
        int torneo = Integer.parseInt(request.getParameter("torneo"));
        int equipoLocalId = Integer.parseInt(request.getParameter("equipoLocal"));
        int equipoVisitaId = Integer.parseInt(request.getParameter("equipoVisitante"));

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        // Buscar equipos
        Equipo equipoVisita = session.get(Equipo.class, equipoVisitaId);
        Equipo equipoLocal = session.get(Equipo.class, equipoLocalId);

        Partido partido = new Partido();
        partido.setEquipoVisita(equipoVisita);
        partido.setEquipoLocal(equipoLocal);
        partido.setJornadaActual(jornadaActual);
        partido.setEstado("Pendiente");

        // Parsear la fecha si quieres guardarla como java.util.Date
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            Date fechaDate = sdf.parse(fechaStr);
            partido.setFechaPartido(fechaDate);
        } catch (Exception e) {
            e.printStackTrace();
            // Puedes manejar errores de fecha aquí si quieres
        }

        // También debes asociarlo a un torneo si tu entidad Partido tiene relación con Torneo
        Torneo torneoEntidad = session.get(Torneo.class, torneo);
        partido.setTorneo(torneoEntidad);

        // Guardar el partido
        session.persist(partido);
        tx.commit();
        session.close();

        System.out.println("Partido guardado: " + partido);

        response.sendRedirect(request.getContextPath() + "/partidos");
    }
}
