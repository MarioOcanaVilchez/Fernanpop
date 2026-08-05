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
</head>
<body>
<%
    session.setAttribute("orden",null);
    session.setAttribute("pagina",null);
    session.setAttribute("numPaginas",null);
    session.setAttribute("textoBuscar",null);
    session.setAttribute("productos",null);
    session.setAttribute("precioMin",null);
    session.setAttribute("precioMax",null);
    response.sendRedirect("index.jsp");
%>
</body>
</html>
