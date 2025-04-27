<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gestorfutbol.entity.Torneo" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Fútbol Manager</title>
    <link href="/css/index.css" rel="stylesheet"/>
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
                Torneos
            </a>
            <a href="/html/equipos.html">
                <img class="icono" src="/imagenes/equipo.png"/>
                Equipos
            </a>
            <a href="/html/tabla.html">
                <img class="icono" src="/imagenes/tabla.png"/>
                Tabla de Posiciones
            </a>
            <a href="/html/partidos.html">
                <img class="icono" src="/imagenes/calendario.png"/>
                Partidos
            </a>
        </nav>
    </aside>

    <main class="contenido_principal">
        <div class="encabezado">
            <h1>Torneos</h1>
            <button class="boton_nuevo">+ Nuevo Torneo</button>
        </div>

        <div class="tarjetas">
            <%
                List<Torneo> torneos = (List<Torneo>) request.getAttribute("torneos");
                if (torneos != null && !torneos.isEmpty()) {
                    for (Torneo torneo : torneos) {
            %>
            <div class="tarjeta">
                <div class="encabezado_tarjeta fondo_azul">
                    <h3><%= torneo.getNombre() %></h3>
                    <span><%= torneo.getFechaInicio() != null ? torneo.getFechaInicio() : "Sin fecha" %></span>
                </div>
                <div class="cuerpo_tarjeta">
                    <p>Más información aquí...</p>
                    <div class="acciones_tarjeta">
                        <a href="#">Detalles</a>
                        <a href="#">Editar</a>
                    </div>
                </div>
            </div>
            <%
                }
            } else {
            %>
            <p>No hay torneos registrados.</p>
            <%
                }
            %>
        </div>
    </main>

    <!-- Modal de nuevo torneo (si quieres conservarlo en el futuro) -->
    <div class="modal" id="modalFormulario">
        <div class="modal_contenido">
            <span class="cerrar" id="cerrarModal">&times;</span>
            <h2>Nuevo Torneo</h2>
            <form class="formulario_encuesta" id="formTorneo">
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
