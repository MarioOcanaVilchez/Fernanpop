<%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 08/07/2026
  Time: 17:32
  To change this template use File | Settings | File Templates.
--%>
<% String numEntrada = (String) session.getAttribute("numEntrada");
String numProbado = request.getParameter("numProbado");
if (numEntrada.equals(numProbado)) response.sendRedirect("RegistrarDatos.jsp");
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
