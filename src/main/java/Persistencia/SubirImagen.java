package Persistencia;

import Controller.GestionAPP;
import Modelos.Producto;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;

@WebServlet("/SubirImagen")
@MultipartConfig
public class SubirImagen extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        GestionAPP gestionAPP = (GestionAPP) session.getAttribute("controller");
        session.setAttribute("titulo",request.getParameter("titulo"));
        session.setAttribute("descripcion",request.getParameter("descripcion"));
        session.setAttribute("precio",request.getParameter("precio"));
        session.setAttribute("estado",request.getParameter("estado"));
        //Aquí guardamos la imagen
        String accion = (String) session.getAttribute("accion");
        if (accion != null && accion.equals("actualizaProducto")) {
            Producto producto = gestionAPP.buscarProductoId((Long) session.getAttribute("idProducto"));
            Part imagen = request.getPart("imagen");
            if (imagen != null && imagen.getSize() != 0){
                if (producto.getNombreImagen() != null) gestionAPP.eliminaImagen(producto.getNombreImagen());
                gestionAPP.subeImagen(request, session);
            } else {
                if (producto.getNombreImagen() != null) session.setAttribute("nombreImagen",producto.getNombreImagen());
            }
            response.sendRedirect("pantallaEspera.jsp");
        } else {
            gestionAPP.subeImagen(request, session);
            response.sendRedirect("pantallaEspera.jsp");
        }
    }
}
