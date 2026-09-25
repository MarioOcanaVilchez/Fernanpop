## FUNCIONALIDAD: REGISTRAR UNA CUENTA

### OBJETIVO

Permite crear una nueva cuenta de usuario en FernanPop mediante verificación del correo electrónico.

### REQUISITOS PREVIOS

* Tener acceso al correo electrónico que se desea registrar.
* No tener una cuenta activa con ese correo.

### CÓMO REGISTRAR UNA CUENTA (PASO A PASO)

1. Entrar en **Inicia sesión**.
2. Pulsar **Registrarse**.
3. Introducir el correo electrónico.
4. Pulsar **Registrar**.
5. El sistema envía un correo de verificación con un código numérico de 6 cifras.
6. Introducir el código recibido en **Validar**.
7. Si el código es correcto, se abre el formulario **Registrarse** para completar los datos.
8. Introducir la contraseña, nombre, apellidos y, opcionalmente, teléfono.
9. Pulsar **Crear cuenta**.
10. Se muestra una pantalla de espera con el mensaje **Creando cuenta**.
11. El sistema crea la cuenta.
12. El usuario queda autenticado y es redirigido a la pantalla principal.

### ELEMENTOS DE LA PANTALLA

* **Email:** Dirección de correo que se utilizará para la cuenta.
* **Número de verificación:** Código de 6 cifras enviado por correo.
* **Contraseña:** Debe contener al menos 4 caracteres.
* **Nombre:** Nombre del usuario.
* **Apellidos:** Apellidos del usuario.
* **Teléfono:** Número opcional de 9 cifras.
* **Registrar:** Solicita el envío del código de verificación.
* **Validar:** Comprueba el código recibido.
* **Crear cuenta:** Crea la cuenta con los datos introducidos.

### CASOS ESPECIALES Y VARIACIONES

* Si el correo ya corresponde a un usuario activo, no se permite crear otra cuenta con ese correo.
* Si el correo corresponde a un usuario previamente borrado, la validación puede conducir al proceso de recuperación de la cuenta en lugar de crear una cuenta nueva.
* El código de verificación permite hasta 3 oportunidades. Al agotarlas, el proceso termina.
* El código tiene 6 cifras.

### ERRORES Y SOLUCIONES

* **Error:** "Usuario ya registrado"

  * **Significado:** El correo introducido ya pertenece a una cuenta activa.
  * **Acción requerida:** Utilizar otro correo o iniciar sesión con la cuenta existente.
* **Error:** "Numero incorrecto te quedan [N] oportunidades"

  * **Significado:** El código introducido no coincide con el código enviado.
  * **Acción requerida:** Revisar el correo recibido e introducir el código correcto.
* **Error:** "Oportunidades agotadas"

  * **Significado:** Se han realizado tres intentos incorrectos.
  * **Acción requerida:** Utilizar otra cuenta y revisar el correo de verificación.
* **Error:** "Error al enviar el correo"

  * **Significado:** El sistema no pudo enviar el correo de verificación.
  * **Acción requerida:** Comprobar la conexión y repetir el proceso.

### RESULTADO ESPERADO

La cuenta se crea correctamente, el usuario queda autenticado y se muestra la pantalla principal.

---