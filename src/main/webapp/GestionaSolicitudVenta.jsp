<%@ page import="Controller.GestionAPP" %>
<%@ page import="Modelos.Trato" %><%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 22/07/2026
  Time: 18:12
  To change this template use File | Settings | File Templates.
--%>
<%
    GestionAPP gestionAPP = (GestionAPP) session.getAttribute("controller");
    Trato trato = gestionAPP.buscarTratoId(Integer.parseInt(request.getParameter("id")));
    if (request.getParameter("opcion").equals("aceptar")){
        session.setAttribute("accion","vendeProducto");
        session.setAttribute("idTrato",trato.getId());
        response.sendRedirect("pantallaEspera.jsp");
    } else {
        if (gestionAPP.rechazaSolicitudVenta(trato)){
            response.sendRedirect("SolicitudesVenta.jsp");
        } else {
            //fallo
            session.setAttribute("error","Error al rechazar la solicitud de venta");
            session.setAttribute("recomendacion","Compruebe la conexión");
            response.sendRedirect("Error.jsp");
        }
    }
%>
