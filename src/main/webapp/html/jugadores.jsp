<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gestorfutbol.dto.JugadorDTO" %>
<%@ page import="com.gestorfutbol.dto.EquipoDTO" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Jugadores</title>
    <link rel="stylesheet" href="/css/jugadores.css" />
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
            <a href="/jugadores" class="activo">
                <img src="/imagenes/jugador.png" class="icono" /><span class="opciones">Jugadores</span>
            </a>
        </nav>
    </aside>

    <main class="contenido_principal">
        <div class="encabezado">
            <h1>Jugadores</h1>
            <div class="acciones_jugadores">
                <div class="selector_equipo">
                    <form action="/jugadores" method="get">
                        <select name="equipoId" onchange="this.form.submit()">
                            <option value="">Todos los equipos</option>
                            <%
                                List<EquipoDTO> equipos = (List<EquipoDTO>) request.getAttribute("equipos");
                                String equipoSeleccionado = (String) request.getAttribute("equipoSeleccionado");

                                if (equipos != null) {
                                    for (EquipoDTO equipo : equipos) {
                                        String selected = String.valueOf(equipo.getIdEquipo()).equals(equipoSeleccionado) ? "selected" : "";
                            %>
                            <option value="<%= equipo.getIdEquipo() %>" <%= selected %>>
                                <%= equipo.getNombre() %>
                            </option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </form>
                </div>
                <button class="boton_nuevo">
                    + Nuevo Jugador
                </button>
            </div>
        </div>

        <div class="filtros_jugadores">
            <div class="buscador">
                <input type="text" placeholder="Buscar jugador..." id="buscarJugador">
                <img src="/imagenes/buscar.png" class="icono_buscar" />
            </div>
            <div class="filtro_posicion">
                <select id="filtroPosicion">
                    <option value="">Todas las posiciones</option>
                    <option value="Portero">Portero</option>
                    <option value="Defensa">Defensa</option>
                    <option value="Centrocampista">Centrocampista</option>
                    <option value="Delantero">Delantero</option>
                </select>
            </div>
        </div>

        <div class="contenedor_tarjetas">
            <%
                List<JugadorDTO> jugadores = (List<JugadorDTO>) request.getAttribute("jugadores");
                String[] imagenes = {"barcelona.png", "madrid.png", "atleti.png", "argentina.png", "colombia.png", "ecuador.png"};
                int contadorImagen = 0;

                if (jugadores != null && !jugadores.isEmpty()) {
                    for (JugadorDTO jugador : jugadores) {
                        String imagenEquipo = imagenes[contadorImagen % imagenes.length];
                        contadorImagen++;
            %>
            <div class="tarjeta_jugador">
                <div class="cabecera_tarjeta">
                    <div class="numero_dorsal"><%= jugador.getDorsal() %></div>
                    <div class="equipo_badge">
                        <img src="/imagenes/<%= imagenEquipo %>" class="icono_escudo" />
                        <span><%= jugador.getNombreEquipo() %></span>
                    </div>
                </div>
                <div class="cuerpo_tarjeta">
                    <div class="avatar_jugador">
                        <img src="/imagenes/jugador.png" alt="Avatar del jugador" />
                    </div>
                    <h3 class="nombre_jugador"><%= jugador.getNombre() %></h3>
                    <p class="posicion_jugador"><%= jugador.getPosicion() %></p>
                    <span class="edad_jugador"><%= jugador.getEdad() %> años</span>
                </div>
                <div class="pie_tarjeta">
                    <button class="accion editar" data-id="<%= jugador.getIdJugador() %>">Editar</button>
                    <button class="accion eliminar" data-id="<%= jugador.getIdJugador() %>">Eliminar</button>
                </div>
            </div>
            <%
                }
            } else {
            %>
            <p>No hay jugadores disponibles <%= equipoSeleccionado != null ? "para este equipo" : "" %>.</p>
            <%
                }
            %>
        </div>
    </main>
</div>

<!-- Modal para nuevo jugador -->
<div class="modal" id="modalNuevoJugador">
    <div class="modal-contenido">
        <span class="cerrar-modal">&times;</span>
        <h2>Nuevo Jugador</h2>
        <form id="formNuevoJugador" action="/jugadores" method="post">
            <div class="form-grupo">
                <label for="cedula">Cédula</label>
                <input id="cedula" name="cedula" type="text">
            </div>

            <div class="form-grupo">
                <label for="nombre">Nombre</label>
                <input id="nombre" name="nombre" type="text">
            </div>

            <div class="form-grupo">
                <label for="edad">Edad</label>
                <input id="edad" name="edad" type="number">
            </div>

            <div class="form-grupo">
                <label for="posicion">Posición</label>
                <select id="posicion" name="posicion">
                    <option value="">Seleccione una posición</option>
                    <option value="Portero">Portero</option>
                    <option value="Defensa">Defensa</option>
                    <option value="Centrocampista">Centrocampista</option>
                    <option value="Delantero">Delantero</option>
                </select>
            </div>

            <div class="form-grupo">
                <label for="dorsal">Dorsal</label>
                <input id="dorsal" name="dorsal" type="number">
            </div>

            <div class="form-grupo">
                <label for="equipoId">Equipo</label>
                <select id="equipoId" name="equipoId">
                    <option value="">Seleccione un equipo</option>
                    <%
                        if (equipos != null) {
                            for (EquipoDTO equipo : equipos) {
                    %>
                    <option value="<%= equipo.getIdEquipo() %>"><%= equipo.getNombre() %></option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>

            <div class="form-grupo acciones">
                <button class="boton boton-secundario" type="button" id="btnCancelar">Cancelar</button>
                <button class="boton boton-primario" type="submit">Guardar Jugador</button>
            </div>
        </form>
    </div>
</div>

<script src="/js/jugadores.js"></script>
</body>
</html>