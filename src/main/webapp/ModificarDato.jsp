<%@ page import="Controller.GestionAPP" %><%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 30/07/2026
  Time: 17:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    GestionAPP gestionAPP = (GestionAPP) session.getAttribute("controller");
    String campo = request.getParameter("campo");
    String valor = request.getParameter("valor");
    switch (campo){
        case "movil":
            int movil = Integer.parseInt(valor);
            gestionAPP.cambiaTelefono(movil);
            break;
        case "nombre":
            gestionAPP.cambiaNombre(valor);
            break;
        case "apel":
            gestionAPP.cambiaApel(valor);
            break;
        case "email":
            if (gestionAPP.buscaMail(valor) == null){
                gestionAPP.cambiaEmail(valor);
                response.sendRedirect("Perfil.jsp");
            }
            else {
                session.setAttribute("error","Error al actualizar el correo");
                session.setAttribute("recomendacion","Correo en uso por otro usuario");
                response.sendRedirect("Error.jsp");
            }
            break;
    }
    session.setAttribute("controller",gestionAPP);
    if (!campo.equals("email")) response.sendRedirect("Perfil.jsp");
%>
