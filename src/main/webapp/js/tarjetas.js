// Funcionalidad actualizada para la vista de tarjetas con escudos
document.addEventListener('DOMContentLoaded', function() {
    // Variables para los modales
    const modalNuevaTarjeta = document.getElementById('modalNuevaTarjeta');
    const modalEditarTarjeta = document.getElementById('modalEditarTarjeta');
    const botonNuevo = document.querySelector('.boton_nueva');
    const cerrarModalNuevo = document.querySelector('.cerrar-modal');
    const cerrarModalEditar = document.querySelector('.cerrar-modal-editar');
    const btnCancelar = document.getElementById('btnCancelar');
    const btnCancelarEditar = document.getElementById('btnCancelarEditar');

    // Selectores para filtrado
    const filtroTipo = document.getElementById('filtroTipo');
    const buscarTarjeta = document.getElementById('buscarTarjeta');
    const partidoSelector = document.getElementById('partidoSelector');

    // Formularios
    const formNuevaTarjeta = document.getElementById('formNuevaTarjeta');
    const formEditarTarjeta = document.getElementById('formEditarTarjeta');

    // Eventos para abrir y cerrar modales
    botonNuevo.addEventListener('click', function() {
        modalNuevaTarjeta.style.display = 'block';
    });

    cerrarModalNuevo.addEventListener('click', function() {
        modalNuevaTarjeta.style.display = 'none';
    });

    cerrarModalEditar.addEventListener('click', function() {
        modalEditarTarjeta.style.display = 'none';
    });

    btnCancelar.addEventListener('click', function() {
        modalNuevaTarjeta.style.display = 'none';
    });

    btnCancelarEditar.addEventListener('click', function() {
        modalEditarTarjeta.style.display = 'none';
    });

    // Cerrar modales al hacer clic fuera de ellos
    window.addEventListener('click', function(event) {
        if (event.target === modalNuevaTarjeta) {
            modalNuevaTarjeta.style.display = 'none';
        }
        if (event.target === modalEditarTarjeta) {
            modalEditarTarjeta.style.display = 'none';
        }
    });

    // Manejo de los botones de editar
    const botonesEditar = document.querySelectorAll('.editar');
    botonesEditar.forEach(boton => {
        boton.addEventListener('click', function() {
            // Obtener los datos del botón
            const id = this.getAttribute('data-id');
            const tipo = this.getAttribute('data-tipo');
            const jugador = this.getAttribute('data-jugador');
            const equipo = this.getAttribute('data-equipo');
            const minuto = this.getAttribute('data-minuto');
            const motivo = this.getAttribute('data-motivo');
            const partido = this.getAttribute('data-partido');

            // Rellenar el formulario de edición
            document.getElementById('idTarjetaEditar').value = id;
            document.getElementById('tipoTarjetaEditar').value = tipo;
            document.getElementById('minutoEditar').value = minuto;
            document.getElementById('motivoEditar').value = motivo;
            document.getElementById('partidoIdEditar').value = partido;

            // Para el jugador, habría que cargar las opciones según el partido seleccionado
            // y luego seleccionar el jugador correcto
            cargarJugadores(document.getElementById('partidoIdEditar'), document.getElementById('jugadorIdEditar'));

            // Mostrar el modal
            modalEditarTarjeta.style.display = 'block';
        });
    });

    // Manejo de los botones de eliminar
    const botonesEliminar = document.querySelectorAll('.eliminar');
    botonesEliminar.forEach(boton => {
        boton.addEventListener('click', function() {
            const id = this.getAttribute('data-id');
            if (confirm('¿Estás seguro de que deseas eliminar esta tarjeta?')) {
                // Aquí iría el código para eliminar la tarjeta
                // En una implementación real, esto sería una petición AJAX
                console.log('Eliminando tarjeta con ID:', id);

                // Simulación de eliminación exitosa
                alert('Tarjeta eliminada correctamente');
                // Recargar la página o eliminar el elemento del DOM
                this.closest('.tarjeta_sancion').remove();
            }
        });
    });

    // Filtrado por tipo de tarjeta
    filtroTipo.addEventListener('change', function() {
        const tipo = this.value.toLowerCase();
        const tarjetas = document.querySelectorAll('.tarjeta_sancion');

        tarjetas.forEach(tarjeta => {
            if (tipo === '' || tarjeta.classList.contains(tipo.toLowerCase())) {
                tarjeta.style.display = 'block';
            } else {
                tarjeta.style.display = 'none';
            }
        });
    });

    // Búsqueda de tarjetas por texto
    buscarTarjeta.addEventListener('input', function() {
        const termino = this.value.toLowerCase();
        const tarjetas = document.querySelectorAll('.tarjeta_sancion');

        tarjetas.forEach(tarjeta => {
            const nombreJugador = tarjeta.querySelector('.nombre_jugador').textContent.toLowerCase();
            const equipoJugador = tarjeta.querySelector('.equipo_jugador').textContent.toLowerCase();
            const motivoTarjeta = tarjeta.querySelector('.motivo_tarjeta').textContent.toLowerCase();

            if (nombreJugador.includes(termino) ||
                equipoJugador.includes(termino) ||
                motivoTarjeta.includes(termino)) {
                tarjeta.style.display = 'block';
            } else {
                tarjeta.style.display = 'none';
            }
        });
    });

    // Cambio de partido en el selector
    partidoSelector.addEventListener('change', function() {
        this.form.submit();
    });

    // Cambio dinámico de jugadores cuando se selecciona un partido
    const partidoId = document.getElementById('partidoId');
    const jugadorId = document.getElementById('jugadorId');
    const partidoIdEditar = document.getElementById('partidoIdEditar');
    const jugadorIdEditar = document.getElementById('jugadorIdEditar');

    // Función para cargar jugadores según el partido
    function cargarJugadores(partidoSelect, jugadorSelect) {
        const idPartido = partidoSelect.value;

        if (idPartido) {
            // En una implementación real, esto sería una petición AJAX
            // Aquí simplemente simulamos algunos jugadores
            jugadorSelect.innerHTML = '<option value="">Seleccione un jugador</option>';

            if (idPartido === '1') {
                // Jugadores para Barcelona vs Emelec
                agregarOpcion(jugadorSelect, '1', 'Carlos Gruezo (Barcelona) - #8');
                agregarOpcion(jugadorSelect, '2', 'Janner Corozo (Barcelona) - #11');
                agregarOpcion(jugadorSelect, '3', 'Aníbal Chalá (Emelec) - #5');
                agregarOpcion(jugadorSelect, '4', 'Joao Rojas (Emelec) - #10');
            } else if (idPartido === '2') {
                // Jugadores para Liga vs Aucas
                agregarOpcion(jugadorSelect, '5', 'Adrián Gabbarini (Liga de Quito) - #1');
                agregarOpcion(jugadorSelect, '6', 'Alexander Alvarado (Liga de Quito) - #7');
                agregarOpcion(jugadorSelect, '7', 'Roberto Ordóñez (Aucas) - #9');
                agregarOpcion(jugadorSelect, '8', 'Jhonny Quiñónez (Aucas) - #8');
            } else if (idPartido === '3') {
                // Jugadores para Dep. Cuenca vs Independiente
                agregarOpcion(jugadorSelect, '9', 'Lucas Mancinelli (Dep. Cuenca) - #10');
                agregarOpcion(jugadorSelect, '10', 'Sebastián Rodríguez (Dep. Cuenca) - #8');
                agregarOpcion(jugadorSelect, '11', 'Jonathan Bauman (Independiente) - #19');
                agregarOpcion(jugadorSelect, '12', 'Fernando Gaibor (Independiente) - #10');
            }
        } else {
            jugadorSelect.innerHTML = '<option value="">Seleccione un partido primero</option>';
        }
    }

    function agregarOpcion(select, valor, texto) {
        const opcion = document.createElement('option');
        opcion.value = valor;
        opcion.textContent = texto;
        select.appendChild(opcion);
    }

    // Eventos para cargar jugadores
    partidoId.addEventListener('change', function() {
        cargarJugadores(partidoId, jugadorId);
    });

    partidoIdEditar.addEventListener('change', function() {
        cargarJugadores(partidoIdEditar, jugadorIdEditar);
    });

    // Inicializar los selectores de jugadores si ya hay un partido seleccionado
    if (partidoId.value) {
        cargarJugadores(partidoId, jugadorId);
    }

    if (partidoIdEditar.value) {
        cargarJugadores(partidoIdEditar, jugadorIdEditar);
    }
});