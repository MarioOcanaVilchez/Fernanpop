<%@ page import="Controller.GestionAPP" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Modelos.Producto" %><%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 12/07/2026
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    GestionAPP gestionAPP = (GestionAPP) session.getAttribute("controller");
    String textoBuscar = (String) session.getAttribute("textoBuscar");
    int precioMin = (Integer) session.getAttribute("precioMin");
    int precioMax = (Integer) session.getAttribute("precioMax");
    //Casos en los que no se cambia la pagina (Ya no sirven porque se desabilita el botón pero esta bien tenerlos por si acaso)
    if (session.getAttribute("pagina") == session.getAttribute("numPaginas") && request.getParameter("cambio").equals("mas")) response.sendRedirect("index.jsp");
    else if (session.getAttribute("pagina").toString().equals("1") && request.getParameter("cambio").equals("menos"))response.sendRedirect("index.jsp");
    //Casos en los que si pero no hay que buscar mas productos
    else if (request.getParameter("cambio").equals("menos")) {
        session.setAttribute("pagina", (Integer) session.getAttribute("pagina") - 1);
        response.sendRedirect("index.jsp");
        //Cambios que si pueden requerir buscar mas productos

    } else if (session.getAttribute("consultaIA") != null){
        session.setAttribute("pagina",(Integer) session.getAttribute("pagina") + 1);
        ArrayList<Producto> productos = (ArrayList<Producto>) session.getAttribute("productos");
        //Basicamente comprobamos si en la pagina caben mas productos y hay mas
        if (productos.size() < (Integer) session.getAttribute("pagina") * 12 && session.getAttribute("pagina") != session.getAttribute("numPaginas") || gestionAPP.totalProductosPeticionIA((String) session.getAttribute("consultaIA")) != productos.size() && session.getAttribute("pagina") == session.getAttribute("numPaginas")) productos.addAll(gestionAPP.getPaginaProductosPeticionIA(productos,(String) session.getAttribute("consultaIA")));
        session.setAttribute("productos",productos);
        response.sendRedirect("index.jsp");
    } else if (session.getAttribute("textoBuscar") == null){
        session.setAttribute("pagina",(Integer) session.getAttribute("pagina") + 1);
        ArrayList<Producto> productos = (ArrayList<Producto>) session.getAttribute("productos");
        //Basicamente comprobamos si en la pagina caben mas productos y hay mas
        if (productos.size() < (Integer) session.getAttribute("pagina") * 12 && session.getAttribute("pagina") != session.getAttribute("numPaginas") || gestionAPP.getTotalProductos(textoBuscar,precioMin,precioMax) != productos.size() && session.getAttribute("pagina") == session.getAttribute("numPaginas")) productos.addAll(gestionAPP.getPaginaProductos((ArrayList<Producto>) session.getAttribute("productos"),null,(String) session.getAttribute("orden"),0,Integer.MAX_VALUE));
        session.setAttribute("productos",productos);
        response.sendRedirect("index.jsp");
    } else {
        session.setAttribute("pagina",(Integer) session.getAttribute("pagina") + 1);
        ArrayList<Producto> productos = (ArrayList<Producto>) session.getAttribute("productos");
        //Basicamente comprobamos si en la pagina caben mas productos y hay mas
        if (productos.size() < (Integer) session.getAttribute("pagina") * 12 && session.getAttribute("pagina") != session.getAttribute("numPaginas") || gestionAPP.getTotalProductos(textoBuscar,precioMin,precioMax) != productos.size() && session.getAttribute("pagina") == session.getAttribute("numPaginas")) productos.addAll(gestionAPP.getPaginaProductos((ArrayList<Producto>) session.getAttribute("productos"),(String) session.getAttribute("textoBuscar"),(String) session.getAttribute("orden"),0,Integer.MAX_VALUE));
        session.setAttribute("productos",productos);
        response.sendRedirect("index.jsp");
    }

%>
</body>
</html>
