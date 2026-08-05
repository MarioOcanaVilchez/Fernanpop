<%@ page import="Controller.GestionAPP" %><%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 08/07/2026
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
  <link rel="stylesheet" type="text/css" href="CSS/ValidarEmail.css">
  <link rel="icon" type="image/png" href="imagenes/logo%20fernanpop.png">
</head>
<body>
<form method="get" action="ValidarNumEntrada.jsp">
  <% GestionAPP gestionAPP = (GestionAPP) session.getAttribute("controller");
  if (session.getAttribute("oportunidades") == null) session.setAttribute("oportunidades",3);
  out.print("<p class=\"info-envio\">Enviando correo de verificación a <strong>" + session.getAttribute("email") + "</strong></p>" +
            "<p class=\"info-instruccion\">Introduce la clave proporcionada</p>");
  %>
  <input type="text" minlength="6" maxlength="6" name="numProbado" required>
  <input type="submit" value="Validar">
  <p class="error"><% if (session.getAttribute("ErrorNumEntrada") != null) out.print("Numero incorrecto te quedan " + session.getAttribute("oportunidades").toString() + " oportunidades");%></p>
</form>
</body>
</html>
