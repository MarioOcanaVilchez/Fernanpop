## FUNCIONALIDAD: ELIMINAR UNA CUENTA

### OBJETIVO

Permite eliminar la cuenta del usuario junto con sus productos y tratos pendientes.

### REQUISITOS PREVIOS

* Estar autenticado.
* Tener acceso a **Perfil**.

### CÓMO ELIMINAR UNA CUENTA (PASO A PASO)

1. Entrar en **Perfil**.
2. Pulsar **Eliminar cuenta**.
3. El sistema inicia la eliminación de los productos del usuario.
4. El sistema elimina los tratos pendientes de compra y venta.
5. El sistema elimina la cuenta.
6. El usuario autenticado se establece como inexistente.
7. El usuario es redirigido a la **Pantalla principal**.

### ELEMENTOS DE LA PANTALLA

* **Eliminar cuenta:** Ejecuta la eliminación de la cuenta.
* **Pantalla principal:** Destino después de una eliminación correcta.

### CASOS ESPECIALES Y VARIACIONES

* Antes de eliminar la cuenta se intentan eliminar todos los productos propios.
* También se eliminan los tratos pendientes asociados al usuario.
* Una cuenta eliminada puede recuperarse posteriormente mediante la verificación del correo durante el proceso de cambio de contraseña.

### ERRORES Y SOLUCIONES

* **Error:** "Error al eliminar la cuenta"

  * **Significado:** No se pudo completar alguno de los pasos de eliminación.
  * **Acción requerida:** Comprobar la conexión y repetir la operación.
* **Error:** "Compruebe la conexión"

  * **Significado:** El sistema no pudo completar la operación contra la persistencia de datos.
  * **Acción requerida:** Comprobar la conexión y volver a intentarlo.

### RESULTADO ESPERADO

La cuenta queda eliminada, el usuario deja de estar autenticado y la aplicación vuelve a la pantalla principal.

---