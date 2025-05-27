<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.gestorfutbol.dto.EquipoDTO" %>
<%@ page import="com.gestorfutbol.dto.TorneoDTO" %>
<%@ page import="com.gestorfutbol.dto.PartidoDTO" %>
<%@ page import="com.gestorfutbol.dto.JugadorDTO" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8"/>
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <title>Partidos</title>
    <link href="/css/partidos.css" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css2?family=Jaldi:wght@400;700&display=swap" rel="stylesheet">
</head>
<body>
<div class="aplicacion">
    <aside class="barra_lateral">
        <h2>
            <img alt="Logo" class="icono" src="/imagenes/pelota.png"/>
            FútbolManager
        </h2>
        <nav>
            <a href="/listarTorneos">
                <img class="icono" src="/imagenes/trofeo.png"/>
                <span class="opciones">Torneos</span>
            </a>
            <a href="/equipos">
                <img class="icono" src="/imagenes/equipo.png"/>
                <span class="opciones">Equipos</span>
            </a>
            <a href="/mostrarTablaPosiciones">
                <img class="icono" src="/imagenes/tabla.png"/>
                <span class="opciones">Tabla de Posiciones</span>
            </a>
            <a href="/partidos" class="activo">
                <img class="icono" src="/imagenes/calendario.png"/>
                <span class="opciones">Partidos</span>
            </a>

            <a href="/jugadores">
                <img class="icono" src="/imagenes/jugador.png"/>
                <span class="opciones">Jugadores</span>
            </a>
            <a href="/tarjetas">
                <img class="icono" src="/imagenes/tarjetas.png"/>
                <span class="opciones">Tarjetas</span>
            </a>
        </nav>
    </aside>

    <main class="contenido_principal">
        <div class="encabezado">
            <h1>Partidos</h1>
            <button class="boton_nuevo" id="abrirModal">+ Nuevo Partido</button>
        </div>

        <div class="partidos">
            <%
                List<PartidoDTO> partidos = (List<PartidoDTO>) request.getAttribute("partidos");
                if (partidos != null && !partidos.isEmpty()) {
                    for (PartidoDTO p : partidos) {
                        String nombreLocal = p.getEquipoLocal();
                        String nombreVisitante = p.getEquipoVisita();
                        String nombreTorneo = p.getTorneo();
                        String fechaFormateada = p.getFechaPartido();
            %>
            <div class="partido">
                <div class="partido_cabecera">
                    <span class="torneo_badge"><%=nombreTorneo%></span>
                    <span class="estado_partido <%= p.getEstado().toLowerCase().replace(" ", "_") %>"><%= p.getEstado() %></span>
                </div>

                <div class="info_partido">
                    <div class="equipo equipo_local">
                        <img class="icono_escudo" src="/imagenes/barcelona.png"/>
                        <span class="nombre_equipo"><%=nombreLocal%></span>
                    </div>

                    <div class="marcador">
                        <span class="gol"><%= p.getGolesLocal() %></span>
                        <span class="separador">-</span>
                        <span class="gol"><%= p.getGolesVisita() %></span>
                    </div>

                    <div class="equipo equipo_visitante">
                        <span class="nombre_equipo"><%= nombreVisitante%></span>
                        <img class="icono_escudo" src="/imagenes/atleti.png"/>
                    </div>
                </div>

                <div class="partido_footer">
                    <div class="partido_detalles">
                        <span class="jornada">Jornada <%= p.getJornadaActual() %></span>
                        <span class="fecha_partido"><%= fechaFormateada %></span>
                    </div>
                    <!-- En la sección de botones de acción, cambiar el botón Editar -->
                    <div class="partido_acciones">
                        <a class="boton_ver" href="#"
                           data-id="<%=p.getIdPartido()%>"
                           data-local="<%= nombreLocal%>"
                           data-visitante="<%=nombreVisitante%>"
                           data-goles-local="<%= p.getGolesLocal() %>"
                           data-goles-visitante="<%= p.getGolesVisita() %>"
                           data-torneo="<%=nombreTorneo%>"
                           data-jornada="<%= p.getJornadaActual() %>"
                           data-estado="<%= p.getEstado() %>">
                            Ver detalles
                        </a>

                        <!-- CAMBIO: En lugar de abrir modal, redirigir a nueva página -->
                        <a class="accion_enlace <%= p.getEstado().equals("Finalizado") ? "disabled" : "" %>"
                           href="<%= p.getEstado().equals("Finalizado") ? "#" : request.getContextPath() + "/editarPartido?id=" + p.getIdPartido() %>"
                                <%= p.getEstado().equals("Finalizado") ? "onclick='return false;'" : "" %>>
                            Editar
                        </a>
                    </div>
                </div>
            </div>
            <%
                }
            } else {
            %>
            <p>No hay partidos disponibles.</p>
            <%
                }
            %>
        </div>


        <!-- Modal para nuevo partido -->
        <div class="modal" id="modalNuevoPartido">
            <div class="modal-contenido">
                <span class="cerrar-modal">&times;</span>

                <h2>Nuevo Partido</h2>
                <form id="formNuevoPartido" action="/partidos" method="post">
                    <div class="form-grupo">
                        <label for="torneo">Torneo</label>
                        <select id="torneo" name="torneo" required>
                            <option value="">Seleccione un torneo</option>
                        </select>
                    </div>

                    <div class="form-grupo">
                        <label for="jornada">Jornada</label>
                        <input id="jornada" name="jornada" min="1" type="number" required>
                    </div>

                    <div class="equipos-container">
                        <div class="form-grupo equipo">
                            <label for="equipoLocal">Equipo Local</label>
                            <select id="equipoLocal" name="equipoLocal" required>
                                <option value="">Seleccione equipo</option>
                            </select>
                        </div>
                        <div class="vs">VS</div>
                        <div class="form-grupo equipo">
                            <label for="equipoVisitante">Equipo Visitante</label>
                            <select id="equipoVisitante" name="equipoVisitante" required>
                                <option value="">Seleccione equipo</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-grupo">
                        <label for="fecha">Fecha y Hora</label>
                        <input id="fecha" name="fecha" type="datetime-local" required>
                    </div>

                    <div class="form-grupo">
                        <label for="estadio">Estadio</label>
                        <input id="estadio" name="estadio" type="text" required>
                    </div>

                    <div class="form-grupo acciones">
                        <button class="boton boton-secundario cerrar-modal" type="button">Cancelar</button>
                        <button class="boton boton-primario" type="submit">Guardar Partido</button>
                    </div>
                </form>
            </div>
        </div>
    </main>
</div>

<!-- Modal Detalle Partido -->
<div class="modal" id="modalDetallePartido">
    <div class="modal-contenido-detalle">
        <span class="cerrar-modal-detalle">&times;</span>
        <h2>Detalles de encuentro</h2>

        <form id="formDetallePartido" action="/actualizarPartido" method="post">
            <p><strong>(Nombre Local)</strong> vs <strong>(Nombre Visitante)</strong></p>
            <p>(Nombre Torneo) · Jornada (n)</p>
            <input type="hidden" id="idPartido" name="idPartido" value="">
            <div class="resultado_partido">
                <div class="equipo_detalle">
                    <img src="" id="foto-local" class="icono_detalle" />
                </div>

                <div class="marcador_detalle">
                    <select class="select_goles" name="golesLocal">
                        <option>0</option><option>1</option><option>2</option><option>3</option><option>4</option><option>5</option>
                    </select>
                    <span>-</span>
                    <select class="select_goles" name="golesVisitante">
                        <option>0</option><option>1</option><option>2</option><option>3</option><option>4</option><option>5</option>
                    </select>
                </div>

                <div class="equipo_detalle">
                    <img src="" id="foto-visitante" class="icono_detalle" />
                </div>
            </div>

            <div class="seccion-tarjetas">
                <h3>Tarjetas del Partido</h3>

                <!-- Contenedor para tarjetas añadidas -->
                <div class="contenedor-tarjetas-partido" id="contenedorTarjetas">
                    <!-- Las tarjetas se añadirán aquí dinámicamente -->
                </div>

                <button type="button" class="boton-nueva-tarjeta" id="agregarTarjeta">+ Nueva Tarjeta</button>
            </div>

            <!-- Modal para nueva tarjeta (aparece dentro del modal de detalles) -->
            <div class="modal-tarjeta" id="modalNuevaTarjeta" style="display: none;">
                <div class="form-tarjeta">
                    <h4>Nueva Tarjeta</h4>
                    <div class="form-grupo">
                        <label for="tipoTarjeta">Tipo de Tarjeta</label>
                        <select id="tipoTarjeta" name="tipoTarjeta" >
                            <option value="">Seleccione un tipo</option>
                            <option value="AMARILLA">Amarilla</option>
                            <option value="ROJA">Roja</option>
                        </select>
                    </div>

                    <div class="form-grupo">
                        <label for="jugadorTarjeta">Jugador</label>
                        <select id="jugadorTarjeta" name="jugadorTarjeta" >
                            <option value="">Seleccione un jugador</option>
                            <!-- Los jugadores se cargarán dinámicamente -->
                        </select>
                    </div>

                    <div class="form-grupo">
                        <label for="minutoTarjeta">Minuto</label>
                        <input id="minutoTarjeta" name="minutoTarjeta" type="number">
                    </div>

                    <div class="form-grupo">
                        <label for="motivoTarjeta">Motivo</label>
                        <textarea id="motivoTarjeta" name="motivoTarjeta" rows="3" ></textarea>
                    </div>

                    <div class="form-grupo acciones-tarjeta">
                        <button type="button" class="boton boton-secundario" id="cancelarTarjeta">Cancelar</button>
                        <button type="button" class="boton boton-primario" id="guardarTarjeta">Añadir Tarjeta</button>
                    </div>
                </div>
            </div>



            <div class="form-grupo">
                <label for="estadoPartido">Estado del partido</label>
                <select id="estadoPartido" name="estadoPartido">
                    <option>Finalizado</option>
                    <option>En curso</option>
                    <option>Pendiente</option>
                </select>
            </div>

            <div class="acciones">
                <button class="boton boton-primario" type="submit">Guardar cambios</button>
            </div>

        </form>
    </div>
</div>

<%-- Leer listas desde el servlet --%>
<%
    List<EquipoDTO> equipos = (List<EquipoDTO>) request.getAttribute("equipos");
    List<TorneoDTO> torneos = (List<TorneoDTO>) request.getAttribute("torneos");

    if (equipos == null) {
        equipos = java.util.Collections.emptyList();
    }
    if (torneos == null) {
        torneos = java.util.Collections.emptyList();
    }
%>


<%-- Variables para torneos y equipos para JS --%>
<%-- Después de las variables para torneos y equipos para JS --%>
<script>
    window.torneos = [
        <% for (int i = 0; i < torneos.size(); i++) {
             TorneoDTO t = torneos.get(i); %>
        { id: "<%= t.getIdTorneo() %>", nombre: "<%= t.getNombre() %>" }<%= (i < torneos.size() - 1) ? "," : "" %>
        <% } %>
    ];
    window.equipos = [
        <% for (int i = 0; i < equipos.size(); i++) {
             EquipoDTO e = equipos.get(i);
        %>
        {
            id: "<%= e.getIdEquipo() %>",
            nombre: "<%= e.getNombre() %>",
            torneoId: "<%= e.getIdTorneo() %>",
            siglas: "<%= e.getSiglas() %>"
        }<%= (i < equipos.size() - 1) ? "," : "" %>
        <% } %>
    ];

    // Datos de jugadores por equipo para uso en modales
    window.jugadoresPorEquipo = {
        <% for (EquipoDTO e : equipos) {
            List<JugadorDTO> jugadoresEquipo = (List<JugadorDTO>) request.getAttribute("jugadores_" + e.getIdEquipo());
            if (jugadoresEquipo != null && !jugadoresEquipo.isEmpty()) {
        %>
        "<%= e.getIdEquipo() %>": [
            <% for (int j = 0; j < jugadoresEquipo.size(); j++) {
                JugadorDTO jugador = jugadoresEquipo.get(j);
            %>
            {
                id: "<%= jugador.getIdJugador() %>",
                nombre: "<%= jugador.getNombre() %>",
                dorsal: <%= jugador.getDorsal() %>,
                posicion: "<%= jugador.getPosicion() %>",
                equipo: "<%= e.getNombre() %>"
            }<%= (j < jugadoresEquipo.size() - 1) ? "," : "" %>
            <% } %>
        ],
        <% } } %>
    };
</script>



<%-- Tu JavaScript final --%>
<script src="/js/partido.js"></script>

</body>
</html>