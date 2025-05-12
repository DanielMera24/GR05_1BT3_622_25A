document.addEventListener('DOMContentLoaded', function() {
    // Elementos para el modal de nuevo jugador
    const botonNuevo = document.querySelector('.boton_nuevo');
    const modal = document.getElementById('modalNuevoJugador');
    const cerrarModalBtn = document.querySelector('.cerrar-modal');
    const btnCancelar = document.getElementById('btnCancelar');
    const formulario = document.getElementById('formNuevoJugador');

    const modalEditar = document.getElementById('modalEditarJugador');
    const cerrarModalEditar = document.querySelector('.cerrar-modal-editar');
    const btnCancelarEditar = document.getElementById('btnCancelarEditar');
    const formEditar = document.getElementById('formEditarJugador');

    if (botonNuevo) {
        botonNuevo.addEventListener('click', function() {
            modal.style.display = 'block';
            document.body.style.overflow = 'hidden';
        });
    }

    if (cerrarModalBtn) {
        cerrarModalBtn.addEventListener('click', function() {
            cerrarModal();
        });
    }

    if (btnCancelar) {
        btnCancelar.addEventListener('click', function() {
            cerrarModal();
        });
    }

    window.addEventListener('click', function(event) {
        if (event.target === modal) {
            cerrarModal();
        }
    });

    function cerrarModal() {
        modal.style.display = 'none';
        document.body.style.overflow = 'auto';
        if (formulario) {
            formulario.reset();
        }
    }

    document.addEventListener('click', function(event) {
        if (event.target.classList.contains('editar')) {
            event.preventDefault();
            event.stopPropagation();
            abrirModalEditar(event.target);
        }
    });

    if (cerrarModalEditar) {
        cerrarModalEditar.addEventListener('click', function() {
            cerrarModalEditarJugador();
        });
    }

    if (btnCancelarEditar) {
        btnCancelarEditar.addEventListener('click', function() {
            cerrarModalEditarJugador();
        });
    }

    // Cerrar modal editar al hacer clic fuera de él
    window.addEventListener('click', function(event) {
        if (event.target === modalEditar) {
            cerrarModalEditarJugador();
        }
    });

    function abrirModalEditar(botonEditar) {
        console.log('Abriendo modal de edición...');

        const jugadorData = {
            id: botonEditar.getAttribute('data-id'),
            cedula: botonEditar.getAttribute('data-cedula'),
            nombre: botonEditar.getAttribute('data-nombre'),
            edad: botonEditar.getAttribute('data-edad'),
            posicion: botonEditar.getAttribute('data-posicion'),
            dorsal: botonEditar.getAttribute('data-dorsal'),
            equipoNombre: botonEditar.getAttribute('data-equipo-nombre')
        };

        const cedulaInput = document.getElementById('cedulaEditar');
        const nombreInput = document.getElementById('nombreEditar');
        const edadInput = document.getElementById('edadEditar');
        const posicionSelect = document.getElementById('posicionEditar');
        const dorsalInput = document.getElementById('dorsalEditar');
        const equipoSelect = document.getElementById('equipoIdEditar');

        if (cedulaInput) cedulaInput.value = jugadorData.cedula || '';
        if (nombreInput) nombreInput.value = jugadorData.nombre || '';
        if (edadInput) edadInput.value = jugadorData.edad || '';
        if (posicionSelect) posicionSelect.value = jugadorData.posicion || '';
        if (dorsalInput) dorsalInput.value = jugadorData.dorsal || '';

        if (equipoSelect) {
            for (let i = 0; i < equipoSelect.options.length; i++) {
                if (equipoSelect.options[i].text === jugadorData.equipoNombre) {
                    equipoSelect.selectedIndex = i;
                    break;
                }
            }
        }

        if (modalEditar) {
            modalEditar.style.display = 'block';
            document.body.style.overflow = 'hidden';
        }
    }

    function cerrarModalEditarJugador() {
        if (modalEditar) {
            modalEditar.style.display = 'none';
            document.body.style.overflow = 'auto';
        }
        if (formEditar) {
            formEditar.reset();
        }
    }
});