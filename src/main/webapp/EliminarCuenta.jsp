<%@ page import="Controller.GestionAPP" %>
<%
  GestionAPP gestionAPP = (GestionAPP) session.getAttribute("controller");
  if (gestionAPP.eliminarUsuario()){
    session.setAttribute("controller",gestionAPP);
    response.sendRedirect("index.jsp");
  } else {
    session.setAttribute("error","Error al eliminar la cuenta");
    session.setAttribute("recomendacion","Compruebe la conexión");
    response.sendRedirect("Error.jsp");
  }
%>

