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

@WebServlet("/crearPartido")
public class PartidoServlet extends HttpServlet {
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

        response.sendRedirect(request.getContextPath() + "/mostrarOpcionesPartido");
    }
}
