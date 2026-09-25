## FUNCIONALIDAD: GESTIONAR EL PERFIL Y DATOS PERSONALES

### OBJETIVO

Permite consultar la información de la cuenta, acceder a productos y operaciones y modificar datos personales.

### REQUISITOS PREVIOS

* Estar autenticado.

### CÓMO GESTIONAR EL PERFIL Y DATOS PERSONALES (PASO A PASO)

1. Pulsar **Perfil**.
2. Se abre la pantalla **Perfil**.
3. Revisar nombre, apellidos, email y teléfono.
4. Para modificar un dato, editar el campo correspondiente.
5. Pulsar **Guardar cambios** junto al campo modificado.
6. El sistema guarda el nuevo valor.
7. Para consultar productos propios, pulsar la tarjeta **Productos**.
8. Para consultar compras, pulsar la tarjeta **Compras**.
9. La tarjeta de **Valoración** muestra la valoración media de las ventas cuando existe.
10. Para cambiar la contraseña, pulsar **Cambiar contraseña**.
11. Para cerrar la sesión, pulsar **Cerrar sesión**.
12. Para eliminar la cuenta, pulsar **Eliminar cuenta**.

### ELEMENTOS DE LA PANTALLA

* **Nombre:** Nombre actual del usuario y campo editable.
* **Apellidos:** Apellidos actuales y campo editable.
* **Email:** Correo actual y campo editable.
* **Teléfono:** Teléfono actual y campo editable.
* **Guardar cambios:** Guarda el dato correspondiente.
* **Productos:** Abre **Mis Productos** y muestra el número de productos propios.
* **Compras:** Abre el historial de compras y muestra su cantidad.
* **Valoración:** Muestra la media de las valoraciones recibidas por ventas; si no existe, muestra **N/A**.
* **Cambiar contraseña:** Abre el proceso de actualización de contraseña.
* **Cerrar sesión:** Finaliza la sesión.
* **Eliminar cuenta:** Inicia la eliminación de la cuenta.

### CASOS ESPECIALES Y VARIACIONES

* Cada dato tiene su propio botón **Guardar cambios**.
* El email solo se actualiza si no está siendo utilizado por otro usuario.
* Al modificar el nombre, se actualiza también la información de usuario utilizada por la aplicación.
* El teléfono admite valores numéricos.
* La valoración media puede aparecer como **N/A** cuando no hay valoraciones.

### ERRORES Y SOLUCIONES

* **Error:** "Error al actualizar el correo"

  * **Significado:** El correo nuevo ya está siendo utilizado por otro usuario.
  * **Acción requerida:** Introducir un correo que no esté asociado a otra cuenta.
* **Error:** "Correo en uso por otro usuario"

  * **Significado:** No se puede duplicar un correo existente.
  * **Acción requerida:** Utilizar otra dirección de correo.
* **Error:** "Error al actualizar la contraseña"

  * **Significado:** No se pudo guardar la nueva contraseña.
  * **Acción requerida:** Comprobar la conexión y repetir el proceso.

### RESULTADO ESPERADO

Los datos personales modificados quedan guardados y el perfil refleja la información actualizada.

---