document.addEventListener('DOMContentLoaded', function() {
    // Elementos del modal
    const modal = document.getElementById('modalNuevoPartido');
    const btnAbrir = document.getElementById('abrirModal');
    const btnCerrar = document.querySelector('.cerrar-modal');
    const btnCancelar = document.querySelector('.boton-secundario');
    const form = document.getElementById('formNuevoPartido');

    // Abrir modal
    btnAbrir.addEventListener('click', function() {
        modal.style.display = 'block';
    });

    // Cerrar modal haciendo clic en la X
    btnCerrar.addEventListener('click', function() {
        modal.style.display = 'none';
    });

    // Cerrar modal haciendo clic en Cancelar
    btnCancelar.addEventListener('click', function() {
        modal.style.display = 'none';
    });

    // Cerrar modal haciendo clic fuera del contenido
    window.addEventListener('click', function(event) {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });

    // Manejar el envío del formulario
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        // Obtener valores del formulario
        const torneo = document.getElementById('torneo').value;
        const jornada = document.getElementById('jornada').value;
        const equipoLocal = document.getElementById('equipoLocal').value;
        const equipoVisitante = document.getElementById('equipoVisitante').value;
        const fecha = document.getElementById('fecha').value;
        const estadio = document.getElementById('estadio').value;

        // Validar que los equipos no sean iguales
        if (equipoLocal === equipoVisitante) {
            alert('Los equipos no pueden ser iguales');
            return;
        }

        // Aquí iría la lógica para guardar el partido
        console.log('Nuevo partido:', {
            torneo,
            jornada,
            equipoLocal,
            equipoVisitante,
            fecha,
            estadio
        });

        // Cerrar el modal después de guardar
        modal.style.display = 'none';

        // Resetear el formulario
        form.reset();

        // Aquí podrías agregar el nuevo partido a la lista sin recargar la página
        // agregarPartidoALista(torneo, jornada, equipoLocal, equipoVisitante, fecha, estadio);
    });

    // Función para formatear la fecha (opcional)
    function formatearFecha(fechaISO) {
        const opciones = {
            year: 'numeric',
            month: 'long',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        };
        return new Date(fechaISO).toLocaleDateString('es-ES', opciones);
    }
// Modal de Detalles
    const modalDetalle = document.getElementById('modalDetallePartido');
    const botonesDetalles = document.querySelectorAll('.ver-equipo, .accion_enlace'); // Ajusta si cambia tu botón
    const cerrarDetalle = document.querySelector('.cerrar-modal-detalle');

// Abrir modal de detalle al hacer click en cualquier botón "Ver detalles"
    botonesDetalles.forEach(btn => {
        btn.addEventListener('click', function(event) {
            event.preventDefault();
            modalDetalle.style.display = 'block';
        });
    });

// Cerrar modal detalle
    cerrarDetalle.addEventListener('click', function() {
        modalDetalle.style.display = 'none';
    });

// También cerrar si clickea afuera del contenido
    window.addEventListener('click', function(event) {
        if (event.target === modalDetalle) {
            modalDetalle.style.display = 'none';
        }
    });


});

