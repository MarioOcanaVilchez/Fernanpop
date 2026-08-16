<%@ page import="Controller.GestionAPP" %><%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 10/08/2026
  Time: 17:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    GestionAPP gestionAPP = (GestionAPP) session.getAttribute("controller");
    long idChat = (long) session.getAttribute("idChat");
    String mensaje = request.getParameter("mensaje");
    if (!gestionAPP.otroUserBloqueoAUser(gestionAPP.getChat(idChat).getOtroUsuario().getId()) && !gestionAPP.userBloqueado(gestionAPP.getChat(idChat).getOtroUsuario().getId())) {
        if (gestionAPP.enviarMensaje(idChat, mensaje)) response.sendRedirect("UsaChat.jsp");
        else {
            session.setAttribute("error", "Error al enviar el mensaje");
            session.setAttribute("recomendacion", "Compruebe la conexión");
            response.sendRedirect("Error.jsp");
        }
    } else {
        if (gestionAPP.enviarMensajeDeBloqueado(idChat,mensaje)) response.sendRedirect("UsaChat.jsp");
        else {
            session.setAttribute("error", "Error al enviar el mensaje");
            session.setAttribute("recomendacion", "Compruebe la conexión");
            response.sendRedirect("Error.jsp");
        }
    }
%>
