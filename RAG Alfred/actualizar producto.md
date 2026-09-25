## FUNCIONALIDAD: ACTUALIZAR UN PRODUCTO

### OBJETIVO

Permite modificar los datos de un producto que pertenece al usuario.

### REQUISITOS PREVIOS

* Estar autenticado.
* Tener al menos un producto propio.
* El producto debe estar accesible desde **Mis Productos**.

### CÓMO ACTUALIZAR UN PRODUCTO (PASO A PASO)

1. Entrar en **Perfil**.
2. Pulsar **Productos** para abrir **Mis Productos**.
3. Localizar el producto que se desea modificar.
4. Abrir el menú de tres puntos del producto.
5. Pulsar **Actualizar**.
6. Se abre el formulario de edición con los datos actuales.
7. Modificar título, descripción, precio o estado.
8. Seleccionar una nueva imagen si se desea cambiarla.
9. Pulsar **Actualizar producto**.
10. Se muestra una pantalla de espera con el mensaje **Actualizando producto**.
11. El sistema guarda los cambios.
12. Se muestra **Producto actualizado**.

### ELEMENTOS DE LA PANTALLA

* **Imagen:** Permite seleccionar una imagen para el producto.
* **Nombre:** Título editable.
* **Descripción:** Descripción editable; requiere al menos 20 caracteres.
* **Precio:** Precio editable, igual o superior a 0.
* **Estado:** Estado actual y opciones disponibles.
* **Actualizar producto:** Guarda los cambios.

### CASOS ESPECIALES Y VARIACIONES

* La imagen puede mantenerse sin cambios.
* Si se selecciona una imagen nueva, esta se asocia al producto actualizado.
* El menú de producto también contiene la acción **Eliminar**.

### ERRORES Y SOLUCIONES

* **Error:** "Error al actualizar el producto"

  * **Significado:** No se pudieron guardar los cambios.
  * **Acción requerida:** Comprobar la conexión y repetir la operación.
* **Error:** Validación por descripción demasiado corta.

  * **Significado:** La descripción contiene menos de 20 caracteres.
  * **Acción requerida:** Introducir al menos 20 caracteres.

### RESULTADO ESPERADO

El producto muestra la información actualizada en **Mis Productos** y en los listados donde corresponda.

---