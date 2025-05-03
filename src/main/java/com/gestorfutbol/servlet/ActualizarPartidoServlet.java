package com.gestorfutbol.servlet;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.entity.Partido;
import com.gestorfutbol.entity.TablaPosiciones;
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
                // Guardar resultado
                partido.setGolesLocal(golesLocal);
                partido.setGolesVisita(golesVisitante);
                partido.setEstado(estado);
                session.update(partido);

                // Solo si el partido está Finalizado, actualizamos tabla posiciones
                if ("Finalizado".equalsIgnoreCase(estado)) {
                    int idEquipoLocal = partido.getEquipoLocal().getIdEquipo();
                    int idEquipoVisitante = partido.getEquipoVisita().getIdEquipo();
                    int idTorneo = partido.getTorneo().getIdTorneo();

                    // Buscar tabla de posiciones del equipo local
                    TablaPosiciones localPosicion = buscarTablaPosiciones(session, idTorneo, idEquipoLocal);
                    // Buscar tabla de posiciones del equipo visitante
                    TablaPosiciones visitantePosicion = buscarTablaPosiciones(session, idTorneo, idEquipoVisitante);

                    if (localPosicion != null && visitantePosicion != null) {
                        // Actualizar partidos jugados
                        localPosicion.setPartidosJugados(localPosicion.getPartidosJugados() + 1);
                        visitantePosicion.setPartidosJugados(visitantePosicion.getPartidosJugados() + 1);

                        // Actualizar goles a favor y en contra
                        localPosicion.setGolesAFavor(localPosicion.getGolesAFavor() + golesLocal);
                        localPosicion.setGolesEnContra(localPosicion.getGolesEnContra() + golesVisitante);
                        visitantePosicion.setGolesAFavor(visitantePosicion.getGolesAFavor() + golesVisitante);
                        visitantePosicion.setGolesEnContra(visitantePosicion.getGolesEnContra() + golesLocal);

                        // Calcular diferencia de goles
                        localPosicion.setDiferenciaGoles(localPosicion.getGolesAFavor() - localPosicion.getGolesEnContra());
                        visitantePosicion.setDiferenciaGoles(visitantePosicion.getGolesAFavor() - visitantePosicion.getGolesEnContra());

                        // Determinar resultado
                        if (golesLocal > golesVisitante) {
                            // Gana local
                            localPosicion.setPartidosGanados(localPosicion.getPartidosGanados() + 1);
                            visitantePosicion.setPartidosPerdidos(visitantePosicion.getPartidosPerdidos() + 1);
                            localPosicion.setPuntosAcumulados(localPosicion.getPuntosAcumulados() + 3);
                        } else if (golesVisitante > golesLocal) {
                            // Gana visitante
                            visitantePosicion.setPartidosGanados(visitantePosicion.getPartidosGanados() + 1);
                            localPosicion.setPartidosPerdidos(localPosicion.getPartidosPerdidos() + 1);
                            visitantePosicion.setPuntosAcumulados(visitantePosicion.getPuntosAcumulados() + 3);
                        } else {
                            // Empate
                            localPosicion.setPartidosEmpatados(localPosicion.getPartidosEmpatados() + 1);
                            visitantePosicion.setPartidosEmpatados(visitantePosicion.getPartidosEmpatados() + 1);
                            localPosicion.setPuntosAcumulados(localPosicion.getPuntosAcumulados() + 1);
                            visitantePosicion.setPuntosAcumulados(visitantePosicion.getPuntosAcumulados() + 1);
                        }

                        // Guardar cambios
                        session.update(localPosicion);
                        session.update(visitantePosicion);
                    }
                }

                tx.commit();
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

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
