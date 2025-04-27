package com.gestorfutbol.servlet;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.entity.Equipo;
import com.gestorfutbol.service.ValidadorEquipo;
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

        // Obtener los parámetros del formulario
        String nombreEquipo = request.getParameter("nombreNuevoEquipo");
        String inicialesEquipo = request.getParameter("inicialesNuevoEquipo");
        String ciudadEquipo = request.getParameter("ciudadNuevoEquipo");
        String estadioEquipo = request.getParameter("estadioNuevoEquipo");
        int idTorneo = Integer.parseInt(request.getParameter("torneoPerteneciente"));

        // Crear el validador de equipo
        ValidadorEquipo validador = new ValidadorEquipo();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            // Verificar si ya existe un equipo con el mismo nombre
            if (validador.equipoYaExiste(nombreEquipo, session)) {
                // Si ya existe, devolver un código de estado HTTP 400 (Bad Request)
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
                response.getWriter().write("Error: Ya existe un equipo con ese nombre.");
                return; // No continuar con la persistencia
            }

            // Si no existe, proceder con la creación del equipo
            Transaction tx = session.beginTransaction();

            // Obtener el torneo asociado al equipo
            com.gestorfutbol.entity.Torneo torneo = session.get(com.gestorfutbol.entity.Torneo.class, idTorneo);

            // Crear el nuevo equipo
            Equipo equipo = new Equipo();
            equipo.setCiudad(ciudadEquipo);
            equipo.setNombre(nombreEquipo);
            equipo.setEstadio(estadioEquipo);
            equipo.setTorneo(torneo);

            // Persistir el equipo en la base de datos
            session.persist(equipo);
            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Después de guardar, redirigir al GET para mostrar los equipos
        response.sendRedirect(request.getContextPath() + "/mostrarEquipos");
    }
}


