document.addEventListener('DOMContentLoaded', function() {
    // Verificar que los elementos existen antes de usarlos
    const modalAgregarJugador = document.getElementById('modalAgregarJugador');
    const modalAgregarTarjeta = document.getElementById('modalAgregarTarjeta');

    // Botones para abrir modales
    const botonesAgregarJugador = document.querySelectorAll('.btn_agregar_jugador');
    const btnAgregarTarjeta = document.getElementById('agregarTarjeta');

    // Botones para cerrar modales - usar la clase correcta
    const cerrarModales = document.querySelectorAll('.cerrar_modal');
    const cancelarModales = document.querySelectorAll('.btn_cancelar_modal');

    // Contenedores
    const contenedorTarjetas = document.getElementById('contenedorTarjetas');

    // Formularios
    const formAgregarJugador = document.getElementById('formAgregarJugador');
    const formAgregarTarjeta = document.getElementById('formAgregarTarjeta');

    // Variable para controlar qué equipo está agregando jugadores
    let equipoActual = '';

    // Eventos para abrir modal de agregar jugador
    if (botonesAgregarJugador && botonesAgregarJugador.length > 0) {
        botonesAgregarJugador.forEach(btn => {
            btn.addEventListener('click', function() {
                equipoActual = this.getAttribute('data-equipo');
                cargarJugadoresDisponibles(equipoActual);
                if (modalAgregarJugador) {
                    modalAgregarJugador.style.display = 'block';
                }
            });
        });
    }

    // Evento para abrir modal de agregar tarjeta
    if (btnAgregarTarjeta && modalAgregarTarjeta) {
        btnAgregarTarjeta.addEventListener('click', function() {
            cargarJugadoresParaTarjeta();
            modalAgregarTarjeta.style.display = 'block';
        });
    }

    // Eventos para cerrar modales
    if (cerrarModales && cerrarModales.length > 0) {
        cerrarModales.forEach(btn => {
            btn.addEventListener('click', function() {
                cerrarTodosLosModales();
            });
        });
    }

    if (cancelarModales && cancelarModales.length > 0) {
        cancelarModales.forEach(btn => {
            btn.addEventListener('click', function() {
                cerrarTodosLosModales();
            });
        });
    }

    // Cerrar modales al hacer clic fuera
    window.addEventListener('click', function(event) {
        if (event.target === modalAgregarJugador || event.target === modalAgregarTarjeta) {
            cerrarTodosLosModales();
        }
    });

    // Función para cerrar todos los modales
    function cerrarTodosLosModales() {
        if (modalAgregarJugador) modalAgregarJugador.style.display = 'none';
        if (modalAgregarTarjeta) modalAgregarTarjeta.style.display = 'none';
        limpiarFormularios();
    }

    // Función para limpiar formularios
    function limpiarFormularios() {
        if (formAgregarJugador) formAgregarJugador.reset();
        if (formAgregarTarjeta) formAgregarTarjeta.reset();
    }

    // Función para cargar jugadores disponibles en el modal de agregar jugador
    function cargarJugadoresDisponibles(equipo) {
        const jugadorSelect = document.getElementById('jugadorSelect');
        if (!jugadorSelect) {
            console.error('No se encontró el elemento jugadorSelect');
            return;
        }

        jugadorSelect.innerHTML = '<option value="">Seleccione un jugador</option>';

        const jugadores = equipo === 'local' ? window.jugadoresLocal : window.jugadoresVisitante;

        if (jugadores && jugadores.length > 0) {
            jugadores.forEach(jugador => {
                const option = document.createElement('option');
                option.value = jugador.id;
                option.textContent = `${jugador.nombre} (#${jugador.dorsal})`;
                option.setAttribute('data-nombre', jugador.nombre);
                option.setAttribute('data-dorsal', jugador.dorsal);
                jugadorSelect.appendChild(option);
            });
        }
    }

    // Función para cargar jugadores de ambos equipos en el modal de tarjeta
    function cargarJugadoresParaTarjeta() {
        const jugadorSelect = document.getElementById('jugadorTarjeta');
        if (!jugadorSelect) {
            console.error('No se encontró el elemento jugadorTarjeta');
            return;
        }

        jugadorSelect.innerHTML = '<option value="">Seleccione un jugador</option>';

        // Verificar que existen los datos necesarios
        if (!window.partidoData || !window.jugadoresLocal || !window.jugadoresVisitante) {
            console.error('Datos del partido no disponibles');
            return;
        }

        // Agregar jugadores del equipo local
        if (window.jugadoresLocal && window.jugadoresLocal.length > 0) {
            const grupoLocal = document.createElement('optgroup');
            grupoLocal.label = window.partidoData.equipoLocal.nombre;

            window.jugadoresLocal.forEach(jugador => {
                const option = document.createElement('option');
                option.value = jugador.id;
                option.textContent = `${jugador.nombre} (#${jugador.dorsal})`;
                option.setAttribute('data-nombre', jugador.nombre);
                option.setAttribute('data-dorsal', jugador.dorsal);
                option.setAttribute('data-equipo', window.partidoData.equipoLocal.nombre);
                grupoLocal.appendChild(option);
            });

            jugadorSelect.appendChild(grupoLocal);
        }

        // Agregar jugadores del equipo visitante
        if (window.jugadoresVisitante && window.jugadoresVisitante.length > 0) {
            const grupoVisitante = document.createElement('optgroup');
            grupoVisitante.label = window.partidoData.equipoVisitante.nombre;

            window.jugadoresVisitante.forEach(jugador => {
                const option = document.createElement('option');
                option.value = jugador.id;
                option.textContent = `${jugador.nombre} (#${jugador.dorsal})`;
                option.setAttribute('data-nombre', jugador.nombre);
                option.setAttribute('data-dorsal', jugador.dorsal);
                option.setAttribute('data-equipo', window.partidoData.equipoVisitante.nombre);
                grupoVisitante.appendChild(option);
            });

            jugadorSelect.appendChild(grupoVisitante);
        }
    }

    // Manejo del formulario de agregar jugador
    if (formAgregarJugador) {
        formAgregarJugador.addEventListener('submit', function(event) {
            event.preventDefault();

            const jugadorSelect = document.getElementById('jugadorSelect');
            const golesInput = document.getElementById('golesJugador');

            if (!jugadorSelect || !jugadorSelect.value) {
                alert('Por favor seleccione un jugador');
                return;
            }

            const selectedOption = jugadorSelect.options[jugadorSelect.selectedIndex];
            const nombreJugador = selectedOption.getAttribute('data-nombre');
            const dorsalJugador = selectedOption.getAttribute('data-dorsal');
            const golesJugador = golesInput ? golesInput.value || 0 : 0;

            // Agregar jugador a la tabla correspondiente
            agregarJugadorATabla(equipoActual, {
                id: jugadorSelect.value,
                nombre: nombreJugador,
                dorsal: dorsalJugador,
                goles: golesJugador
            });

            cerrarTodosLosModales();
        });
    }

    // Manejo del formulario de agregar tarjeta
    if (formAgregarTarjeta) {
        formAgregarTarjeta.addEventListener('submit', function(event) {
            event.preventDefault();

            const tipoTarjeta = document.getElementById('tipoTarjeta');
            const jugadorSelect = document.getElementById('jugadorTarjeta');
            const minutoTarjeta = document.getElementById('minutoTarjeta');
            const motivoTarjeta = document.getElementById('motivoTarjeta');

            if (!tipoTarjeta || !jugadorSelect || !minutoTarjeta || !motivoTarjeta) {
                alert('Error: No se encontraron todos los campos del formulario');
                return;
            }

            if (!tipoTarjeta.value || !jugadorSelect.value || !minutoTarjeta.value || !motivoTarjeta.value) {
                alert('Por favor complete todos los campos');
                return;
            }

            const selectedOption = jugadorSelect.options[jugadorSelect.selectedIndex];
            const nombreJugador = selectedOption.getAttribute('data-nombre');
            const dorsalJugador = selectedOption.getAttribute('data-dorsal');
            const equipoJugador = selectedOption.getAttribute('data-equipo');

            // Agregar tarjeta al contenedor
            agregarTarjetaAlContenedor({
                tipo: tipoTarjeta.value,
                jugador: nombreJugador,
                dorsal: dorsalJugador,
                equipo: equipoJugador,
                minuto: minutoTarjeta.value,
                motivo: motivoTarjeta.value
            });

            cerrarTodosLosModales();
        });
    }

    // Función para agregar jugador a la tabla
    function agregarJugadorATabla(equipo, jugadorData) {
        const tablaId = equipo === 'local' ? 'jugadoresLocal' : 'jugadoresVisitante';
        const tbody = document.getElementById(tablaId);

        if (!tbody) {
            console.error(`No se encontró la tabla con ID: ${tablaId}`);
            return;
        }

        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${jugadorData.dorsal}</td>
            <td>${jugadorData.nombre}</td>
            <td>
                <input type="number" class="input_goles" value="${jugadorData.goles}" min="0" max="10" />
            </td>
            <td>
                <input type="radio" name="capitan${equipo === 'local' ? 'Local' : 'Visitante'}" value="${jugadorData.id}" />
            </td>
            <td>
                <button type="button" class="btn_eliminar" onclick="eliminarJugadorDeTabla(this)">🗑️</button>
            </td>
        `;

        tbody.appendChild(row);
    }

    // Función para eliminar jugador de la tabla
    window.eliminarJugadorDeTabla = function(button) {
        if (confirm('¿Está seguro de eliminar este jugador del partido?')) {
            button.closest('tr').remove();
        }
    };

    // Función para agregar tarjeta al contenedor
    function agregarTarjetaAlContenedor(tarjetaData) {
        if (!contenedorTarjetas) {
            console.error('No se encontró el contenedor de tarjetas');
            return;
        }

        const tarjetaElement = document.createElement('div');
        tarjetaElement.className = `tarjeta_partido ${tarjetaData.tipo.toLowerCase()}`;

        tarjetaElement.innerHTML = `
            <div class="indicador_tarjeta ${tarjetaData.tipo.toLowerCase()}"></div>
            <div class="info_tarjeta">
                <div class="nombre_jugador">${tarjetaData.jugador}</div>
                <div class="equipo_jugador">${tarjetaData.equipo}</div>
                <div class="detalle_tarjeta">Minuto ${tarjetaData.minuto}' - ${tarjetaData.motivo}</div>
            </div>
            <button type="button" class="eliminar_tarjeta" onclick="eliminarTarjeta(this)">×</button>
        `;

        contenedorTarjetas.appendChild(tarjetaElement);
    }

    // Función para eliminar tarjeta
    window.eliminarTarjeta = function(button) {
        if (confirm('¿Está seguro de eliminar esta tarjeta?')) {
            button.closest('.tarjeta_partido').remove();
        }
    };

    // Agregar eventos a los botones de eliminar tarjeta existentes
    document.addEventListener('click', function(event) {
        if (event.target.classList.contains('eliminar_tarjeta')) {
            if (confirm('¿Está seguro de eliminar esta tarjeta?')) {
                event.target.closest('.tarjeta_partido').remove();
            }
        }
    });

    // Validación del formulario principal antes de enviar
    const formPrincipal = document.getElementById('formEditarPartido');
    if (formPrincipal) {
        formPrincipal.addEventListener('submit', function(event) {
            console.log('Enviando formulario de edición de partido...');

            // Recopilar datos adicionales si es necesario
            const jugadoresTabla = document.querySelectorAll('.tabla_participantes tbody tr');
            const tarjetasExistentes = document.querySelectorAll('.tarjeta_partido');

            console.log(`Jugadores en tablas: ${jugadoresTabla.length}`);
            console.log(`Tarjetas del partido: ${tarjetasExistentes.length}`);
        });
    }

    // Debug: Verificar que los datos estén disponibles
    console.log('Datos disponibles:');
    console.log('partidoData:', window.partidoData);
    console.log('jugadoresLocal:', window.jugadoresLocal);
    console.log('jugadoresVisitante:', window.jugadoresVisitante);
});