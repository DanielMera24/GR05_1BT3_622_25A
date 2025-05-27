package com.gestorfutbol.servlet;

import com.gestorfutbol.dto.EquipoDTO;
import com.gestorfutbol.dto.PartidoDTO;
import com.gestorfutbol.dto.JugadorDTO;
import com.gestorfutbol.dto.TarjetaDTO;
import com.gestorfutbol.dto.GolDTO;
import com.gestorfutbol.dto.DetallePartidoDTO;
import com.gestorfutbol.service.PartidoService;
import com.gestorfutbol.service.EquipoService;
import com.gestorfutbol.service.JugadorService;
import com.gestorfutbol.service.TarjetaService;
import com.gestorfutbol.service.GolService;
import com.gestorfutbol.service.DetallePartidoService;
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
    private GolService golService;
    private DetallePartidoService detallePartidoService;

    @Override
    public void init() throws ServletException {
        partidoService = new PartidoService();
        equipoService = new EquipoService();
        jugadorService = new JugadorService();
        tarjetaService = new TarjetaService();
        golService = new GolService();
        detallePartidoService = new DetallePartidoService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String partidoIdStr = request.getParameter("id");

        if (partidoIdStr == null || partidoIdStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/partidos");
            return;
        }

        try {
            int partidoId = Integer.parseInt(partidoIdStr);

            // Obtener el partido
            Partido partido = partidoService.obtenerPartidoPorId(partidoId);

            if (partido == null) {
                response.sendRedirect(request.getContextPath() + "/partidos");
                return;
            }

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

            // Obtener jugadores de ambos equipos (todos los jugadores disponibles)
            List<JugadorDTO> jugadoresLocal = jugadorService.listarJugadoresPorEquipo(
                    partido.getEquipoLocal().getIdEquipo()
            );
            List<JugadorDTO> jugadoresVisitante = jugadorService.listarJugadoresPorEquipo(
                    partido.getEquipoVisita().getIdEquipo()
            );

            // **NUEVO: Obtener detalles del partido (jugadores que participaron)**
            List<DetallePartidoDTO> detallesPartido = detallePartidoService.listarDetallesPorPartido(partidoId);

            // Separar detalles por equipo
            List<DetallePartidoDTO> detallesEquipoLocal = detallePartidoService.listarDetallesPorPartidoYEquipo(
                    partidoId, partido.getEquipoLocal().getIdEquipo()
            );
            List<DetallePartidoDTO> detallesEquipoVisitante = detallePartidoService.listarDetallesPorPartidoYEquipo(
                    partidoId, partido.getEquipoVisita().getIdEquipo()
            );

            // Obtener tarjetas del partido
            List<TarjetaDTO> tarjetas = tarjetaService.listarTarjetasPorPartido(partidoId);

            // Obtener goles del partido
            List<GolDTO> goles = golService.listarGolesPorPartido(partidoId);

            // Pasar todos los datos a la vista
            request.setAttribute("partido", partidoDTO);
            request.setAttribute("equipoLocal", equipoLocal);
            request.setAttribute("equipoVisitante", equipoVisitante);
            request.setAttribute("jugadoresLocal", jugadoresLocal);
            request.setAttribute("jugadoresVisitante", jugadoresVisitante);
            request.setAttribute("tarjetas", tarjetas);
            request.setAttribute("goles", goles);

            // **NUEVO: Pasar detalles del partido**
            request.setAttribute("detallesPartido", detallesPartido);
            request.setAttribute("detallesEquipoLocal", detallesEquipoLocal);
            request.setAttribute("detallesEquipoVisitante", detallesEquipoVisitante);

            request.getRequestDispatcher("/html/editarPartido.jsp").forward(request, response);


        } catch (NumberFormatException e) {
            System.err.println("ID de partido inválido: " + partidoIdStr);
            response.sendRedirect(request.getContextPath() + "/partidos");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error al cargar el partido para edición", e);
        }
    }
}