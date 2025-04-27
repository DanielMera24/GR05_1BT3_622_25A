package com.gestorfutbol.servlet;

import com.gestorfutbol.entity.Equipo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/mostrarEquipos")
public class MostrarEquipoServlet extends HttpServlet {

    @PersistenceUnit(unitName = "GestorFutbolPU")
    private EntityManagerFactory emf;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = emf.createEntityManager();
        List<Equipo> equipos = em.createQuery("SELECT e FROM Equipo e", Equipo.class)
                .getResultList();
        em.close();

        request.setAttribute("equipos", equipos);
        // <-- Aquí ajustamos AL DIRECTORIO real de tu JSP:
        request.getRequestDispatcher("/html/equipos.jsp")
                .forward(request, response);
    }
}
