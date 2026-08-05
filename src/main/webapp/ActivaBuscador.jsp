<%@ page import="Controller.GestionAPP" %><%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 17/07/2026
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  GestionAPP gestionAPP = (GestionAPP) session.getAttribute("controller");
  String textoBuscar = request.getParameter("texto");
  String orden = request.getParameter("orden");
  int precioMin = Integer.parseInt(request.getParameter("precioMin"));
  int precioMax = Integer.parseInt(request.getParameter("precioMax"));
  if (textoBuscar == null || textoBuscar.isEmpty()) session.setAttribute("textoBuscar", null);
  else session.setAttribute("textoBuscar", textoBuscar);
  if (orden == null || orden.isEmpty()) session.setAttribute("orden", "aleatorio");
  else session.setAttribute("orden", orden);
  session.setAttribute("precioMin",precioMin);
  session.setAttribute("precioMax",precioMax);
  int numProductos = gestionAPP.getTotalProductos((String) session.getAttribute("textoBuscar"),precioMin,precioMax);
  session.setAttribute("pagina",1);
  session.setAttribute("numPaginas",(numProductos % 12 != 0? numProductos / 12 + 1 : numProductos / 12));
  session.setAttribute("productos", gestionAPP.getPaginaProductos(null, (String) session.getAttribute("textoBuscar"), (String) session.getAttribute("orden"), precioMin, precioMax));
  response.sendRedirect("index.jsp");
%>
