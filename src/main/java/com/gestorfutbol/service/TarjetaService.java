package com.gestorfutbol.service;

import com.gestorfutbol.dao.implementation.TarjetaDAOImpl;
import com.gestorfutbol.dao.interfaces.TarjetaDAO;
import com.gestorfutbol.entity.TipoTarjeta;

public class TarjetaService {
    private TarjetaDAO tarjetaDAO;

    public TarjetaService(TarjetaDAO tarjetaDAO) {
        this.tarjetaDAO = tarjetaDAO;

    }

    public TarjetaService() {
    }

    public boolean guardarTarjeta(TipoTarjeta tipoTarjeta, String motivo, int minutos, int idPartido, int idJugador) {
        if (false){

        }



        return tarjetaDAO.guardarTarjeta();
    }

}
