package com.gestorfutbol.service;

import com.gestorfutbol.dao.implementation.TarjetaDAOImpl;
import com.gestorfutbol.dao.interfaces.TarjetaDAO;
import com.gestorfutbol.entity.Jugador;
import com.gestorfutbol.entity.Tarjeta;

import java.util.List;
import java.util.Objects;

public class TarjetaService {
    private TarjetaDAO tarjetaDAO;

    public TarjetaService(TarjetaDAO tarjetaDAO) {
        this.tarjetaDAO = tarjetaDAO;

    }

    public TarjetaService() {
    }

    public boolean guardarTarjeta(String tipoTarjeta, String motivo, int minutos, int idPartido, int idJugador) {
        if (esMinutoValido(minutos)) {
            return false;
        }



        TarjetaDAOImpl tarjetaDAO = new TarjetaDAOImpl();
        tarjetaDAO.guardarTarjeta();
        return true;
    }

    public boolean esMinutoValido(int minutos) {
       return minutos <= 90 && minutos >= 0;
    }


    public boolean tieneMasDosTarjetasAmarillas(List<Tarjeta> tarjetasDeUnJugador) {
        int contadorTarjetasAmarillas = 0;
        for (Tarjeta tarjeta : tarjetasDeUnJugador) {
            if (Objects.equals(tarjeta.getTipoTarjeta(), "AMARILLA"))  {
                contadorTarjetasAmarillas++;
            }
        }
        return contadorTarjetasAmarillas <= 2;
    }

    public boolean tieneMasUnaTarjetaRoja(List<Tarjeta> tarjetas)
    {
        int contadorTarjetasRojas = 0;
        for (Tarjeta tarjeta : tarjetas) {
            if (Objects.equals(tarjeta.getTipoTarjeta(), "ROJA")) {
                contadorTarjetasRojas++;
            }
        }
        return contadorTarjetasRojas <= 1;
    }

    public boolean validarTipoTarjeta(Tarjeta tarjeta) {
        if (Objects.equals(tarjeta.getTipoTarjeta(), "AMARILLA")) {
            return true;
        } else if (Objects.equals(tarjeta.getTipoTarjeta(), "ROJA")) {
            return true;
        }
        return false;
    }

    public boolean esNuloOVacio(String motivo) {
        return !(motivo != null && !motivo.isEmpty());
    }

    public boolean cumpleLimiteCaracteres(String motivo) {
        return !(motivo.length() < 200);
    }

    public boolean cantidadEsNegativa(int cantidadTarjetas) {
        return cantidadTarjetas < 0;
    }

    public boolean jugadorAmonestadoExiste(List<Jugador> jugadores, String cedula) {
        for(Jugador jugador : jugadores) {
            if (cedula.equals(jugador.getCedula())) {
                return true;
            }
        }
        return false;
    }
}
