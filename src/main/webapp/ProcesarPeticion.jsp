<%@ page import="Controller.GestionAPP" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Modelos.Mensaje" %>
<%@ page import="Modelos.Usuario" %><%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 19/08/2026
  Time: 19:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    GestionAPP gestionAPP = (GestionAPP) session.getAttribute("controller");
    String peticion = (String) session.getAttribute("peticion");
    String respuesta = gestionAPP.consultarIA(peticion);
    ArrayList<Mensaje> mensajes = (ArrayList<Mensaje>) session.getAttribute("chatbot");
    mensajes.add(new Mensaje(mensajes.size() + 1,respuesta,new Usuario(-1),null,false));
    session.setAttribute("chatbot",mensajes);
%>
