<%@ page import="Controller.GestionAPP" %><%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 21/07/2026
  Time: 18:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    GestionAPP gestionAPP = (GestionAPP) session.getAttribute("controller");
    if (gestionAPP.eliminaSolicitudCompra(Integer.parseInt(request.getParameter("id")))) response.sendRedirect("SolicitudesCompra.jsp");
    else {
        session.setAttribute("error","Error al retirar la solicitud de compra");
        session.setAttribute("recomendacion","Compruebe la conexión");
        response.sendRedirect("Error.jsp");
    }
%>
