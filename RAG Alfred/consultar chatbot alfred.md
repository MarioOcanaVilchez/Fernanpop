## FUNCIONALIDAD: CONSULTAR EL CHATBOT ALFRED

### OBJETIVO

Permite realizar consultas en lenguaje natural al chatbot **Alfred** y recibir una respuesta.

### REQUISITOS PREVIOS

* Estar autenticado.
* Tener acceso a **ChatBot**.

### CÓMO CONSULTAR AL CHATBOT (PASO A PASO)

1. Pulsar **ChatBot** en el menú inferior.
2. Se abre la pantalla del chatbot **Alfred**.
3. Escribir la consulta en el campo de texto.
4. Pulsar el botón de envío.
5. El sistema envía la petición al servicio de inteligencia artificial.
6. Se obtiene una respuesta.
7. La conversación muestra el mensaje del usuario y la respuesta de Alfred.
8. Si la respuesta representa una consulta de productos, el sistema puede interpretar la respuesta como una petición de búsqueda.
9. En ese caso, se carga la pantalla principal con los productos obtenidos de la consulta.
10. Si la respuesta es conversacional, se mantiene la conversación en **ChatBot**.

### ELEMENTOS DE LA PANTALLA

* **Alfred:** Identificación del chatbot.
* **Campo de consulta:** Texto de la pregunta o petición.
* **Botón de envío:** Envía la consulta.
* **Mensaje propio:** Muestra la petición introducida.
* **Mensaje de Alfred:** Muestra la respuesta generada.

### CASOS ESPECIALES Y VARIACIONES

* Las respuestas pueden clasificarse internamente como conversación o como consulta de productos.
* Una consulta de productos puede devolver resultados en la pantalla principal y utilizar paginación.
* La sesión del chatbot conserva los mensajes durante la sesión de aplicación.
* Si el servicio de IA no está disponible, se devuelve un mensaje indicando que el servicio está caído.

### ERRORES Y SOLUCIONES

* **Error:** "Lo lamento señor pero el servicio está caído, contacte con el administrador"

  * **Significado:** El servicio externo de inteligencia artificial no respondió correctamente o se produjo una excepción.
  * **Acción requerida:** Intentar la consulta de nuevo. Si el problema persiste, contactar con el administrador.
* **Error:** "Error desconocido"

  * **Significado:** Se produjo una condición no contemplada en el procesamiento.
  * **Acción requerida:** Reintentar y contactar con el servicio técnico si persiste.

### RESULTADO ESPERADO

El usuario recibe una respuesta de Alfred o, si la petición corresponde a productos, obtiene un listado filtrado de productos relacionado con su consulta.

---