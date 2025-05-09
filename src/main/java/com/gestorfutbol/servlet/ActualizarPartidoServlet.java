package com.gestorfutbol.servlet;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.entity.Partido;
import com.gestorfutbol.entity.TablaPosiciones;
import com.gestorfutbol.service.EquipoService;
import com.gestorfutbol.service.PartidoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;

@WebServlet("/actualizarPartido")
public class ActualizarPartidoServlet extends HttpServlet {

    PartidoService partidoService = new PartidoService();
    @Override
    public void init() throws ServletException {
        partidoService = new PartidoService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String estado = request.getParameter("estadoPartido");
        int golesLocal = Integer.parseInt(request.getParameter("golesLocal"));
        int golesVisitante = Integer.parseInt(request.getParameter("golesVisitante"));
        int idPartido = Integer.parseInt(request.getParameter("idPartido"));

        Partido partido = new Partido();
        partido.setIdPartido(idPartido);
        partido.setEstado(estado);
        partido.setGolesLocal(golesLocal);
        partido.setGolesVisita(golesVisitante);
        partidoService.actualizarPartido(partido);

        response.sendRedirect(request.getContextPath() + "/partidos");
    }

    private TablaPosiciones buscarTablaPosiciones(Session session, int idTorneo, int idEquipo) {
        String hql = "FROM TablaPosiciones tp WHERE tp.torneo.idTorneo = :idTorneo AND tp.equipo.idEquipo = :idEquipo";
        Query<TablaPosiciones> query = session.createQuery(hql, TablaPosiciones.class);
        query.setParameter("idTorneo", idTorneo);
        query.setParameter("idEquipo", idEquipo);
        return query.uniqueResult();
    }
}
