<%@ page import="Controller.GestionAPP" %><%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 08/07/2026
  Time: 16:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="CSS/PantallaEspera.css">
    <link rel="icon" type="image/png" href="imagenes/logo%20fernanpop.png">
</head>
<body>
<div id="pantallaCarga" class="loading-overlay">
    <div class="spinner"></div>
    <p class="loading-text" id="loadingText"></p>
    <%
        String accion = session.getAttribute("accion").toString();
        GestionAPP gestionAPP = (GestionAPP) session.getAttribute("controller");
        switch (accion){
            case "enviarCorreoVerificacion":
                session.setAttribute("email",request.getParameter("email"));
                break;
            case "crearCuenta":
                session.setAttribute("nombre",request.getParameter("nombre"));
                session.setAttribute("apel",request.getParameter("apel"));
                session.setAttribute("telefono",request.getParameter("telefono"));
                session.setAttribute("clave",request.getParameter("clave"));
                break;
            case "iniciarSesion":
                session.setAttribute("email",request.getParameter("email"));
                session.setAttribute("clave",request.getParameter("clave"));
                break;
            case "procesoCompra":
                if (gestionAPP.getUsuario() == null) response.sendRedirect("InicioSesion.jsp");
                else session.setAttribute("precio",Double.parseDouble(request.getParameter("precio")));
                break;

        }
      %>
    <script>
        const accion = "<%= session.getAttribute("accion") %>";
        const textos = {
            "enviarCorreoVerificacion": "Enviando correo de verificación",
            "crearCuenta": "Creando cuenta",
            "iniciarSesion": "Iniciando sesión",
            "crearProducto": "Creando producto",
            "procesoCompra": "Procediendo con la compra",
            "vendeProducto":"Aceptando solicitud de venta",
            "actualizaProducto":"Actualizando producto",
            "default": "Cargando"
        };
        document.getElementById("loadingText").innerText = textos[accion] || textos["default"];
        <%
        if (!accion.equals("procesoCompra") || gestionAPP.getUsuario() != null)
            out.print("fetch(\"ProcesarAccion.jsp\")\n" +
"            .then(response => response.text())\n" +
"            .then(resultado => {\n" +
"                window.location.href = resultado.trim() + \".jsp\";\n" +
"            })\n" +
"            .catch(error => {\n" +
"                window.location.href = \"Error.jsp\";\n" +
"            });");
    %>

    </script>
</div>
</body>
</html>
