<%@ page import="Controller.GestionAPP" %><%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 12/08/2026
  Time: 16:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  GestionAPP gestionAPP = (GestionAPP) session.getAttribute("controller");
  long idChat = (long) session.getAttribute("idChat");
  if (gestionAPP.eliminarMensajeChat(idChat,Long.parseLong(request.getParameter("id")))) response.sendRedirect("UsaChat.jsp");
  else {
    session.setAttribute("error","Error al eliminar el mensaje");
    session.setAttribute("recomendacion","Compruebe la conexión");
    response.sendRedirect("Error.jsp");
  }
%>
