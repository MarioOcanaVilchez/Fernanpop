## FUNCIONALIDAD: BUSCAR Y FILTRAR PRODUCTOS

### OBJETIVO

Permite localizar productos publicados mediante texto, ordenación y rango de precios.

### REQUISITOS PREVIOS

* Ninguno para consultar productos.
* Para determinadas operaciones sobre un producto, el usuario debe estar autenticado.

### CÓMO BUSCAR Y FILTRAR PRODUCTOS (PASO A PASO)

1. Entrar en la **Pantalla principal**.
2. Utilizar el campo **Buscar...**.
3. Introducir el texto que se desea buscar, si se necesita una búsqueda por título.
4. Seleccionar una opción de ordenación: **aleatorio**, **precio de menor a mayor** o **precio de mayor a menor**.
5. Ajustar el precio mínimo y máximo mediante los controles de rango.
6. Pulsar **Buscar**.
7. El sistema aplica los filtros seleccionados.
8. Los productos se muestran en la pantalla principal en páginas de hasta 12 productos.
9. Utilizar los controles de navegación para avanzar o retroceder entre páginas.

### ELEMENTOS DE LA PANTALLA

* **Buscar...:** Texto utilizado para filtrar productos.
* **Aleatorio:** Ordena los resultados de forma aleatoria.
* **Precio de menor a mayor:** Ordena por precio ascendente.
* **Precio de mayor a menor:** Ordena por precio descendente.
* **Precio mínimo:** Límite inferior del filtro de precio.
* **Precio máximo:** Límite superior del filtro de precio; el valor máximo de 2000 € representa el límite abierto.
* **Buscar:** Aplica los filtros.
* **Controles de paginación:** Permiten cambiar de página.

### CASOS ESPECIALES Y VARIACIONES

* Si no se introduce texto, se muestran productos sin filtro de texto.
* Si no se selecciona una ordenación, se utiliza el orden aleatorio.
* Un usuario autenticado no ve sus propios productos como productos comprables.
* Los productos con una venta pendiente se excluyen de los resultados disponibles para compra.
* La página contiene hasta 12 productos.
* El botón **Voy a tener suerte** permite acceder a un producto aleatorio.

### ERRORES Y SOLUCIONES

* **Error:** "Error desconocido"

  * **Significado:** Se produjo una condición no contemplada durante el procesamiento.
  * **Acción requerida:** Repetir la búsqueda. Si persiste, contactar con el servicio técnico.
* **Error:** "Compruebe la conexión"

  * **Significado:** La operación no pudo completarse correctamente.
  * **Acción requerida:** Comprobar la conexión y repetir la operación.

### RESULTADO ESPERADO

La pantalla principal muestra los productos que cumplen los filtros indicados y permite navegar por sus páginas.

---