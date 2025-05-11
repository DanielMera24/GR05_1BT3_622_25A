document.addEventListener('DOMContentLoaded', function() {
    const botonNuevo = document.querySelector('.boton_nuevo');
    const modal = document.getElementById('modalNuevoJugador');
    const cerrarModalBtn = document.querySelector('.cerrar-modal');
    const btnCancelar = document.getElementById('btnCancelar');
    const formulario = document.getElementById('formNuevoJugador');

    botonNuevo.addEventListener('click', function() {
        modal.style.display = 'block';
        document.body.style.overflow = 'hidden';
    });

    cerrarModalBtn.addEventListener('click', function() {
        cerrarModal();
    });

    btnCancelar.addEventListener('click', function() {
        cerrarModal();
    });

    window.addEventListener('click', function(event) {
        if (event.target === modal) {
            cerrarModal();
        }
    });

    function cerrarModal() {
        modal.style.display = 'none';
        document.body.style.overflow = 'auto';
        formulario.reset();
    }

});