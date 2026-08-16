<%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 10/08/2026
  Time: 18:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    if (chat.getMensajes() == null || chat.getMensajes().isEmpty()){
        out.print("<p>No hay mensajes</p>");
    } else {
        for (Mensaje m : chat.getMensajes()) {
            if (m.getUsuario().getId() == -1) {
                out.print("<div class=\"mensajeAdministracion\">" +
                        "<p>" + m.getContenido() + "</p>" +
                        "</div>");
            } else if (m.getUsuario().getId() == gestionAPP.getUsuario().getId()) {
                out.print("<div class=\"mensajePropio\">" +
                        "<p>" + m.getContenido() + "</p>" +
                        "<p>" + m.getFecha() + "</p>" +
                        "</div>");
            } else {
                out.print("<div class=\"mensajeOtroUser\">" +
                        "<p>" + m.getUsuario().getEmail() + "</p>" +
                        "<p>" + m.getContenido() + "</p>" +
                        "<p>" + m.getFecha() + "</p>" +
                        "</div>");
            }
        }
    }
%>
</body>
</html>
