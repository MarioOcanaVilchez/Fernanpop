<%--
  Created by IntelliJ IDEA.
  User: carni
  Date: 08/07/2026
  Time: 17:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="Controller.GestionAPP" %><%@ page import="Modelos.Usuario"%><%@ page import="Modelos.Producto"%><%@ page import="java.util.ArrayList"%><%@ page import="Modelos.Trato"%>
<%@ page contentType="text/plain;charset=UTF-8" language="java" %>
<%
    session.setAttribute("error","Error");
    session.setAttribute("recomendacion","Inténtelo de nuevo");
    String accion = (String) session.getAttribute("accion");
    GestionAPP gestionAPP = (GestionAPP) session.getAttribute("controller");
    String email = (String) session.getAttribute("email");
    String clave,nombre,apel,titulo,descripcion,estado,nombreImagen;
    double precio;
    switch (accion) {
        case "enviarCorreoVerificacion":
            String numEntrada = gestionAPP.generarNumEntrada();
            session.setAttribute("numEntrada", numEntrada);
            if (gestionAPP.enviarCorreoVerificacion(numEntrada, email)) {
                out.print("");
            } else {
              session.setAttribute("error","Error al enviar el correo");
              session.setAttribute("recomendacion","Compruebe la conexión");
                out.print("Error");
            }
        break;
        case "crearCuenta":
            nombre = (String) session.getAttribute("nombre");
            clave = (String) session.getAttribute("clave");
            apel = (String) session.getAttribute("apel");
            int telefono = 0;
            try{
            if (session.getAttribute("telefono") != null) telefono = Integer.parseInt((String) session.getAttribute("telefono"));
            } catch (NumberFormatException e) {

            }
            if (gestionAPP.addUsuario(email,nombre,apel,clave,telefono)){
                session.setAttribute("controller",gestionAPP);
                out.print("index");
            } else {
                session.setAttribute("error","Error al crear la cuenta");
                session.setAttribute("recomendacion","Compruebe la conexión");
                out.print("Error");
            }
        break;
        case "iniciarSesion":
            clave = (String) session.getAttribute("clave");
            if (gestionAPP.login(email,clave)){
                session.setAttribute("controller",gestionAPP);
                out.print("index");
            } else {
                session.setAttribute("errorSesion","Usuario o contraseña incorrectos");
                out.print("InicioSesion");
            }
        break;
        case "crearProducto":
            titulo = (String) session.getAttribute("titulo");
            descripcion = (String) session.getAttribute("descripcion");
            precio = Double.parseDouble((String) session.getAttribute("precio"));
            estado = (String) session.getAttribute("estado");
            if (session.getAttribute("nombreImagen") == null){
                if (gestionAPP.addProducto(titulo,descripcion,estado,precio)){
                    session.setAttribute("acierto","Producto creado");
                    out.print("Acierto");
                }else {
                    session.setAttribute("error","Error al crear el producto");
                    session.setAttribute("recomendacion","Compruebe la conexión");
                    out.print("Error");
                }
            } else {
                nombreImagen = (String) session.getAttribute("nombreImagen");
                long id = (long) session.getAttribute("id");
                if (gestionAPP.addProducto(titulo,descripcion,estado,precio,id,nombreImagen)){
                    session.setAttribute("acierto","Producto creado");
                    out.print("Acierto");
                } else {
                    session.setAttribute("error","Error al crear el producto");
                    session.setAttribute("recomendacion","Compruebe la conexión");
                    out.print("Error");
                }
            }
        break;
        case "procesoCompra":
            Producto producto = (Producto) session.getAttribute("producto");
            if (!gestionAPP.esTuProducto(producto)) {
                if (gestionAPP.estaEnVenta(producto)){
                    if (gestionAPP.addTratoCompra(gestionAPP.buscaUserPorProducto(producto),producto,(Double) session.getAttribute("precio"))){
                        ArrayList<Producto> productos = (ArrayList<Producto>) session.getAttribute("productos");
                        productos.removeIf(p -> p.getId() == producto.getId());
                         session.setAttribute("productos",productos);
                        int numProductos = gestionAPP.getTotalProductos((String) session.getAttribute("textoBuscar"),(Integer) session.getAttribute("precioMin"),(Integer) session.getAttribute("precioMax"));
                        //Sistema para detectar si se han vendido los suficientes productos como para que alla menos paginas
                        if (numProductos % 12 != 0 && numProductos / 12 + 1 < (Integer) session.getAttribute("numPaginas") || numProductos % 12 == 0 && numProductos / 12 < (Integer) session.getAttribute("numPaginas")){
                            session.setAttribute("numPaginas",(numProductos % 12 != 0? numProductos / 12 + 1 : numProductos / 12));
                        //Sistema para detectar si estamos en una pagina que ya no existe
                        if ((Integer) session.getAttribute("pagina") > (Integer) session.getAttribute("numPaginas")) session.setAttribute("pagina",session.getAttribute("numPaginas"));
                        }
                        //obtenemos un producto mas para rellenar el hueco del que solicitamos
                        if (session.getAttribute("consultaIA") != null){
                            Producto p = gestionAPP.rellenaHuecoProductoPeticionIA(productos,(String) session.getAttribute("consultaIA"));
                            if (p != null) productos.add(p);
                        } else {
                           String textoBuscar = (String) session.getAttribute("textoBuscar");
                           int precioMin = (Integer) session.getAttribute("precioMin");
                           int precioMax = (Integer) session.getAttribute("precioMax");
                           Producto p = gestionAPP.rellenaHuecoProducto(productos,textoBuscar,(String) session.getAttribute("orden"),precioMin,precioMax);
                           if (p != null) productos.add(p);
                        }
                        session.setAttribute("acierto","Solicitud de compra enviada");
                        out.print("Acierto");
                    } else {
                        session.setAttribute("error","Error al enviar la solicitud de compra");
                        session.setAttribute("recomendacion","Compruebe la conexión");
                        out.print("Error");
                    }
                } else {
                    session.setAttribute("error","Error producto ya vendido");
                     session.setAttribute("recomendacion","Seleccione otro producto");
                    out.print("Error");
                }
            } else {
                     session.setAttribute("error","Error producto de su propiedad");
                     session.setAttribute("recomendacion","Seleccione otro producto este lo esta vendiendo usted");
                     out.print("Error");
            }
        break;
        case "vendeProducto":
            Trato trato = gestionAPP.buscarTratoId((Integer) session.getAttribute("idTrato"));
            if (trato != null){
                if (gestionAPP.vendeProducto(trato,gestionAPP.buscaMail(trato.getEmailOtroUser()))){
                    out.print("SolicitudesVenta");
                } else {
                    session.setAttribute("error","Error al aceptar la solicitud de venta");
                    session.setAttribute("recomendacion","Compruebe la conexión");
                    out.print("Error");
                }
            } else {
                session.setAttribute("error","Error al aceptar la solicitud de venta");
                    session.setAttribute("recomendacion","Solicitud retirada");
                    out.print("Error");
            }
        break;
        case "actualizaProducto":
            titulo = (String) session.getAttribute("titulo");
            descripcion = (String) session.getAttribute("descripcion");
            precio = Double.parseDouble((String) session.getAttribute("precio"));
            estado = (String) session.getAttribute("estado");
            long id = (Long) session.getAttribute("idProducto");
            if (session.getAttribute("nombreImagen") == null){
                if (gestionAPP.actualizaProducto(titulo,descripcion,estado,precio,id)){
                    session.setAttribute("acierto","Producto actualizado");
                    out.print("Acierto");
                }else {
                    session.setAttribute("error","Error al actualizar el producto");
                    session.setAttribute("recomendacion","Compruebe la conexión");
                    out.print("Error");
                }
            }else {
                nombreImagen = (String) session.getAttribute("nombreImagen");
                if (gestionAPP.actualizaProducto(titulo,descripcion,estado,precio,nombreImagen,id)){
                    session.setAttribute("acierto","Producto actualizado");
                    out.print("Acierto");
                } else {
                    session.setAttribute("error","Error al actualizar el producto");
                    session.setAttribute("recomendacion","Compruebe la conexión");
                    out.print("Error");
                }
            }
        break;
        case "comprobarRecargaChats":
            int totalMensajesSesionSinLeer = (int) session.getAttribute("totalMensajesSinLeer");
            if (totalMensajesSesionSinLeer != gestionAPP.getTotalMensajesNoLeidos()) out.print("recarga");
            else out.print("");
        break;
        case "comprobarRecargaChat":
            if (gestionAPP.getMensajesNoLeidos((long) session.getAttribute("idChat")) != 0) out.print("recarga");
            else out.print("");
        break;
        case "esperaChatbot":
            session.setAttribute("mensajeEscrito",request.getParameter("valor"));
        break;
        default:
            session.setAttribute("error","Error desconocido");
            session.setAttribute("recomendacion","Contacte con el servicio técnico");
            out.print("Error");
        break;
    }
%>
