<%@ page import="Controller.GestionAPP" %>
<%@ page import="Modelos.Trato" %><%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 23/07/2026
  Time: 20:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    GestionAPP gestionAPP = (GestionAPP) session.getAttribute("controller");
    int idTrato = Integer.parseInt((String) session.getAttribute("idTrato"));
    Trato trato = gestionAPP.buscarTratoId(idTrato);
    String comentario = request.getParameter("comentario");
    int puntuacion = Integer.parseInt(request.getParameter("puntuacion"));
    if (puntuacion == 0) puntuacion = -1;
    gestionAPP.actualizaTrato(trato,comentario,puntuacion);
    response.sendRedirect("Compras.jsp");
%>
