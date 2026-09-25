## FUNCIONALIDAD: CREAR UN CHAT CON OTRO USUARIO

### OBJETIVO

Permite iniciar una conversación con el usuario que vende un producto.

### REQUISITOS PREVIOS

* Estar autenticado.
* El usuario destinatario debe existir.

### CÓMO CREAR UN CHAT CON OTRO USUARIO (PASO A PASO)

1. Abrir un producto.
2. Pulsar **mensaje**.
3. El sistema identifica al usuario propietario del producto.
4. Si ya existe un chat con ese usuario, se reutiliza la conversación existente.
5. Si no existe, el sistema crea un nuevo chat.
6. El sistema añade un primer mensaje automático de bienvenida.
7. Se abre **Chat con [usuario]**.
8. El usuario puede continuar escribiendo mensajes.

### ELEMENTOS DE LA PANTALLA

* **mensaje:** Inicia o abre la conversación con el vendedor.
* **Chat con [usuario]:** Encabezado de la conversación.
* **Campo de mensaje:** Permite introducir el texto.
* **Botón de envío:** Envía el mensaje.

### CASOS ESPECIALES Y VARIACIONES

* Si ya existe un chat con el destinatario, no se crea una segunda conversación.
* El nuevo chat recibe inicialmente el mensaje automático **bienvenido a este nuevo chat**.

### ERRORES Y SOLUCIONES

* **Error:** "Error al crear el chat"

  * **Significado:** No se pudo crear la conversación.
  * **Acción requerida:** Comprobar la conexión y volver a intentarlo.

### RESULTADO ESPERADO

El usuario queda dentro de una conversación con el destinatario seleccionado.

---