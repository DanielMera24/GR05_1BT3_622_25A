package com.gestorfutbol.dao.interfaces;

import com.gestorfutbol.entity.Tarjeta;
import java.util.List;

public interface TarjetaDAO {
    boolean guardarTarjeta(Tarjeta tarjeta);
    List<Tarjeta> obtenerTodas();
    List<Tarjeta> obtenerPorPartido(int idPartido);
}