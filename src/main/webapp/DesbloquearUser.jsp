<%@ page import="Controller.GestionAPP" %><%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 12/08/2026
  Time: 18:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    GestionAPP gestionAPP = (GestionAPP) session.getAttribute("controller");
    int idOtroUser = Integer.parseInt(request.getParameter("idUser"));
    if (gestionAPP.desbloquearUser(idOtroUser)) response.sendRedirect("SeleccionChats.jsp");
    else {
        session.setAttribute("error","Error al bloquear al usuario");
        session.setAttribute("recomendacion","Compruebe la conexión");
        response.sendRedirect("Error.jsp");
    }
%>
