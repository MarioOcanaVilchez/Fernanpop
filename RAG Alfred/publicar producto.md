## FUNCIONALIDAD: PUBLICAR UN PRODUCTO

### OBJETIVO

Permite al usuario autenticado poner un producto a la venta con información, precio, estado e imagen opcional.

### REQUISITOS PREVIOS

* Estar autenticado.
* Tener preparada la información del producto.
* La descripción debe tener al menos 20 caracteres.

### CÓMO PUBLICAR UN PRODUCTO (PASO A PASO)

1. Entrar en la aplicación con una sesión iniciada.
2. Pulsar la opción de crear/publicar producto del menú inferior.
3. Se abre **Crear producto**.
4. Seleccionar una imagen si se desea añadirla.
5. Introducir el nombre del producto.
6. Introducir una descripción de al menos 20 caracteres.
7. Introducir el precio.
8. Seleccionar el estado del producto.
9. Pulsar **Vender producto**.
10. Se muestra una pantalla de espera con el mensaje **Creando producto**.
11. El sistema registra el producto.
12. Si se añadió una imagen, se asocia al producto.
13. Se muestra **Producto creado**.

### ELEMENTOS DE LA PANTALLA

* **Imagen:** Archivo de imagen opcional del producto.
* **Nombre:** Título del producto.
* **Descripción:** Descripción del producto; requiere un mínimo de 20 caracteres.
* **Precio:** Importe de venta; admite decimales y debe ser igual o superior a 0.
* **Estado:** Puede ser **No determinado**, **Como nuevo**, **Poco usado**, **Bien cuidado**, **Usado** o **Deteriorado**.
* **Vender producto:** Publica el producto.

### CASOS ESPECIALES Y VARIACIONES

* La imagen es opcional.
* Si no se proporciona imagen, se utiliza una imagen predeterminada en los listados.
* Al publicar un producto se envía una comunicación de confirmación al correo del usuario.

### ERRORES Y SOLUCIONES

* **Error:** "Error al crear el producto"

  * **Significado:** No se pudo registrar el producto.
  * **Acción requerida:** Comprobar la conexión y repetir la publicación.
* **Error:** Validación del formulario por descripción demasiado corta.

  * **Significado:** La descripción tiene menos de 20 caracteres.
  * **Acción requerida:** Introducir una descripción de al menos 20 caracteres.

### RESULTADO ESPERADO

El producto queda publicado y aparece en los productos del usuario y, si está disponible, en el catálogo de productos.

---