package com.gestorfutbol.entity;


import jakarta.persistence.*;

import java.util.Date;
import java.util.List;


@Entity
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPartido;

    private int golesLocal;

    private int golesVisita;

    @Temporal(TemporalType.DATE)
    private Date fechaPartido;

    private String Estado;

    private int jornadaActual;

    @OneToOne
    @JoinColumn(name = "idEquipoLocal")
    private Equipo equipoLocal;

    @OneToOne
    @JoinColumn(name = "idEquipoVisita")
    private Equipo equipoVisita;


    @ManyToOne()
    @JoinColumn(name = "idTorneo")
    private Torneo torneo;

    public Partido() {}




}
