## FUNCIONALIDAD: RECUPERAR O CAMBIAR LA CONTRASEÑA

### OBJETIVO

Permite recuperar el acceso a una cuenta mediante la verificación del correo y establecer una nueva contraseña.

### REQUISITOS PREVIOS

* Tener acceso al correo electrónico de la cuenta.
* La cuenta debe existir o haber sido eliminada previamente.

### CÓMO RECUPERAR O CAMBIAR LA CONTRASEÑA (PASO A PASO)

1. Entrar en **Inicia sesión**.
2. Pulsar **Cambiar contraseña**.
3. Introducir el correo electrónico.
4. Pulsar **Validar**.
5. El sistema envía un código de verificación de 6 cifras al correo.
6. Introducir el código recibido.
7. Pulsar **Validar**.
8. Si la cuenta estaba eliminada, el sistema la recupera antes de continuar.
9. Se abre la pantalla **Cambiar contraseña**.
10. Introducir la nueva contraseña.
11. Volver a introducirla en el campo de comprobación.
12. Cuando ambas contraseñas coinciden, se habilita **Actualizar**.
13. Pulsar **Actualizar**.
14. El sistema guarda la nueva contraseña.
15. Se muestra el mensaje **Contraseña actualizada con éxito**.

### ELEMENTOS DE LA PANTALLA

* **Email:** Correo de la cuenta cuya contraseña se quiere actualizar.
* **Número de verificación:** Código de 6 cifras enviado por correo.
* **Contraseña:** Nueva contraseña.
* **Vuelve a escribir la contraseña:** Confirmación de la nueva contraseña.
* **Actualizar:** Guarda la nueva contraseña cuando las dos entradas coinciden.

### CASOS ESPECIALES Y VARIACIONES

* Una cuenta eliminada puede recuperarse durante la verificación del correo.
* El botón **Actualizar** permanece deshabilitado mientras las contraseñas no coincidan.
* El código de verificación dispone de 3 oportunidades.

### ERRORES Y SOLUCIONES

* **Error:** "Numero incorrecto te quedan [N] oportunidades"

  * **Significado:** El código introducido no es correcto.
  * **Acción requerida:** Revisar el correo y volver a introducir el código.
* **Error:** "Oportunidades agotadas"

  * **Significado:** Se agotaron los tres intentos de validación.
  * **Acción requerida:** Reiniciar el proceso y revisar el correo.
* **Error:** "Error al recuperar el usuario"

  * **Significado:** El sistema no pudo reactivar la cuenta eliminada.
  * **Acción requerida:** Comprobar la conexión y repetir el proceso.
* **Error:** "Error al actualizar la contraseña"

  * **Significado:** No se pudo guardar la nueva contraseña.
  * **Acción requerida:** Comprobar la conexión y volver a intentarlo.

### RESULTADO ESPERADO

La contraseña queda actualizada y el usuario puede utilizarla para iniciar sesión.

---