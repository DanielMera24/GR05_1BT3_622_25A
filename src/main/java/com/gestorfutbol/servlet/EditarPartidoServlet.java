package com.gestorfutbol.servlet;

import com.gestorfutbol.dto.EquipoDTO;
import com.gestorfutbol.dto.PartidoDTO;
import com.gestorfutbol.dto.JugadorDTO;
import com.gestorfutbol.dto.TarjetaDTO;
import com.gestorfutbol.service.PartidoService;
import com.gestorfutbol.service.EquipoService;
import com.gestorfutbol.service.JugadorService;
import com.gestorfutbol.service.TarjetaService;
import com.gestorfutbol.entity.Partido;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/editarPartido")
public class EditarPartidoServlet extends HttpServlet {

    private PartidoService partidoService;
    private EquipoService equipoService;
    private JugadorService jugadorService;
    private TarjetaService tarjetaService;

    @Override
    public void init() throws ServletException {
        partidoService = new PartidoService();
        equipoService = new EquipoService();
        jugadorService = new JugadorService();
        tarjetaService = new TarjetaService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String partidoIdStr = request.getParameter("id");



        try {
            int partidoId = Integer.parseInt(partidoIdStr);

            // Obtener el partido
            Partido partido = partidoService.obtenerPartidoPorId(partidoId);


            // Crear DTO del partido para la vista
            PartidoDTO partidoDTO = new PartidoDTO(
                    partido.getIdPartido(),
                    partido.getGolesLocal(),
                    partido.getGolesVisita(),
                    partido.getFechaPartido().toString(),
                    partido.getEstado(),
                    partido.getJornadaActual(),
                    partido.getEquipoLocal().getNombre(),
                    partido.getEquipoVisita().getNombre(),
                    partido.getTorneo().getNombre()
            );

            // Obtener información de los equipos
            EquipoDTO equipoLocal = new EquipoDTO(
                    partido.getEquipoLocal().getNombre(),
                    partido.getEquipoLocal().getCiudad(),
                    partido.getEquipoLocal().getEstadio(),
                    partido.getEquipoLocal().getSiglas()
            );
            equipoLocal.setIdEquipo(partido.getEquipoLocal().getIdEquipo());

            EquipoDTO equipoVisitante = new EquipoDTO(
                    partido.getEquipoVisita().getNombre(),
                    partido.getEquipoVisita().getCiudad(),
                    partido.getEquipoVisita().getEstadio(),
                    partido.getEquipoVisita().getSiglas()
            );
            equipoVisitante.setIdEquipo(partido.getEquipoVisita().getIdEquipo());

            // Obtener jugadores de ambos equipos
            List<JugadorDTO> jugadoresLocal = jugadorService.listarJugadoresPorEquipo(
                    partido.getEquipoLocal().getIdEquipo()
            );
            List<JugadorDTO> jugadoresVisitante = jugadorService.listarJugadoresPorEquipo(
                    partido.getEquipoVisita().getIdEquipo()
            );

            // Obtener tarjetas del partido
            List<TarjetaDTO> tarjetas = tarjetaService.listarTarjetasPorPartido(partidoId);

            // Pasar todos los datos a la vista
            request.setAttribute("partido", partidoDTO);
            request.setAttribute("equipoLocal", equipoLocal);
            request.setAttribute("equipoVisitante", equipoVisitante);
            request.setAttribute("jugadoresLocal", jugadoresLocal);
            request.setAttribute("jugadoresVisitante", jugadoresVisitante);
            request.setAttribute("tarjetas", tarjetas);

            // Redirigir a la página de edición
            request.getRequestDispatcher("/html/editarPartido.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/partidos");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error al cargar el partido para edición", e);
        }
    }
}