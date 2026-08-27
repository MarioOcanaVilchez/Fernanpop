<%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 07/07/2026
  Time: 20:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <% session.setAttribute("paginaAnterior","registrarEmail.jsp"); %>
    <title>Registrarse</title>
    <link rel="stylesheet" type="text/css" href="CSS/RegistrarEmail.css">
    <link rel="icon" type="image/png" href="imagenes/logo%20fernanpop.png">
    <% session.setAttribute("accion","enviarCorreoVerificacion");
    session.setAttribute("oportunidades",null);%>
</head>
<body>
    <form method="get" action="ValidarEmail.jsp">
        <p><input type="text" required name="email" placeholder="email"></p>
        <p><input type="submit" value="Registrar"></p>
        <p>¿Ya tienes cuenta? <a href="InicioSesion.jsp">Inicia sesión</a></p>
    </form>
</body>
</html>
