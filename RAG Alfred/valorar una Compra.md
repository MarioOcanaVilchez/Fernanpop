## FUNCIONALIDAD: VALORAR UNA COMPRA

### OBJETIVO

Permite valorar una operación de compra mediante una puntuación de 1 a 5 estrellas y un comentario opcional.

### REQUISITOS PREVIOS

* Estar autenticado.
* Tener una compra realizada pendiente de valoración.

### CÓMO VALORAR UNA COMPRA (PASO A PASO)

1. Entrar en **Tratos**.
2. Seleccionar **Compras**.
3. Localizar una compra que aparezca como **No puntuado**.
4. Pulsar **Puntuar**.
5. Se abre la pantalla de valoración.
6. Seleccionar una de las cinco estrellas.
7. Introducir un comentario si se desea.
8. Pulsar **valorar**.
9. El sistema registra la puntuación y el comentario.
10. La operación pasa a estado valorado.
11. Volver a **Compras** para comprobar la nueva valoración.

### ELEMENTOS DE LA PANTALLA

* **Estrellas:** Cinco estrellas que representan una puntuación de 1 a 5.
* **Puntuación:** Valor numérico seleccionado mediante las estrellas.
* **Comentario:** Texto opcional asociado a la valoración.
* **valorar:** Guarda la valoración.

### CASOS ESPECIALES Y VARIACIONES

* Al pulsar la misma estrella seleccionada como última estrella, la puntuación vuelve a 0.
* La valoración se aplica a un trato concreto.
* La puntuación queda asociada al historial de la operación.

### ERRORES Y SOLUCIONES

* **Error:** "Error" o fallo al guardar la valoración.

  * **Significado:** La operación no pudo actualizar el trato.
  * **Acción requerida:** Volver a **Compras** y repetir la valoración.

### RESULTADO ESPERADO

La compra muestra la puntuación seleccionada y el comentario introducido, si existe.

---