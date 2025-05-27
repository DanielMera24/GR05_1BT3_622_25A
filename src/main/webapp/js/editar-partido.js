document.addEventListener('DOMContentLoaded', function () {
    // Verificar que los elementos existen antes de usarlos
    const modalAgregarJugador = document.getElementById('modalAgregarJugador');
    const modalAgregarGol = document.getElementById('modalAgregarGol');
    const modalAgregarTarjeta = document.getElementById('modalAgregarTarjeta');

    // Botones para abrir modales
    const botonesAgregarJugador = document.querySelectorAll('.btn_agregar_jugador');
    const btnAgregarGol = document.getElementById('agregarGol');
    const btnAgregarTarjeta = document.getElementById('agregarTarjeta');

    // Botones para cerrar modales - usar la clase correcta
    const cerrarModales = document.querySelectorAll('.cerrar_modal');
    const cancelarModales = document.querySelectorAll('.btn_cancelar_modal');

    // Contenedores
    const contenedorGoles = document.getElementById('contenedorGoles');
    const contenedorTarjetas = document.getElementById('contenedorTarjetas');

    // Formularios
    const formAgregarJugador = document.getElementById('formAgregarJugador');
    const formAgregarGol = document.getElementById('formAgregarGol');
    const formAgregarTarjeta = document.getElementById('formAgregarTarjeta');

    // Variable para controlar qué equipo está agregando jugadores
    let equipoActual = '';

    // Arrays para almacenar los datos
    if (!window.jugadoresParticipantes) window.jugadoresParticipantes = [];
    if (!window.golesPartido) window.golesPartido = [];
    if (!window.tarjetasPartido) window.tarjetasPartido = [];

    // Eventos para abrir modal de agregar jugador
    if (botonesAgregarJugador && botonesAgregarJugador.length > 0) {
        botonesAgregarJugador.forEach(btn => {
            btn.addEventListener('click', function () {
                equipoActual = this.getAttribute('data-equipo');
                cargarJugadoresDisponibles(equipoActual);
                if (modalAgregarJugador) {
                    modalAgregarJugador.style.display = 'block';
                }
            });
        });
    }

    // Evento para abrir modal de agregar gol
    if (btnAgregarGol && modalAgregarGol) {
        btnAgregarGol.addEventListener('click', function () {
            cargarJugadoresParaGol();
            modalAgregarGol.style.display = 'block';
        });
    }

    // Evento para abrir modal de agregar tarjeta
    if (btnAgregarTarjeta && modalAgregarTarjeta) {
        btnAgregarTarjeta.addEventListener('click', function () {
            cargarJugadoresParaTarjeta();
            modalAgregarTarjeta.style.display = 'block';
        });
    }

    // Eventos para cerrar modales
    if (cerrarModales && cerrarModales.length > 0) {
        cerrarModales.forEach(btn => {
            btn.addEventListener('click', function () {
                cerrarTodosLosModales();
            });
        });
    }

    if (cancelarModales && cancelarModales.length > 0) {
        cancelarModales.forEach(btn => {
            btn.addEventListener('click', function () {
                cerrarTodosLosModales();
            });
        });
    }

    // Cerrar modales al hacer clic fuera
    window.addEventListener('click', function (event) {
        if (event.target === modalAgregarJugador || event.target === modalAgregarGol || event.target === modalAgregarTarjeta) {
            cerrarTodosLosModales();
        }
    });

    // Función para cerrar todos los modales
    function cerrarTodosLosModales() {
        if (modalAgregarJugador) modalAgregarJugador.style.display = 'none';
        if (modalAgregarGol) modalAgregarGol.style.display = 'none';
        if (modalAgregarTarjeta) modalAgregarTarjeta.style.display = 'none';
        limpiarFormularios();
    }

    // Función para limpiar formularios
    function limpiarFormularios() {
        if (formAgregarJugador) formAgregarJugador.reset();
        if (formAgregarGol) formAgregarGol.reset();
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

    // Función para cargar jugadores de ambos equipos en el modal de gol
    function cargarJugadoresParaGol() {
        const jugadorSelect = document.getElementById('jugadorGol');
        if (!jugadorSelect) {
            console.error('No se encontró el elemento jugadorGol');
            return;
        }

        cargarJugadoresEnSelect(jugadorSelect);
    }

    // Función para cargar jugadores de ambos equipos en el modal de tarjeta
    function cargarJugadoresParaTarjeta() {
        const jugadorSelect = document.getElementById('jugadorTarjeta');
        if (!jugadorSelect) {
            console.error('No se encontró el elemento jugadorTarjeta');
            return;
        }

        cargarJugadoresEnSelect(jugadorSelect);
    }

    // Función reutilizable para cargar jugadores en cualquier select
    function cargarJugadoresEnSelect(jugadorSelect) {
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
                option.setAttribute('data-equipo-id', window.partidoData.equipoLocal.id);
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
                option.setAttribute('data-equipo-id', window.partidoData.equipoVisitante.id);
                grupoVisitante.appendChild(option);
            });

            jugadorSelect.appendChild(grupoVisitante);
        }
    }

    // Manejo del formulario de agregar jugador (SIN campo de goles)
    if (formAgregarJugador) {
        formAgregarJugador.addEventListener('submit', function (event) {
            event.preventDefault();

            const jugadorSelect = document.getElementById('jugadorSelect');

            if (!jugadorSelect || !jugadorSelect.value) {
                alert('Por favor seleccione un jugador');
                return;
            }

            const selectedOption = jugadorSelect.options[jugadorSelect.selectedIndex];
            const nombreJugador = selectedOption.getAttribute('data-nombre');
            const dorsalJugador = selectedOption.getAttribute('data-dorsal');
            const equipoId = equipoActual === 'local' ? window.partidoData.equipoLocal.id : window.partidoData.equipoVisitante.id;

            const jugadorData = {
                id: jugadorSelect.value,
                nombre: nombreJugador,
                dorsal: dorsalJugador,
                equipoId: equipoId,
                equipo: equipoActual,
                esCapitan: false
            };

            // Agregar a array de jugadores participantes
            jugadoresParticipantes.push(jugadorData);

            // Agregar jugador a la tabla correspondiente (sin goles)
            agregarJugadorATabla(equipoActual, jugadorData);

            cerrarTodosLosModales();
        });
    }

    // Manejo del formulario de agregar gol
    if (formAgregarGol) {
        formAgregarGol.addEventListener('submit', function (event) {
            event.preventDefault();

            const jugadorSelect = document.getElementById('jugadorGol');
            const minutoGol = document.getElementById('minutoGol');

            if (!jugadorSelect || !minutoGol) {
                alert('Error: No se encontraron todos los campos del formulario');
                return;
            }

            if (!jugadorSelect.value || !minutoGol.value) {
                alert('Por favor complete todos los campos');
                return;
            }

            const selectedOption = jugadorSelect.options[jugadorSelect.selectedIndex];
            const nombreJugador = selectedOption.getAttribute('data-nombre');
            const dorsalJugador = selectedOption.getAttribute('data-dorsal');
            const equipoJugador = selectedOption.getAttribute('data-equipo');

            const golData = {
                jugadorId: jugadorSelect.value,
                jugador: nombreJugador,
                dorsal: dorsalJugador,
                equipo: equipoJugador,
                minuto: minutoGol.value
            };

            // Agregar a array de goles
            golesPartido.push(golData);

            // Agregar gol al contenedor visual
            agregarGolAlContenedor(golData);

            cerrarTodosLosModales();
        });
    }

    // Manejo del formulario de agregar tarjeta
    if (formAgregarTarjeta) {
        formAgregarTarjeta.addEventListener('submit', function (event) {
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

            const tarjetaData = {
                tipo: tipoTarjeta.value,
                jugadorId: jugadorSelect.value,
                jugador: nombreJugador,
                dorsal: dorsalJugador,
                equipo: equipoJugador,
                minuto: minutoTarjeta.value,
                motivo: motivoTarjeta.value
            };

            // Agregar a array de tarjetas
            tarjetasPartido.push(tarjetaData);

            // Agregar tarjeta al contenedor visual
            agregarTarjetaAlContenedor(tarjetaData);

            cerrarTodosLosModales();
        });
    }
    if (window.golesPartido && window.golesPartido.length > 0) {
        const golesElementos = contenedorGoles.querySelectorAll('.gol_partido');
        golesElementos.forEach((elemento, index) => {
            elemento.setAttribute('data-gol-index', index);
            const botonEliminar = elemento.querySelector('.eliminar_gol');
            if (botonEliminar) {
                botonEliminar.setAttribute('onclick', `eliminarGol(this, ${index})`);
            }
        });
    }

    if (window.tarjetasPartido && window.tarjetasPartido.length > 0) {
        const tarjetasElementos = contenedorTarjetas.querySelectorAll('.tarjeta_partido');
        tarjetasElementos.forEach((elemento, index) => {
            elemento.setAttribute('data-tarjeta-index', index);
            const botonEliminar = elemento.querySelector('.eliminar_tarjeta');
            if (botonEliminar) {
                botonEliminar.setAttribute('onclick', `eliminarTarjeta(this, ${index})`);
            }
        });
    }

    // Función para agregar jugador a la tabla (SIN columna de goles)
    function agregarJugadorATabla(equipo, jugadorData) {
        const tablaId = equipo === 'local' ? 'jugadoresLocal' : 'jugadoresVisitante';
        const tbody = document.getElementById(tablaId);

        if (!tbody) {
            console.error(`No se encontró la tabla con ID: ${tablaId}`);
            return;
        }

        const row = document.createElement('tr');
        row.setAttribute('data-jugador-id', jugadorData.id);
        row.innerHTML = `
            <td>${jugadorData.dorsal}</td>
            <td>${jugadorData.nombre}</td>
            <td>
                <input type="radio" name="capitan${equipo === 'local' ? 'Local' : 'Visitante'}" 
                       value="${jugadorData.id}" onchange="actualizarCapitan('${jugadorData.id}', '${equipo}')" />
            </td>
            <td>
                <button type="button" class="btn_eliminar" onclick="eliminarJugadorDeTabla(this, '${jugadorData.id}')">🗑️</button>
            </td>
        `;

        tbody.appendChild(row);
    }

    // Función para actualizar capitán
    window.actualizarCapitan = function (jugadorId, equipo) {
        jugadoresParticipantes.forEach(jugador => {
            if (jugador.equipo === equipo) {
                jugador.esCapitan = (jugador.id == jugadorId);
            }
        });
    };

    // Función para eliminar jugador de la tabla
    window.eliminarJugadorDeTabla = function (button, jugadorId) {
        if (confirm('¿Está seguro de eliminar este jugador del partido?')) {
            // Eliminar de la tabla visual
            button.closest('tr').remove();

            // Eliminar del array de jugadores participantes
            jugadoresParticipantes = jugadoresParticipantes.filter(jugador => jugador.id != jugadorId);
        }
    };

    // Función para agregar gol al contenedor
    function agregarGolAlContenedor(golData) {
        if (!contenedorGoles) {
            console.error('No se encontró el contenedor de goles');
            return;
        }

        const golElement = document.createElement('div');
        golElement.className = 'gol_partido';
        golElement.setAttribute('data-gol-index', golesPartido.length - 1);

        golElement.innerHTML = `
            <div class="indicador_gol">⚽</div>
            <div class="info_gol">
                <div class="nombre_jugador">${golData.jugador}</div>
                <div class="equipo_jugador">${golData.equipo}</div>
                <div class="detalle_gol">Minuto ${golData.minuto}'</div>
            </div>
            <button type="button" class="eliminar_gol" onclick="eliminarGol(this, ${golesPartido.length - 1})">×</button>
        `;

        contenedorGoles.appendChild(golElement);
    }

    // Función para eliminar gol
    window.eliminarGol = function (button, index) {
        if (confirm('¿Está seguro de eliminar este gol?')) {
            // Eliminar del array
            golesPartido.splice(index, 1);

            // Eliminar del DOM
            button.closest('.gol_partido').remove();

            // Reindexar los elementos restantes
            actualizarIndicesGoles();
        }
    };

    // Función para reindexar goles después de eliminar
    function actualizarIndicesGoles() {
        const golesElementos = contenedorGoles.querySelectorAll('.gol_partido');
        golesElementos.forEach((elemento, index) => {
            elemento.setAttribute('data-gol-index', index);
            const botonEliminar = elemento.querySelector('.eliminar_gol');
            botonEliminar.setAttribute('onclick', `eliminarGol(this, ${index})`);
        });
    }

    // Función para agregar tarjeta al contenedor
    function agregarTarjetaAlContenedor(tarjetaData) {
        if (!contenedorTarjetas) {
            console.error('No se encontró el contenedor de tarjetas');
            return;
        }

        const tarjetaElement = document.createElement('div');
        tarjetaElement.className = `tarjeta_partido ${tarjetaData.tipo.toLowerCase()}`;
        tarjetaElement.setAttribute('data-tarjeta-index', tarjetasPartido.length - 1);

        tarjetaElement.innerHTML = `
            <div class="indicador_tarjeta ${tarjetaData.tipo.toLowerCase()}"></div>
            <div class="info_tarjeta">
                <div class="nombre_jugador">${tarjetaData.jugador}</div>
                <div class="equipo_jugador">${tarjetaData.equipo}</div>
                <div class="detalle_tarjeta">Minuto ${tarjetaData.minuto}' - ${tarjetaData.motivo}</div>
            </div>
            <button type="button" class="eliminar_tarjeta" onclick="eliminarTarjeta(this, ${tarjetasPartido.length - 1})">×</button>
        `;

        contenedorTarjetas.appendChild(tarjetaElement);
    }

    // Función para eliminar tarjeta
    window.eliminarTarjeta = function (button, index) {
        if (confirm('¿Está seguro de eliminar esta tarjeta?')) {
            // Eliminar del array
            tarjetasPartido.splice(index, 1);

            // Eliminar del DOM
            button.closest('.tarjeta_partido').remove();

            // Reindexar los elementos restantes
            actualizarIndicesTarjetas();
        }
    };

    // Función para reindexar tarjetas después de eliminar
    function actualizarIndicesTarjetas() {
        const tarjetasElementos = contenedorTarjetas.querySelectorAll('.tarjeta_partido');
        tarjetasElementos.forEach((elemento, index) => {
            elemento.setAttribute('data-tarjeta-index', index);
            const botonEliminar = elemento.querySelector('.eliminar_tarjeta');
            botonEliminar.setAttribute('onclick', `eliminarTarjeta(this, ${index})`);
        });
    }

    // Validación del formulario principal antes de enviar
    // Validación del formulario principal antes de enviar
    const formPrincipal = document.getElementById('formEditarPartido');
    if (formPrincipal) {
        formPrincipal.addEventListener('submit', function (event) {
            console.log('Enviando formulario de edición de partido...');
            console.log('Jugadores participantes:', jugadoresParticipantes);
            console.log('Goles del partido:', golesPartido);
            console.log('Tarjetas del partido:', tarjetasPartido);

            // Agregar campos hidden para jugadores participantes
            if (jugadoresParticipantes.length > 0) {
                const cantidadJugadores = document.createElement('input');
                cantidadJugadores.type = 'hidden';
                cantidadJugadores.name = 'cantidadJugadores';
                cantidadJugadores.value = jugadoresParticipantes.length;
                formPrincipal.appendChild(cantidadJugadores);

                jugadoresParticipantes.forEach((jugador, index) => {
                    const campos = [
                        {name: `jugador_${index}_id`, value: jugador.id},
                        {name: `jugador_${index}_equipoId`, value: jugador.equipoId},
                        {name: `jugador_${index}_dorsal`, value: jugador.dorsal},
                        {name: `jugador_${index}_capitan`, value: jugador.esCapitan ? 'true' : 'false'}
                    ];

                    campos.forEach(campo => {
                        const input = document.createElement('input');
                        input.type = 'hidden';
                        input.name = campo.name;
                        input.value = campo.value;
                        formPrincipal.appendChild(input);
                    });
                });
            }

            // Agregar campos hidden para goles
            if (golesPartido.length > 0) {
                const golesPorJugador = {};

                golesPartido.forEach(gol => {
                    if (!golesPorJugador[gol.jugadorId]) {
                        golesPorJugador[gol.jugadorId] = [];
                    }
                    golesPorJugador[gol.jugadorId].push(gol.minuto);
                });

                jugadoresParticipantes.forEach((jugador, index) => {
                    const golesJugador = golesPorJugador[jugador.id] || [];

                    const inputCantidad = document.createElement('input');
                    inputCantidad.type = 'hidden';
                    inputCantidad.name = `jugador_${index}_cantidadGoles`;
                    inputCantidad.value = golesJugador.length;
                    formPrincipal.appendChild(inputCantidad);

                    golesJugador.forEach((minuto, j) => {
                        const inputMinuto = document.createElement('input');
                        inputMinuto.type = 'hidden';
                        inputMinuto.name = `jugador_${index}_gol_${j}_minuto`;
                        inputMinuto.value = minuto;
                        formPrincipal.appendChild(inputMinuto);
                    });
                });
            }

            // Agregar campos hidden para tarjetas
            if (tarjetasPartido.length > 0) {
                const cantidadTarjetas = document.createElement('input');
                cantidadTarjetas.type = 'hidden';
                cantidadTarjetas.name = 'cantidadTarjetas';
                cantidadTarjetas.value = tarjetasPartido.length;
                formPrincipal.appendChild(cantidadTarjetas);

                tarjetasPartido.forEach((tarjeta, index) => {
                    const campos = [
                        {name: `tarjeta_${index}_tipo`, value: tarjeta.tipo},
                        {name: `tarjeta_${index}_jugadorId`, value: tarjeta.jugadorId},
                        {name: `tarjeta_${index}_minuto`, value: tarjeta.minuto},
                        {name: `tarjeta_${index}_motivo`, value: tarjeta.motivo}
                    ];

                    campos.forEach(campo => {
                        const input = document.createElement('input');
                        input.type = 'hidden';
                        input.name = campo.name;
                        input.value = campo.value;
                        formPrincipal.appendChild(input);
                    });
                });
            }

            // (Opcional) Verificación de lo que se enviará
            console.log('Datos que se enviarán al backend:');
            console.log([...new FormData(formPrincipal).entries()]);
        });
    }


    // Debug: Verificar que los datos estén disponibles
    // Asegurarse que los datos de los arrays están cargados antes de usarlos
    console.log('Jugadores participantes:', window.jugadoresParticipantes);
    console.log('Datos disponibles:');
    console.log('partidoData:', window.partidoData);
    console.log('jugadoresLocal:', window.jugadoresLocal);
    console.log('jugadoresVisitante:', window.jugadoresVisitante);
});