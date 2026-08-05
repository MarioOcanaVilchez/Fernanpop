<%@ page import="Controller.GestionAPP" %><%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 30/07/2026
  Time: 17:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    GestionAPP gestionAPP = (GestionAPP) session.getAttribute("controller");
//codigo para borrar user quitar despues y poner en el botón correspondiente
    if (gestionAPP.getUsuario() == null) response.sendRedirect("InicioSesion.jsp");
    else response.sendRedirect("Perfil.jsp");
%>
