<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.gestorfutbol.dto.TorneoDTO" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Fútbol Manager</title>
    <link href="/css/index.css" rel="stylesheet"/>
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
            <a class="activo" href="/listarTorneos">
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
            <a href="/partidos">
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
            <h1>Torneos</h1>
            <button class="boton_nuevo">+ Nuevo Torneo</button>
        </div>

        <%
            String errorMensaje = (String) request.getAttribute("errorMensaje");
            if (errorMensaje != null) {
        %>
        <div class="alerta_error">
            <p><%= errorMensaje %></p>
        </div>
        <%
            }
        %>

        <div class="tarjetas">
            <%
                List<TorneoDTO> torneos = (List<TorneoDTO>) request.getAttribute("torneos");
                if (torneos != null && !torneos.isEmpty()) {
                    int index = 0;
                    for (TorneoDTO torneo : torneos) {
                        String fondo;
                        if (index % 3 == 0) {
                            fondo = "fondo_azul";
                        } else if (index % 3 == 1) {
                            fondo = "fondo_rosado";
                        } else {
                            fondo = "fondo_rojo";
                        }

                        String fechaTexto = (torneo.getFechaInicio() != null)
                                ? torneo.getFechaInicio().toString()
                                : "Sin fecha";
            %>
            <div class="tarjeta">
                <div class="encabezado_tarjeta <%= fondo %>">
                    <h3><%= torneo.getNombre() %></h3>
                    <span><%= fechaTexto %></span>
                </div>
                <div class="cuerpo_tarjeta">
                    <p><strong>Mas información...</strong> <%--torneo.getIdTorneo()--%></p>
                    <div class="acciones_tarjeta">
                        <a href="#<%-- /detalleTorneo?id= <%=%>torneo.getIdTorneo() --%>">Detalles</a>
                        <a href="#<%-- /editarTorneo?id= <%=torneo.getIdTorneo() --%>">Editar</a>
                    </div>
                </div>
            </div>
            <%
                    index++;
                }
            } else {
            %>
            <p>No hay torneos registrados.</p>
            <%
                }
            %>
        </div>



    </main>

    <div class="modal" id="modalFormulario">
        <div class="modal_contenido">
            <span class="cerrar" id="cerrarModal">&times;</span>
            <h2>Nuevo Torneo</h2>
            <form class="formulario_encuesta" id="formTorneo" method="post" action="/listarTorneos">
            <div class="campo">
                    <label for="nombreTorneo">Nombre del Torneo</label>
                    <input id="nombreTorneo" name="nombreTorneo" type="text"/>
                </div>
                <div class="campo">
                    <label for="fechaInicio">Fecha de Inicio</label>
                    <input id="fechaInicio" name="fechaInicio" type="date"/>
                </div>
                <button class="boton_submit" type="submit">Crear Torneo</button>
            </form>
        </div>
    </div>

</div>

<script src="/js/index.js"></script>

</body>
</html>
