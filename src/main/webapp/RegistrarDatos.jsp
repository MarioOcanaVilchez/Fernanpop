<%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 08/07/2026
  Time: 17:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registrarse</title>
    <link rel="stylesheet" type="text/css" href="CSS/RegistrarDatos.css">
    <link rel="icon" type="image/png" href="imagenes/logo%20fernanpop.png">
</head>
<body>
<% session.setAttribute("oportunidades",null);
session.setAttribute("accion","crearCuenta"); %>
<form action="pantallaEspera.jsp" method="get">
    <input type="password" minlength="4" required name="clave" placeholder="contraseña">
    <input type="text" required name="nombre" placeholder="nombre">
    <input type="text" required name="apel" placeholder="apellidos">
    <input type="number" min="100000000" max="999999999" name="telefono" placeholder="teléfono">
    <input type="submit" value="Crear cuenta">
</form>
</body>
</html>
