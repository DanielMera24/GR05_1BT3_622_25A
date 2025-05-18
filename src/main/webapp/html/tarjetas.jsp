<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gestorfutbol.dto.TarjetaDTO" %>
<%@ page import="com.gestorfutbol.dto.PartidoDTO" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Tarjetas</title>
    <link rel="stylesheet" href="/css/tarjetas.css" />
    <link href="https://fonts.googleapis.com/css2?family=Jaldi:wght@400;700&display=swap" rel="stylesheet">
</head>

<body>
<div class="aplicacion">
    <aside class="barra_lateral">
        <h2>
            <img src="/imagenes/pelota.png" class="icono" />
            FútbolManager
        </h2>
        <nav>
            <a href="/listarTorneos">
                <img src="/imagenes/trofeo.png" class="icono" /><span class="opciones">Torneos</span>
            </a>
            <a href="/equipos">
                <img src="/imagenes/equipo.png" class="icono" /><span class="opciones">Equipos</span>
            </a>
            <a href="/mostrarTablaPosiciones">
                <img src="/imagenes/tabla.png" class="icono" /><span class="opciones">Tabla de Posiciones</span>
            </a>
            <a href="/partidos">
                <img src="/imagenes/calendario.png" class="icono" /><span class="opciones">Partidos</span>
            </a>
            <a href="/jugadores">
                <img src="/imagenes/jugador.png" class="icono" /><span class="opciones">Jugadores</span>
            </a>
            <a href="/tarjetas" class="activo">
                <img src="/imagenes/tarjetas.png" class="icono" /><span class="opciones">Tarjetas</span>
            </a>
        </nav>
    </aside>

    <main class="contenido_principal">
        <div class="encabezado">
            <h1>Tarjetas</h1>
            <div class="acciones_tarjetas">
                <div class="selector_partido">
                    <form action="/tarjetas" method="get">
                        <select name="partidoId" id="partidoSelector" onchange="this.form.submit()">
                            <option value="">Todos los partidos</option>
                            <%
                                List<PartidoDTO> partidos = (List<PartidoDTO>) request.getAttribute("partidos");
                                String partidoSeleccionado = (String) request.getAttribute("partidoSeleccionado");

                                if (partidos != null) {
                                    for (PartidoDTO partido : partidos) {
                                        String selected = String.valueOf(partido.getIdPartido()).equals(partidoSeleccionado) ? "selected" : "";
                            %>
                            <option value="<%= partido.getIdPartido() %>" <%= selected %>>
                                <%= partido.getEquipoLocal() %> vs <%= partido.getEquipoVisita() %> - <%= partido.getFechaPartido() %>
                            </option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </form>
                </div>
            </div>
        </div>

        <div class="filtros_tarjetas">
            <div class="buscador">
                <input type="text" placeholder="Buscar tarjetas de un jugador..." id="buscarTarjeta">
                <img src="/imagenes/buscar.png" class="icono_buscar" />
            </div>
            <div class="filtro_tipo">
                <select id="filtroTipo">
                    <option value="">Todos los tipos</option>
                    <option value="AMARILLA">Amarilla</option>
                    <option value="ROJA">Roja</option>
                </select>
            </div>
        </div>

        <div class="contenedor_tarjetas">
            <%
                List<TarjetaDTO> tarjetas = (List<TarjetaDTO>) request.getAttribute("tarjetas");
                if (tarjetas != null && !tarjetas.isEmpty()) {
                    for (TarjetaDTO tarjeta : tarjetas) {
                        String tipoClase = tarjeta.getTipoTarjeta().equalsIgnoreCase("AMARILLA") ? "amarilla" : "roja";
            %>
            <div class="tarjeta_sancion <%= tipoClase %>">
                <div class="cabecera_tarjeta">
                    <div class="tipo_tarjeta"><%= tarjeta.getTipoTarjeta() %></div>
                    <div class="partido_badge">
                        <img src="/imagenes/<%= tarjeta.getSiglaEquipoLocal() %>.png" class="icono_escudo" />
                        <span class="vs">vs</span>
                        <img src="/imagenes/<%= tarjeta.getSiglaEquipoVisitante() %>.png" class="icono_escudo" />
                    </div>
                </div>
                <div class="cuerpo_tarjeta">
                    <div class="avatar_jugador">
                        <img src="/imagenes/jugador.png" alt="Avatar del jugador" />
                    </div>
                    <h3 class="nombre_jugador"><%= tarjeta.getNombreJugador() %></h3>
                    <div class="info_jugador">
                        <span class="equipo_jugador"><%= tarjeta.getEquipoJugador() %></span>
                        <span class="dorsal_jugador">#<%= tarjeta.getDorsalJugador() %></span>
                    </div>
                    <div class="detalle_tarjeta">
                        <div class="minuto_tarjeta">Minuto: <%= tarjeta.getMinuto() %>'</div>
                        <div class="motivo_tarjeta">Motivo: <%= tarjeta.getMotivo() %></div>
                    </div>
                </div>
                <div class="pie_tarjeta">
                    <button class="accion editar"
                            data-id="<%= tarjeta.getIdTarjeta() %>"
                            data-tipo="<%= tarjeta.getTipoTarjeta() %>"
                            data-jugador="<%= tarjeta.getNombreJugador() %>"
                            data-equipo="<%= tarjeta.getEquipoJugador() %>"
                            data-minuto="<%= tarjeta.getMinuto() %>"
                            data-motivo="<%= tarjeta.getMotivo() %>"
                            data-partido="<%= tarjeta.getIdPartido() %>">
                        Editar
                    </button>
                    <button class="accion eliminar" data-id="<%= tarjeta.getIdTarjeta() %>">Eliminar</button>
                </div>
            </div>
            <%
                }
            } else {
            %>
            <p>No hay tarjetas disponibles <%= partidoSeleccionado != null ? "para este partido" : "" %>.</p>
            <%
                }
            %>
        </div>
    </main>
</div>

<!-- Modal para editar tarjeta -->
<div class="modal" id="modalEditarTarjeta">
    <div class="modal-contenido">
        <span class="cerrar-modal-editar">&times;</span>
        <h2>Editar Tarjeta</h2>
        <form id="formEditarTarjeta" action="/actualizarTarjeta" method="post">
            <input type="hidden" id="idTarjetaEditar" name="idTarjeta">

            <div class="form-grupo">
                <label for="tipoTarjetaEditar">Tipo de Tarjeta</label>
                <select id="tipoTarjetaEditar" name="tipoTarjeta">
                    <option value="">Seleccione un tipo</option>
                    <option value="AMARILLA">Amarilla</option>
                    <option value="ROJA">Roja</option>
                </select>
            </div>

            <div class="form-grupo">
                <label for="partidoIdEditar">Partido</label>
                <select id="partidoIdEditar" name="partidoId">
                    <option value="">Seleccione un partido</option>
                    <%
                        if (partidos != null) {
                            for (PartidoDTO partido : partidos) {
                    %>
                    <option value="<%= partido.getIdPartido() %>">
                        <%= partido.getEquipoLocal() %> vs <%= partido.getEquipoVisita() %> (<%= partido.getFechaPartido() %>)
                    </option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>

            <div class="form-grupo">
                <label for="jugadorIdEditar">Jugador</label>
                <select id="jugadorIdEditar" name="jugadorId">
                    <option value="">Seleccione un partido primero</option>
                </select>
            </div>

            <div class="form-grupo">
                <label for="minutoEditar">Minuto</label>
                <input id="minutoEditar" name="minuto" type="number" >
            </div>

            <div class="form-grupo">
                <label for="motivoEditar">Motivo</label>
                <textarea id="motivoEditar" name="motivo" rows="3"></textarea>
            </div>

            <div class="form-grupo acciones">
                <button class="boton boton-secundario" type="button" id="btnCancelarEditar">Cancelar</button>
                <button class="boton boton-primario" type="submit">Actualizar</button>
            </div>
        </form>
    </div>
</div>

<script src="/js/tarjetas.js"></script>
</body>
</html>