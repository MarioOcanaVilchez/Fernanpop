<%@ page import="Controller.GestionAPP" %><%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 12/07/2026
  Time: 17:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="icon" type="image/png" href="imagenes/logo%20fernanpop.png">
</head>
<body>
<% GestionAPP gestionAPP = (GestionAPP) session.getAttribute("controller");
    if (gestionAPP.getUsuario() == null) response.sendRedirect("InicioSesion.jsp");
%>
</body>
</html>
