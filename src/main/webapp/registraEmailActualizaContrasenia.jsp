<%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 27/08/2026
  Time: 20:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Actualizar contraseña</title>
    <link rel="stylesheet" type="text/css" href="CSS/registraEmailActualizaContrasenia.css">
    <link rel="icon" type="image/png" href="imagenes/logo%20fernanpop.png">
    <% session.setAttribute("accion","enviarCorreoVerificacion");
        session.setAttribute("oportunidades",null);%>
</head>
<body>
<form method="get" action="ValidarEmail.jsp">
    <p><input type="text" required name="email" placeholder="email"></p>
    <p><input type="submit" value="Validar"></p>
</form>
</body>
</html>
