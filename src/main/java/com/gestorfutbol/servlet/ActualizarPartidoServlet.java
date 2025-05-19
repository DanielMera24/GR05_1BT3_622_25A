package com.gestorfutbol.servlet;

import com.gestorfutbol.entity.Jugador;
import com.gestorfutbol.entity.Partido;
import com.gestorfutbol.entity.TablaPosiciones;
import com.gestorfutbol.entity.Tarjeta;
import com.gestorfutbol.service.JugadorService;
import com.gestorfutbol.service.PartidoService;
import com.gestorfutbol.service.TarjetaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/actualizarPartido")
public class ActualizarPartidoServlet extends HttpServlet {

    PartidoService partidoService;
    TarjetaService tarjetaService;
    JugadorService jugadorService;

    @Override
    public void init() throws ServletException {
        partidoService = new PartidoService();
        tarjetaService = new TarjetaService();
        jugadorService = new JugadorService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String estado = request.getParameter("estadoPartido");
        int golesLocal = Integer.parseInt(request.getParameter("golesLocal"));
        int golesVisitante = Integer.parseInt(request.getParameter("golesVisitante"));
        int idPartido = Integer.parseInt(request.getParameter("idPartido"));

        // Actualizar partido
        Partido partido = partidoService.obtenerPartidoPorId(idPartido);
        // Procesar tarjetas si existen
        String cantidadTarjetasStr = request.getParameter("cantidadTarjetas");
        if (cantidadTarjetasStr != null && !cantidadTarjetasStr.isEmpty()) {
            int cantidadTarjetas = Integer.parseInt(cantidadTarjetasStr);

            List<Tarjeta> tarjetas = new ArrayList<>();

            for (int i = 0; i < cantidadTarjetas; i++) {
                String tipo = request.getParameter("tarjeta_" + i + "_tipo");
                String jugadorIdStr = request.getParameter("tarjeta_" + i + "_jugadorId");
                String minutoStr = request.getParameter("tarjeta_" + i + "_minuto");
                String motivo = request.getParameter("tarjeta_" + i + "_motivo");

                if (tipo != null && jugadorIdStr != null && minutoStr != null && motivo != null) {
                    Tarjeta tarjeta = new Tarjeta();
                    tarjeta.setTipoTarjeta(tipo);
                    tarjeta.setMotivo(motivo);
                    tarjeta.setMinuto(Integer.parseInt(minutoStr));

                    // Obtener jugador
                    Jugador jugador = jugadorService.obtenerJugadorPorId(Integer.parseInt(jugadorIdStr));

                    tarjeta.setJugador(jugador);
                    tarjeta.setPartido(partido);

                    tarjetas.add(tarjeta);
                }
            }

            // Guardar las tarjetas
            if (!tarjetas.isEmpty()) {
                tarjetaService.guardarTarjeta(tarjetas);
            }
        }


        partidoService.actualizarPartido(partido, estado, golesLocal, golesVisitante);


        response.sendRedirect(request.getContextPath() + "/partidos");
    }


    private TablaPosiciones buscarTablaPosiciones(Session session, int idTorneo, int idEquipo) {
        String hql = "FROM TablaPosiciones tp WHERE tp.torneo.idTorneo = :idTorneo AND tp.equipo.idEquipo = :idEquipo";
        Query<TablaPosiciones> query = session.createQuery(hql, TablaPosiciones.class);
        query.setParameter("idTorneo", idTorneo);
        query.setParameter("idEquipo", idEquipo);
        return query.uniqueResult();
    }
}
