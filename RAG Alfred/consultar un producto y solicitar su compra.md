## FUNCIONALIDAD: CONSULTAR UN PRODUCTO Y SOLICITAR SU COMPRA

### OBJETIVO

Permite consultar los datos de un producto y enviar al vendedor una solicitud de compra por el precio publicado o mediante una oferta.

### REQUISITOS PREVIOS

* Estar autenticado para solicitar la compra.
* El producto debe seguir en venta.
* El producto no puede pertenecer al usuario actual.

### CÓMO SOLICITAR LA COMPRA DE UN PRODUCTO (PASO A PASO)

1. Entrar en la **Pantalla principal**.
2. Buscar el producto.
3. Seleccionar el producto.
4. Se abre la pantalla con la información del producto.
5. Revisar título, descripción, precio, vendedor y estado.
6. Para comprar por el precio publicado, pulsar **Comprar**.
7. Para proponer otro importe, pulsar **Hacer oferta**.
8. Introducir el importe de la oferta.
9. Pulsar **Hacer oferta**.
10. El sistema comprueba que el producto sigue disponible y que no pertenece al usuario.
11. Se registra una solicitud de compra.
12. El vendedor recibe una notificación por correo.
13. El producto deja de aparecer como disponible para compra en los resultados del usuario.
14. Se muestra **Solicitud de compra enviada**.

### ELEMENTOS DE LA PANTALLA

* **Imagen del producto:** Muestra la imagen publicada o una imagen predeterminada si no existe.
* **Título:** Nombre del producto.
* **Descripción:** Información descriptiva del producto.
* **Precio:** Precio publicado.
* **Oferta de [correo]:** Identifica al vendedor mediante su correo.
* **Estado:** Estado declarado del producto.
* **Comprar:** Envía una solicitud por el precio publicado.
* **Hacer oferta:** Abre el campo para introducir un precio alternativo.
* **Oferta:** Importe propuesto por el comprador.
* **mensaje:** Permite iniciar un chat con el vendedor.

### CASOS ESPECIALES Y VARIACIONES

* La oferta acepta valores numéricos con decimales y no admite valores inferiores a 0.
* El producto puede desaparecer de los resultados si se genera una solicitud o cambia su disponibilidad.
* La compra no se completa inmediatamente: se crea una solicitud que debe gestionar el vendedor.
* Un usuario no autenticado que intenta comprar es enviado a **Inicia sesión**.

### ERRORES Y SOLUCIONES

* **Error:** "Error producto ya vendido"

  * **Significado:** El producto dejó de estar disponible antes de procesar la solicitud.
  * **Acción requerida:** Seleccionar otro producto.
* **Error:** "Error producto de su propiedad"

  * **Significado:** El usuario intenta comprar uno de sus propios productos.
  * **Acción requerida:** Seleccionar otro producto.
* **Error:** "Error al enviar la solicitud de compra"

  * **Significado:** No se pudo registrar la solicitud o enviar la comunicación al vendedor.
  * **Acción requerida:** Comprobar la conexión y repetir la operación.

### RESULTADO ESPERADO

La solicitud de compra queda registrada y el vendedor puede aceptarla o rechazarla desde sus solicitudes de venta.

---