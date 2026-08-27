<%@ page import="Controller.GestionAPP" %><%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 08/07/2026
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registrarse</title>
  <link rel="stylesheet" type="text/css" href="CSS/ValidarEmail.css">
  <link rel="icon" type="image/png" href="imagenes/logo%20fernanpop.png">
</head>
<body>
  <% GestionAPP gestionAPP = (GestionAPP) session.getAttribute("controller");
    session.setAttribute("email",request.getParameter("email"));
    if (gestionAPP.usuarioActivo((String) session.getAttribute("email")) && session.getAttribute("paginaAnterior").equals("registrarEmail.jsp")){
      session.setAttribute("error","Usuario ya registrado");
      session.setAttribute("recomendacion","Use otra cuenta o inicie sesión con esta");
      response.sendRedirect("Error");
    } else {
      out.print("<form method=\"get\" action=\"ValidarNumEntrada.jsp\">" +
              "<p class=\"info-envio\">Enviando correo de verificación a <strong>" + session.getAttribute("email") + "</strong></p>" +
              "<p class=\"info-instruccion\">Introduce la clave proporcionada</p>");
      out.print("<input type=\"text\" minlength=\"6\" maxlength=\"6\" name=\"numProbado\" required>\n" +
              "  <input type=\"submit\" value=\"Validar\">");
      out.print("<p class=\"error\">");
      if (session.getAttribute("ErrorNumEntrada") != null) out.print("Numero incorrecto te quedan " + session.getAttribute("oportunidades").toString() + " oportunidades");
      out.print("</p>");
      out.print("<script>\n" +
"      fetch(\"ProcesarAccion.jsp\")\n" +
"              .then(response => {\n" +
"                if (!response.ok) {\n" +
"                  throw new Error(\"Error en la petición\");\n" +
"                }\n" +
"                return response.text();\n" +
"              })\n" +
"              .then(resultado => {\n" +
"                resultado = resultado.trim();\n" +
"                // Si devuelve algo, redirigimos\n" +
"                if (resultado !== \"\") {\n" +
"                  window.location.href = \"Error.jsp\";\n" +
"                }\n" +
"              })\n" +
"              .catch(error => {\n" +
"                console.error(\"Error:\", error);\n" +
"                window.location.href = \"Error.jsp\";\n" +
"              });\n" +
"  </script>\n" +
"\n" +
"</form>");
    }
    if (session.getAttribute("oportunidades") == null) session.setAttribute("oportunidades",3);
  %>

</body>
</html>
