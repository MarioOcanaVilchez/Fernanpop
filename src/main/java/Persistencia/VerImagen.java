package Persistencia;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
@WebServlet("/VerImagen")
public class VerImagen extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombreImagen = request.getParameter("nombreImagen");
        String ruta = "C:\\1ºDam\\Programación\\proyectosjava\\fernanpop\\imagenesProductos";
        File imagen = new File(ruta, nombreImagen);
        // Si no existe
        if (!imagen.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String tipo = getServletContext().getMimeType(imagen.getName());
        if (tipo == null) {
            tipo = "application/octet-stream";
        }
        response.setContentType(tipo);
        response.setContentLengthLong(imagen.length());
        // Envía la imagen al navegador
        Files.copy(imagen.toPath(), response.getOutputStream());
        response.getOutputStream().flush();
    }
}
