<%@ page import="Controller.GestionAPP" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <link rel="stylesheet" type="text/css" href="CSS/inicioSesion.css">
    <link rel="icon" type="image/png" href="imagenes/logo%20fernanpop.png">
</head>
<body>
<% session.setAttribute("accion", "iniciarSesion");
session.setAttribute("productos",null);
session.setAttribute("numPaginas",null);
session.setAttribute("pagina",null);
session.setAttribute("orden",null);
session.setAttribute("textoBuscar",null);
%>
<form action="pantallaEspera.jsp" method="get">
    <p><input type="text" name="email" required placeholder="email"></p>
    <p><input type="password" name="clave" required placeholder="contraseña"></p>
    <p class="error"><% if (session.getAttribute("errorSesion") != null) out.print((String) session.getAttribute("errorSesion")); %></p>
    <p><input type="submit" value="Iniciar sesión"></p>
    <p>¿Aún no tienes cuenta? <a href="registrarEmail.jsp">Registrarse</a></p>
</form>
</body>
</html>