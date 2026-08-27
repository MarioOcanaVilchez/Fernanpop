<%@ page import="Controller.GestionAPP" %>
<%@ page import="Modelos.Producto" %><%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 03/08/2026
  Time: 20:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    GestionAPP gestionAPP = (GestionAPP) session.getAttribute("controller");
    long idProducto = Long.parseLong(request.getParameter("id"));
    Producto producto = gestionAPP.buscarProductoId(idProducto);
    if (gestionAPP.eliminarProducto(producto)){
        response.sendRedirect("MisProductos.jsp");
    }
    else {
        session.setAttribute("error","Error al borrar el producto");
        session.setAttribute("recomendacion","Compruebe la conexión");
        response.sendRedirect("Error.jsp");
    }
%>
