<%@ page import="Controller.GestionAPP" %><%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 16/08/2026
  Time: 21:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  GestionAPP gestionAPP = (GestionAPP) session.getAttribute("controller");
  long idChat = Long.parseLong(request.getParameter("idChat"));
  if (gestionAPP.marcarMensajesLeidos(idChat)) response.sendRedirect("SeleccionChats.jsp");
  else {
    session.setAttribute("error","Error al leer los mensajes");
    session.setAttribute("recomendacion","Compruebe la conexión");
    response.sendRedirect("Error.jsp");
  }
%>
