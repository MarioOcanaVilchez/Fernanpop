## FUNCIONALIDAD: NAVEGAR POR EL MENÚ PRINCIPAL

### OBJETIVO

Permite acceder rápidamente a las principales áreas de FernanPop.

### REQUISITOS PREVIOS

* Ninguno para las opciones públicas.
* Algunas secciones requieren una sesión iniciada.

### CÓMO NAVEGAR POR EL MENÚ PRINCIPAL (PASO A PASO)

1. Localizar el menú inferior de la aplicación.
2. Pulsar **Inicio** para volver al catálogo principal.
3. Pulsar **Tratos** para consultar compras, ventas y solicitudes.
4. Pulsar la opción central de creación de producto para publicar un producto.
5. Pulsar **Chats** para abrir el selector de conversaciones.
6. Pulsar **ChatBot** para abrir Alfred.
7. Pulsar **Perfil** desde la cabecera para consultar la cuenta.

### ELEMENTOS DE LA PANTALLA

* **Inicio:** Abre la pantalla principal y limpia los filtros de búsqueda almacenados.
* **Tratos:** Abre la gestión de compras, ventas y solicitudes.
* **Crear producto:** Abre el formulario para publicar un producto.
* **Chats:** Abre el selector de conversaciones.
* **ChatBot:** Abre el chatbot Alfred.
* **Perfil:** Abre el perfil del usuario cuando existe una sesión.

### CASOS ESPECIALES Y VARIACIONES

* Las secciones **Tratos**, **Chats**, **ChatBot** y **Perfil** requieren autenticación.
* Al volver a **Inicio**, se eliminan los filtros y estado de paginación almacenados.
* Si una sección requiere autenticación y no existe sesión, el sistema redirige a **Inicia sesión**.

### ERRORES Y SOLUCIONES

* **Error:** Redirección a "Inicia sesión"

  * **Significado:** Se intentó acceder a una sección que requiere autenticación sin tener una sesión activa.
  * **Acción requerida:** Iniciar sesión y volver a acceder a la sección.

### RESULTADO ESPERADO

El usuario puede desplazarse entre las áreas principales de la aplicación desde el menú.

---
