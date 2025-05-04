package com.gestorfutbol.service;

import com.gestorfutbol.config.HibernateUtil;
import com.gestorfutbol.dao.PartidoDAO;
import com.gestorfutbol.dto.EquipoDTO;
import com.gestorfutbol.dto.PartidoDTO;
import com.gestorfutbol.entity.Equipo;
import com.gestorfutbol.entity.Partido;
import com.gestorfutbol.entity.Torneo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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

        Equipo equipoVisita = null;
        Equipo equipoLocal = null;
        Torneo torneo = null;


        try(Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction();
            equipoVisita = session.get(Equipo.class, partidoDTO.getEquipoLocal());
            equipoLocal = session.get(Equipo.class, partidoDTO.getEquipoVisita());
            torneo = session.get(Torneo.class, partidoDTO.getTorneo());
        }

        Date fechaDate = null;

        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            fechaDate = sdf.parse((partidoDTO.getFechaPartido().toString()));
            partidoDTO.setFechaPartido(String.valueOf(fechaDate));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Partido partido = new Partido();
        //partido.setGolesLocal(partidoDTO.getGolesLocal());
        //partido.setGolesVisita(partidoDTO.getGolesVisita());
        partido.setEstado(partidoDTO.getEstado());
        partido.setJornadaActual(partidoDTO.getJornadaActual());
        partido.setEquipoVisita(equipoVisita);
        partido.setEquipoLocal(equipoLocal);
        partido.setTorneo(torneo);
        partido.setFechaPartido(fechaDate);

        partidoDAO.guardar(partido);
    }
}
