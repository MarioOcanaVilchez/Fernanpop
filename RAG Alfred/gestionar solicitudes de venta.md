## FUNCIONALIDAD: GESTIONAR SOLICITUDES DE VENTA

### OBJETIVO

Permite al vendedor revisar las solicitudes de compra recibidas y aceptar o rechazar una solicitud.

### REQUISITOS PREVIOS

* Estar autenticado.
* Tener al menos una solicitud de venta pendiente.

### CÓMO GESTIONAR UNA SOLICITUD DE VENTA (PASO A PASO)

1. Entrar en **Tratos** desde el menú inferior.
2. Seleccionar **Solicitudes de venta**.
3. Localizar la solicitud correspondiente al producto.
4. Revisar la información de la solicitud.
5. Para aceptar la solicitud, pulsar la acción de aceptación.
6. El sistema muestra una pantalla de espera con el mensaje **Aceptando solicitud de venta**.
7. El sistema comprueba que la solicitud todavía existe.
8. Si es válida, registra la venta.
9. Se envían comunicaciones por correo al comprador y al vendedor.
10. La solicitud deja de aparecer como pendiente.
11. Para rechazarla, seleccionar la acción de rechazo.
12. Si se rechaza correctamente, se vuelve a **Solicitudes de venta**.

### ELEMENTOS DE LA PANTALLA

* **Solicitudes de venta:** Lista de solicitudes pendientes recibidas por el vendedor.
* **Producto:** Producto solicitado.
* **Aceptar:** Acepta la solicitud y completa la venta.
* **Rechazar:** Retira la solicitud sin completar la venta.

### CASOS ESPECIALES Y VARIACIONES

* Una solicitud puede haber sido retirada antes de que el vendedor intente aceptarla.
* Al aceptar, se generan comunicaciones por correo y documentos PDF de la operación.
* La aceptación cambia el trato a una operación realizada.

### ERRORES Y SOLUCIONES

* **Error:** "Error al aceptar la solicitud de venta"

  * **Significado:** La venta no pudo completarse.
  * **Acción requerida:** Comprobar la conexión y volver a consultar las solicitudes.
* **Error:** "Solicitud retirada"

  * **Significado:** La solicitud ya no existe cuando se intenta aceptarla.
  * **Acción requerida:** Volver a **Solicitudes de venta** y revisar las solicitudes disponibles.
* **Error:** "Error al rechazar la solicitud de venta"

  * **Significado:** No se pudo retirar la solicitud.
  * **Acción requerida:** Comprobar la conexión y repetir la operación.

### RESULTADO ESPERADO

La solicitud aceptada se convierte en una venta realizada; una solicitud rechazada deja de estar pendiente.

---