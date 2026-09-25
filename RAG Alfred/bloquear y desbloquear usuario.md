## FUNCIONALIDAD: BLOQUEAR O DESBLOQUEAR UN USUARIO

### OBJETIVO

Permite controlar la comunicación con otro usuario mediante bloqueo y desbloqueo desde un chat.

### REQUISITOS PREVIOS

* Estar autenticado.
* Tener un chat con el usuario que se desea bloquear o desbloquear.

### CÓMO BLOQUEAR O DESBLOQUEAR UN USUARIO (PASO A PASO)

1. Abrir **Chats**.
2. Seleccionar el chat del usuario.
3. Abrir el menú de tres puntos.
4. Si el usuario no está bloqueado, pulsar **Bloquear**.
5. El sistema registra el bloqueo.
6. Se vuelve a la pantalla desde la que se inició la acción.
7. Para retirar el bloqueo, volver al menú del mismo chat.
8. Pulsar **Desbloquear**.
9. El sistema elimina el bloqueo.
10. Se vuelve a la pantalla anterior.

### ELEMENTOS DE LA PANTALLA

* **Bloquear:** Impide la comunicación normal con el usuario bloqueado.
* **Desbloquear:** Retira el bloqueo existente.
* **Menú de tres puntos:** Contiene las acciones de gestión del chat.

### CASOS ESPECIALES Y VARIACIONES

* La opción visible cambia entre **Bloquear** y **Desbloquear** según el estado actual.
* Si el otro usuario ha bloqueado al usuario actual, el avatar del interlocutor puede mostrarse sin inicial.
* Los mensajes enviados mientras existe un bloqueo siguen un tratamiento específico del sistema.

### ERRORES Y SOLUCIONES

* **Error:** "Error al bloquear al usuario"

  * **Significado:** No se pudo crear el bloqueo.
  * **Acción requerida:** Comprobar la conexión y repetir la acción.
* **Error:** "Error al bloquear al usuario" al desbloquear.

  * **Significado:** No se pudo eliminar el bloqueo.
  * **Acción requerida:** Comprobar la conexión y repetir la acción.

### RESULTADO ESPERADO

El estado de bloqueo del usuario queda actualizado y el menú muestra la acción correspondiente al nuevo estado.

---