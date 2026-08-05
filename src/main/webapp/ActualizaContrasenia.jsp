<%@ page import="Controller.GestionAPP" %><%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 31/07/2026
  Time: 17:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    GestionAPP gestionAPP = (GestionAPP) session.getAttribute("controller");
    String clave = request.getParameter("clave");
    if (gestionAPP.cambiaClave(clave)) {
        session.setAttribute("controller", gestionAPP);
        //Acierto
        session.setAttribute("acierto","Contraseña actualizada con éxito");
        response.sendRedirect("Acierto.jsp");
    } else {
        session.setAttribute("error","Error al actualizar la contraseña");
        session.setAttribute("recomendacion","Compruebe la conexión");
        response.sendRedirect("Error.jsp");
    }
%>
