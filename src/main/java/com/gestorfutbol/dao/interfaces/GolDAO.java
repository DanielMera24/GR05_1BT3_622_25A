package com.gestorfutbol.dao.interfaces;

import com.gestorfutbol.entity.Gol;
import java.util.List;

public interface GolDAO {
    boolean guardarGol(Gol gol);

    List<Gol> obtenerPorPartido(int idPartido);
}