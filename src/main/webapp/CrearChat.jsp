<%@ page import="Controller.GestionAPP" %><%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 08/08/2026
  Time: 20:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    GestionAPP gestionAPP = (GestionAPP) session.getAttribute("controller");
    if (gestionAPP.getUsuario() == null) response.sendRedirect("InicioSesion.jsp");
    else{
        int idUser = Integer.parseInt(request.getParameter("idUser"));
        long idChat = gestionAPP.buscaChat(idUser);
        if (idChat != -1) response.sendRedirect("UsaChat.jsp?idChat=" + idChat);
        else {
            if (gestionAPP.creaChat(idUser)) response.sendRedirect("UsaChat.jsp?idChat=" + gestionAPP.buscaChat(idUser));
            else {
                session.setAttribute("error","Error al crear el chat");
                session.setAttribute("recomendacion","Compruebe la conexión");
                response.sendRedirect("Error.jsp");
            }
        }
    }
%>
