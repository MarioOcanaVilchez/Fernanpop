## FUNCIONALIDAD: CONSULTAR Y GESTIONAR CHATS

### OBJETIVO

Permite consultar conversaciones con otros usuarios, abrir un chat existente y gestionar sus opciones.

### REQUISITOS PREVIOS

* Estar autenticado.
* Para abrir una conversación existente, debe existir un chat.

### CÓMO CONSULTAR Y GESTIONAR CHATS (PASO A PASO)

1. Pulsar **Chats** en el menú inferior.
2. Se abre **Selector de chat**.
3. Localizar la conversación por el usuario mostrado.
4. Revisar el último mensaje y el número de mensajes no leídos.
5. Pulsar la conversación para abrirla.
6. En el chat se muestran los mensajes ordenados en la conversación.
7. Utilizar el menú de tres puntos del chat para acceder a **Vaciar chat**, **Bloquear** o **Desbloquear** y **Marcar mensajes leidos**, según corresponda.
8. Para volver al selector, utilizar el botón de regreso del encabezado del chat.

### ELEMENTOS DE LA PANTALLA

* **Selector de chat:** Lista de conversaciones existentes.
* **Nombre/correo del usuario:** Identifica al interlocutor.
* **Último mensaje:** Muestra el último contenido de la conversación.
* **Número de mensajes sin leer:** Indica mensajes pendientes de lectura.
* **Fecha:** Fecha y hora del último mensaje cuando está disponible.
* **Vaciar chat:** Elimina los mensajes del chat para el usuario.
* **Bloquear:** Bloquea al interlocutor.
* **Desbloquear:** Desbloquea al interlocutor.
* **Marcar mensajes leidos:** Marca los mensajes del chat como leídos.

### CASOS ESPECIALES Y VARIACIONES

* Si no existen chats, se muestra **No has iniciado ningún chat aún**.
* Si el otro usuario ha bloqueado al usuario actual, el avatar puede aparecer sin inicial.
* La opción **Bloquear** cambia a **Desbloquear** cuando el usuario ya está bloqueado.
* El contador de mensajes no leídos se actualiza al consultar el estado del chat.

### ERRORES Y SOLUCIONES

* **Error:** "Error al leer los mensajes"

  * **Significado:** No se pudieron marcar como leídos.
  * **Acción requerida:** Comprobar la conexión y repetir la acción.
* **Error:** "Error al bloquear al usuario"

  * **Significado:** No se pudo registrar el bloqueo.
  * **Acción requerida:** Comprobar la conexión y repetir la acción.
* **Error:** "Error al bloquear al usuario" al desbloquear.

  * **Significado:** No se pudo retirar el bloqueo.
  * **Acción requerida:** Comprobar la conexión y repetir la acción.

### RESULTADO ESPERADO

El usuario puede consultar sus conversaciones, identificar mensajes pendientes y gestionar las opciones de cada chat.

---