## FUNCIONALIDAD: CONSULTAR PRODUCTOS MEDIANTE EL CHATBOT

### OBJETIVO

Permite utilizar una petición realizada al chatbot para obtener productos relacionados con la consulta.

### REQUISITOS PREVIOS

* Estar autenticado.
* Haber realizado una consulta al chatbot que el sistema interprete como búsqueda de productos.

### CÓMO CONSULTAR PRODUCTOS MEDIANTE EL CHATBOT (PASO A PASO)

1. Entrar en **ChatBot**.
2. Escribir una petición relacionada con los productos que se desean encontrar.
3. Pulsar el botón de envío.
4. Esperar la respuesta de Alfred.
5. El sistema identifica una respuesta de tipo consulta de productos.
6. Se calcula el número de productos que cumplen la consulta.
7. Se cargan hasta 12 productos inicialmente.
8. El sistema redirige a la **Pantalla principal**.
9. Los productos se muestran según la petición interpretada por el chatbot.
10. Utilizar la paginación para consultar más resultados.

### ELEMENTOS DE LA PANTALLA

* **Petición al chatbot:** Descripción de los productos que se buscan.
* **Listado de productos:** Resultados obtenidos a partir de la consulta.
* **Paginación:** Permite consultar más resultados.
* **Producto:** Cada resultado muestra sus datos habituales.

### CASOS ESPECIALES Y VARIACIONES

* El usuario no puede utilizar esta función para incluir sus propios productos en los resultados.
* Los productos con ventas pendientes se excluyen.
* La ordenación puede conservar la ordenación solicitada por la consulta.
* Si no hay suficientes productos para completar una página, se muestran los disponibles.

### ERRORES Y SOLUCIONES

* **Error:** "Lo lamento señor pero el servicio está caído, contacte con el administrador"

  * **Significado:** El servicio de IA no está disponible.
  * **Acción requerida:** Reintentar más tarde o contactar con el administrador.

### RESULTADO ESPERADO

La pantalla principal muestra los productos que corresponden a la petición interpretada por Alfred.

---