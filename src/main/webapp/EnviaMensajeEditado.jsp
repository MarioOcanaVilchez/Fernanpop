<%@ page import="Controller.GestionAPP" %><%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 17/08/2026
  Time: 17:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    GestionAPP gestionAPP = (GestionAPP) session.getAttribute("controller");
    long idChat = (long) session.getAttribute("idChat");
    long idMensaje = (long) session.getAttribute("idMensaje");
    String nuevoMensaje = request.getParameter("mensaje");
    if (gestionAPP.actualizaMensaje(idChat,idMensaje,nuevoMensaje)){
        response.sendRedirect("UsaChat.jsp");
    } else {
        session.setAttribute("error","Error al editar el mensaje");
        session.setAttribute("recomendacion","Compruebe la conexión");
        response.sendRedirect("Error");
    }
%>
