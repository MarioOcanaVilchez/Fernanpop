## FUNCIONALIDAD: ENVIAR MENSAJES EN UN CHAT

### OBJETIVO

Permite enviar mensajes de texto a otro usuario desde una conversación.

### REQUISITOS PREVIOS

* Estar autenticado.
* Tener un chat abierto.
* El mensaje debe contener texto.

### CÓMO ENVIAR UN MENSAJE (PASO A PASO)

1. Abrir **Chats**.
2. Seleccionar una conversación.
3. Introducir el mensaje en el campo de escritura.
4. Pulsar el botón de envío.
5. El sistema comprueba el estado de bloqueo entre ambos usuarios.
6. Si no existe un bloqueo aplicable, guarda el mensaje y actualiza la fecha del último mensaje.
7. Si existe un bloqueo, el sistema utiliza el flujo específico para mensajes enviados en estado bloqueado.
8. El chat se recarga.
9. El mensaje aparece en la conversación.

### ELEMENTOS DE LA PANTALLA

* **Campo de mensaje:** Texto que se desea enviar.
* **Botón de envío:** Envía el contenido.
* **Mensaje propio:** Muestra los mensajes enviados por el usuario.
* **Mensaje de otro usuario:** Muestra el remitente, contenido y fecha.
* **Mensaje de administración:** Muestra mensajes generados por administración.

### CASOS ESPECIALES Y VARIACIONES

* El campo de mensaje es obligatorio.
* Un mensaje propio puede editarse si tiene menos de 24 horas y no ha sido eliminado.
* Los mensajes propios ofrecen **Eliminar para todos** y **Eliminar para mi** cuando corresponde.
* Los mensajes de otros usuarios y de administración disponen de la acción **Eliminar** para el usuario actual.

### ERRORES Y SOLUCIONES

* **Error:** "Error al enviar el mensaje"

  * **Significado:** No se pudo registrar el mensaje.
  * **Acción requerida:** Comprobar la conexión y repetir el envío.
* **Error:** "Error al editar el mensaje"

  * **Significado:** No se pudo actualizar el mensaje.
  * **Acción requerida:** Comprobar que el mensaje sigue siendo editable y repetir la operación.

### RESULTADO ESPERADO

El mensaje queda visible en el chat y la conversación actualiza su último mensaje.

---