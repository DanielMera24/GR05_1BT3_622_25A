package com.gestorfutbol.dao.interfaces;

import com.gestorfutbol.entity.DetallePartido;

import java.util.List;

public interface DetallePartidoDAO {
    boolean guardar(DetallePartido detallePartido);

    List<DetallePartido> listarPorPartido(int idPartido);

    List<DetallePartido> listarPorPartidoYEquipo(int idPartido, int idEquipo);

}