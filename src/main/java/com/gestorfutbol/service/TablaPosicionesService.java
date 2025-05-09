package com.gestorfutbol.service;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.dao.TablaPosicionesDAO;
import com.gestorfutbol.dao.TorneoDAO;
import com.gestorfutbol.dto.TablaPosicionesDTO;
import com.gestorfutbol.entity.Equipo;
import com.gestorfutbol.entity.Partido;
import com.gestorfutbol.entity.TablaPosiciones;
import com.gestorfutbol.entity.Torneo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TablaPosicionesService {
    private final TablaPosicionesDAO tablaPosicionesDAO;

    public TablaPosicionesService() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        this.tablaPosicionesDAO = new TablaPosicionesDAO(sessionFactory);
    }

    public List<TablaPosicionesDTO> obtenerTablaPorTorneo(int idTorneo) {
        List<TablaPosiciones> registros = tablaPosicionesDAO.obtenerPorTorneo(idTorneo);
        List<TablaPosicionesDTO> registrosDTO = new ArrayList<>();

        for (TablaPosiciones tp : registros) {
            TablaPosicionesDTO dto = new TablaPosicionesDTO(
                    tp.getIdTablaPosicion(),
                    tp.getEquipo(),
                    tp.getTorneo(),
                    tp.getPuntosAcumulados(),
                    tp.getPartidosJugados(),
                    tp.getPartidosGanados(),
                    tp.getPartidosEmpatados(),
                    tp.getPartidosPerdidos(),
                    tp.getGolesAFavor(),
                    tp.getGolesEnContra(),
                    tp.getDiferenciaGoles(),
                    tp.getFechaActualizacion()
            );
            registrosDTO.add(dto);
        }
        return registrosDTO;
    }

    public void crearRegistro(int idEquipo, int idTorneo) {
        Torneo torneo = new Torneo();
        torneo.setIdTorneo(idTorneo);

        Equipo equipo = new Equipo();
        equipo.setIdEquipo(idEquipo);

        TablaPosiciones tp = new TablaPosiciones();
        tp.setTorneo(torneo);
        tp.setEquipo(equipo);
        tp.setPuntosAcumulados(0);
        tp.setPartidosJugados(0);
        tp.setPartidosGanados(0);
        tp.setPartidosEmpatados(0);
        tp.setPartidosPerdidos(0);
        tp.setGolesAFavor(0);
        tp.setGolesEnContra(0);
        tp.setDiferenciaGoles(0);
        tp.setFechaActualizacion(new Date());

        tablaPosicionesDAO.guardar(tp);
    }
    public List<Torneo> obtenerTorneos() {
        return tablaPosicionesDAO.listarTorneos();
    }

    public void actualizarEquipoEnTabla(Partido partido){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            System.out.println("entrando en la tabla.....");
            Transaction tx = session.beginTransaction();
            if("Finalizado".equalsIgnoreCase(partido.getEstado())){
                System.out.println("modo finalizado................");
                TablaPosiciones localPosicion = buscarTablaPosiciones(session, partido.getTorneo().getIdTorneo(),
                                                                               partido.getEquipoLocal().getIdEquipo());
                TablaPosiciones visitantePosicion = buscarTablaPosiciones(session, partido.getTorneo().getIdTorneo(),
                                                                                   partido.getEquipoVisita().getIdEquipo());
                if (localPosicion != null && visitantePosicion != null) {
                    // Actualizar partidos jugados
                    localPosicion.setPartidosJugados(localPosicion.getPartidosJugados() + 1);
                    visitantePosicion.setPartidosJugados(visitantePosicion.getPartidosJugados() + 1);

                    int golesLocal = partido.getGolesLocal();
                    int golesVisitante = partido.getGolesVisita();

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
                    tx.commit();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private TablaPosiciones buscarTablaPosiciones(Session session, int idTorneo, int idEquipo) {
        String hql = "FROM TablaPosiciones tp WHERE tp.torneo.idTorneo = :idTorneo AND tp.equipo.idEquipo = :idEquipo";
        Query<TablaPosiciones> query = session.createQuery(hql, TablaPosiciones.class);
        query.setParameter("idTorneo", idTorneo);
        query.setParameter("idEquipo", idEquipo);
        return query.uniqueResult();
    }

}
