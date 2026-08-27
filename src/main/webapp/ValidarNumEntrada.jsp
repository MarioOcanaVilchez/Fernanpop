<%@ page import="Controller.GestionAPP" %><%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 08/07/2026
  Time: 17:32
  To change this template use File | Settings | File Templates.
--%>
<%
    GestionAPP gestionAPP = (GestionAPP) session.getAttribute("controller");
    String numEntrada = (String) session.getAttribute("numEntrada");
String numProbado = request.getParameter("numProbado");
if (numEntrada.equals(numProbado)){
    if (!gestionAPP.usuarioBorrado((String) session.getAttribute("email")) && !gestionAPP.usuarioActivo((String) session.getAttribute("email"))) response.sendRedirect("RegistrarDatos.jsp");
    else {
        if (gestionAPP.usuarioBorrado((String) session.getAttribute("email"))){
            if (gestionAPP.recuperaUsuario((String) session.getAttribute("email"))){
                gestionAPP.setUsuario(gestionAPP.buscaMail((String) session.getAttribute("email")));
                session.setAttribute("controller",gestionAPP);
                response.sendRedirect("CambiaContrasenia.jsp");
            } else {
                session.setAttribute("error","Error al recuperar el usuario");
                session.setAttribute("recomendacion","Compruebe la conexión");
                response.sendRedirect("Error.jsp");
            }
        } else {
            gestionAPP.setUsuario(gestionAPP.buscaMail((String) session.getAttribute("email")));
            session.setAttribute("controller",gestionAPP);
            response.sendRedirect("CambiaContrasenia.jsp");
        }

    }
}
else {
    session.setAttribute("ErrorNumEntrada",true);
    session.setAttribute("oportunidades",(int) session.getAttribute("oportunidades") - 1);
    if ((int) session.getAttribute("oportunidades") <= 0){
        session.setAttribute("numEntrada",null);
        session.setAttribute("Error","Oportunidades agotadas");
        session.setAttribute("recomendacion","Use otra cuenta y revise su email");
        response.sendRedirect("Error.jsp");
    }
    else response.sendRedirect("ValidarEmail.jsp");
}
%>
