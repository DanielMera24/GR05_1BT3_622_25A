document.addEventListener('DOMContentLoaded', function() {
    // Elementos del modal Nuevo Equipo
    const modalNuevoEquipo = document.getElementById("modalNuevoEquipo");
    const btnNuevoEquipo = document.getElementById("btnNuevoEquipo");
    const btnCerrarNuevoEquipo = document.getElementById("cerrarNuevoEquipo");
    const formNuevoEquipo = document.getElementById("formNuevoEquipo");

    // Elementos del modal Editar Jugadores
    const modalEditarJugadores = document.getElementById("modalEditarJugadores");
    const btnCerrarEditarJugadores = document.getElementById("cerrarEditarJugadores");
    const formJugadores = document.getElementById("formJugadores");
    const agregarJugadorBtn = document.getElementById("agregarJugador");
    const contenedorJugadores = document.getElementById("contenedorJugadores");

    // Botones de editar en la tabla
    const botonesEditar = document.querySelectorAll('.editar-equipo');

    // Funciones de manejo de modales
    function abrirModalNuevoEquipo() {
        modalNuevoEquipo.classList.add("show");
    }

    function cerrarModalNuevoEquipo() {
        modalNuevoEquipo.classList.remove("show");
    }

    function abrirModalEditarJugadores() {
        modalEditarJugadores.classList.add("show");
    }

    function cerrarModalEditarJugadores() {
        modalEditarJugadores.classList.remove("show");
    }

    function manejarClicExterno(event) {
        if (event.target === modalNuevoEquipo) {
            cerrarModalNuevoEquipo();
        }
        if (event.target === modalEditarJugadores) {
            cerrarModalEditarJugadores();
        }
    }

    // Ahora permitimos el envío normal del formulario para que llegue al servlet
    function manejarEnvioNuevoEquipo(e) {
        // No hacemos preventDefault, dejamos que el formulario se envíe al servlet
        // Podrías mostrar aquí un loader o mensaje antes de la recarga
        cerrarModalNuevoEquipo();
        // El formulario seguirá su acción (POST a /crearEquipo)
    }

    function manejarEnvioJugadores(e) {
        e.preventDefault();
        // Lógica para guardar los jugadores editados (AJAX o similar)
        cerrarModalEditarJugadores();
    }

    function agregarJugador() {
        const jugadorDiv = document.createElement("div");
        jugadorDiv.className = "jugador_item";
        jugadorDiv.innerHTML = `
            <input type="text" placeholder="Nombre del jugador" name="nombreJugador[]" required>
            <input type="number" placeholder="Número de camiseta" name="numeroJugador[]" min="1" max="99" required>
        `;
        contenedorJugadores.appendChild(jugadorDiv);
    }

    // Asignación de eventos
    btnNuevoEquipo.addEventListener('click', abrirModalNuevoEquipo);
    btnCerrarNuevoEquipo.addEventListener('click', cerrarModalNuevoEquipo);
    formNuevoEquipo.addEventListener('submit', manejarEnvioNuevoEquipo);

    agregarJugadorBtn.addEventListener('click', agregarJugador);
    btnCerrarEditarJugadores.addEventListener('click', cerrarModalEditarJugadores);
    formJugadores.addEventListener('submit', manejarEnvioJugadores);

    botonesEditar.forEach(function(boton) {
        boton.addEventListener('click', abrirModalEditarJugadores);
    });

    window.addEventListener('click', manejarClicExterno);
});