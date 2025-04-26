// Obtener el modal y el botón que abre el modal
var modal = document.getElementById("modalFormulario");
var btn = document.querySelector(".boton_nuevo");

// Obtener el botón de cierre del modal
var cerrarModal = document.getElementById("cerrarModal");

// Abrir el modal al hacer clic en el botón "Nuevo Torneo"
btn.onclick = function() {
  modal.classList.add("show"); // Añadir la clase show para mostrar el modal
}

// Cerrar el modal al hacer clic en la "X"
cerrarModal.onclick = function() {
  modal.classList.remove("show"); // Quitar la clase show para cerrar el modal
}

// Cerrar el modal si el usuario hace clic fuera del modal
window.onclick = function(event) {
  if (event.target == modal) {
    modal.classList.remove("show"); // Cerrar el modal si el clic fue fuera
  }
}
