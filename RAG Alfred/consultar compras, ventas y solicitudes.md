## FUNCIONALIDAD: CONSULTAR COMPRAS, VENTAS Y SOLICITUDES

### OBJETIVO

Permite consultar el historial de operaciones y las solicitudes pendientes de compra y venta.

### REQUISITOS PREVIOS

* Estar autenticado.

### CÓMO CONSULTAR COMPRAS, VENTAS Y SOLICITUDES (PASO A PASO)

1. Abrir **Tratos** desde el menú inferior.
2. Seleccionar una de las pestañas disponibles.
3. Para consultar compras realizadas, seleccionar **Compras**.
4. Para consultar ventas realizadas, seleccionar **Ventas**.
5. Para consultar solicitudes enviadas, seleccionar **Solicitudes de compra**.
6. Para consultar solicitudes recibidas, seleccionar **Solicitudes de venta**.
7. Revisar los productos y datos de cada operación.
8. En compras, comprobar si la operación tiene valoración y comentario.
9. En solicitudes de compra, utilizar la acción disponible para retirar una solicitud.
10. En solicitudes de venta, utilizar las acciones para aceptar o rechazar.

### ELEMENTOS DE LA PANTALLA

* **Compras:** Historial de compras realizadas.
* **Ventas:** Historial de ventas realizadas.
* **Solicitudes de compra:** Solicitudes de compra pendientes enviadas por el usuario.
* **Solicitudes de venta:** Solicitudes de compra recibidas sobre productos del usuario.
* **Producto:** Producto relacionado con el trato.
* **Precio:** Importe de la operación o solicitud.
* **Fecha:** Fecha de la operación.
* **Puntuación:** Valoración registrada cuando existe.
* **Comentario:** Comentario asociado cuando existe.

### CASOS ESPECIALES Y VARIACIONES

* Si no existen compras, se muestra **No has realizado ninguna compra**.
* Si no existen ventas, se muestra **No has realizado ninguna venta**.
* Si no existen solicitudes de compra, se muestra **No has solicitado la compra de ningún producto**.
* Si no existen solicitudes de venta, se muestra **No tienes ninguna solicitud de venta**.
* Una compra sin valoración muestra **No puntuado**.
* Una operación sin comentario muestra **No Comentado**.

### ERRORES Y SOLUCIONES

* **Error:** "Error al retirar la solicitud de compra"

  * **Significado:** No se pudo eliminar la solicitud pendiente.
  * **Acción requerida:** Comprobar la conexión y repetir la operación.

### RESULTADO ESPERADO

El usuario puede consultar el estado de sus operaciones y gestionar las solicitudes pendientes.

---