package com.gestorfutbol.service;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.dao.PartidoDAO;
import com.gestorfutbol.dto.EquipoDTO;
import com.gestorfutbol.dto.PartidoDTO;
import com.gestorfutbol.entity.Equipo;
import com.gestorfutbol.entity.Partido;
import com.gestorfutbol.entity.TablaPosiciones;
import com.gestorfutbol.entity.Torneo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PartidoService {

    private PartidoDAO partidoDAO;

    private SessionFactory sessionFactory;

    public PartidoService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.partidoDAO = new PartidoDAO(sessionFactory);
    }

    public List<PartidoDTO> listarPartidos(){
        ArrayList<Partido> partidos = (ArrayList<Partido>) partidoDAO.extraerTodos();
        ArrayList<PartidoDTO> partidosDTO  = new ArrayList<>();

        for (Partido partido : partidos) {



            partidosDTO.add(new PartidoDTO(
                                partido.getIdPartido(),
                                partido.getGolesLocal(),
                                partido.getGolesVisita(),
                                partido.getFechaPartido().toString(),
                                partido.getEstado(),
                                partido.getJornadaActual(),
                                partido.getEquipoLocal().getNombre(),
                                partido.getEquipoVisita().getNombre(),
                                partido.getTorneo().getNombre()));
        }


        if(partidosDTO.isEmpty()) partidosDTO = null;

        return partidosDTO;
    }


    public void crearPartido(PartidoDTO partidoDTO){
        System.out.println("creando partido!!!!!!!!!!");
        Equipo equipoVisita = null;
        Equipo equipoLocal = null;
        Torneo torneo = null;

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            // Buscar por nombre (HQL)
            equipoVisita = session.createQuery("FROM Equipo WHERE nombre = :nombre", Equipo.class)
                    .setParameter("nombre", partidoDTO.getEquipoVisita())
                    .uniqueResult();

            equipoLocal = session.createQuery("FROM Equipo WHERE nombre = :nombre", Equipo.class)
                    .setParameter("nombre", partidoDTO.getEquipoLocal())
                    .uniqueResult();

            torneo = session.createQuery("FROM Torneo WHERE nombre = :nombre", Torneo.class)
                    .setParameter("nombre", partidoDTO.getTorneo())
                    .uniqueResult();

            tx.commit();  // Asegúrate de confirmar la transacción si haces más cambios en BD
        }

        // Parseo de fecha
        Date fechaDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            fechaDate = sdf.parse(partidoDTO.getFechaPartido());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Crear el objeto partido
        Partido partido = new Partido();
        partido.setEstado(partidoDTO.getEstado());
        partido.setJornadaActual(partidoDTO.getJornadaActual());
        partido.setEquipoVisita(equipoVisita);
        partido.setEquipoLocal(equipoLocal);
        partido.setTorneo(torneo);
        partido.setFechaPartido(fechaDate);

        partidoDAO.guardar(partido);
    }




    public void actualizarPartido(int idPartido, Partido partido){
        Partido partidoEncontrado = null;
        System.out.println("actualizando partido!!!!!!!!!!!");
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            partidoEncontrado = (Partido) session.get(Partido.class, idPartido);

            if(partidoEncontrado != null){
                System.out.println("partido no es nulo");
                partidoEncontrado.setGolesLocal(partido.getGolesLocal());
                partidoEncontrado.setGolesVisita(partido.getGolesVisita());
                partidoEncontrado.setEstado(partido.getEstado());
                session.update(partidoEncontrado);
                tx.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("usando tabla de posiciones service!!!!");
        TablaPosicionesService tablaPosicionesService = new TablaPosicionesService();
        tablaPosicionesService.actualizarEquipoEnTabla(partidoEncontrado);
    }
}
