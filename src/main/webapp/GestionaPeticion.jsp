<%@ page import="java.util.ArrayList" %>
<%@ page import="Modelos.Mensaje" %>
<%@ page import="Controller.GestionAPP" %><%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 21/08/2026
  Time: 18:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    GestionAPP gestionAPP = (GestionAPP) session.getAttribute("controller");
    ArrayList<Mensaje> mensajes = (ArrayList<Mensaje>) session.getAttribute("chatbot");
    if (mensajes.getLast().getContenido().startsWith("[SQL]")){
        mensajes.getLast().setContenido(mensajes.getLast().getContenido().replace("[SQL]","").replace("[/SQL]",""));
        //Metodo para hacer consulta reiteradamente
        String consultaIA = mensajes.getLast().getContenido();
        int numProductos = gestionAPP.totalProductosPeticionIA(consultaIA);
        session.setAttribute("numPaginas",(numProductos % 12 != 0? numProductos / 12 + 1 : numProductos / 12));
        session.setAttribute("pagina",1);
        session.setAttribute("productos", gestionAPP.getPaginaProductosPeticionIA(null,consultaIA));
        session.setAttribute("consultaIA",consultaIA);
        response.sendRedirect("index.jsp");

    } else {
        mensajes.getLast().setContenido(mensajes.getLast().getContenido().replace("[CONVERSACIÓN]","").replace("[/CONVERSACIÓN]",""));
        response.sendRedirect("ChatBot.jsp");
    }
%>
