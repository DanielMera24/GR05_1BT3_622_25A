package com.gestorfutbol.entity;


import jakarta.persistence.*;


import java.util.Date;
import java.util.List;

@Entity
public class Torneo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTorneo;

    @Column(nullable = false)
    private String nombre;

    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Column(nullable = false)
    private int numFechas;

    @OneToMany(mappedBy = "torneo", cascade = CascadeType.ALL)
    private List<Equipo> equipos;


    @OneToMany(mappedBy = "torneo", cascade = CascadeType.ALL)
    private List<TablaPosiciones> tablaPosiciones;



    @OneToMany(mappedBy = "torneo", cascade = CascadeType.ALL)
    private List<Partido> partidos;


    public Torneo(){}

    public int getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(int idTorneo) {
        this.idTorneo = idTorneo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public int getNumFechas() {
        return numFechas;
    }

    public void setNumFechas(int numFechas) {
        this.numFechas = numFechas;
    }
}
