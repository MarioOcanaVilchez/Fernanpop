<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Inicia sesión</title>
    <link rel="stylesheet" type="text/css" href="CSS/inicioSesion.css?v=2">
    <link rel="icon" type="image/png" href="imagenes/logo%20fernanpop.png">
</head>
<body>
<% session.setAttribute("accion", "iniciarSesion");
session.setAttribute("productos",null);
session.setAttribute("numPaginas",null);
session.setAttribute("pagina",null);
session.setAttribute("orden",null);
session.setAttribute("textoBuscar",null);
session.setAttribute("paginaAnterior","InicioSesion.jsp");
%>
<form action="pantallaEspera.jsp" method="get">
    <p><input type="text" name="email" required placeholder="email"></p>
    <p><input type="password" name="clave" required placeholder="contraseña"></p>
    <p class="error"><% if (session.getAttribute("errorSesion") != null) out.print((String) session.getAttribute("errorSesion")); %></p>
    <p><input type="submit" value="Iniciar sesión"></p>
    <p>¿Aún no tienes cuenta? <a href="registrarEmail.jsp">Registrarse</a></p>
    <p>¿Has olvidado la contraseña? <a href="registraEmailActualizaContrasenia.jsp">Cambiar contraseña</a></p>
</form>
</body>
</html>