## FUNCIONALIDAD: CERRAR SESIÓN

### OBJETIVO

Permite finalizar la sesión del usuario actual y volver a la navegación sin autenticación.

### REQUISITOS PREVIOS

* Tener una sesión iniciada.

### CÓMO CERRAR SESIÓN (PASO A PASO)

1. Entrar en **Perfil**.
2. Pulsar **Cerrar sesión**.
3. El sistema elimina el usuario autenticado de la sesión de aplicación.
4. Se conservan únicamente los datos de navegación que correspondan.
5. El usuario es redirigido a la **Pantalla principal**.
6. Las funcionalidades que requieren autenticación vuelven a solicitar el inicio de sesión.

### ELEMENTOS DE LA PANTALLA

* **Cerrar sesión:** Finaliza la sesión del usuario.
* **Pantalla principal:** Destino después de cerrar la sesión.

### CASOS ESPECIALES Y VARIACIONES

* Cerrar sesión no elimina la cuenta ni los productos.
* Los productos públicos pueden seguir consultándose sin autenticación.

### ERRORES Y SOLUCIONES

* **Error:** "Error"

  * **Significado:** No se ha definido un error específico para el cierre de sesión.
  * **Acción requerida:** Volver a la pantalla principal e iniciar sesión de nuevo si fuera necesario.

### RESULTADO ESPERADO

La cuenta deja de estar autenticada y el usuario queda en la pantalla principal.

---