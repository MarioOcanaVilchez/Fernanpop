<%@ page import="Controller.GestionAPP" %><%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 30/07/2026
  Time: 17:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<% GestionAPP gestionAPP = (GestionAPP) session.getAttribute("controller");
gestionAPP.setUsuario(null);
session.setAttribute("controller",gestionAPP);
response.sendRedirect("BorraVariablesBuscar.jsp");
%>
</body>
</html>
