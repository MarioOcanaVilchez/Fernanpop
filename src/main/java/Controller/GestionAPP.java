package Controller;

import Dao.*;
import Modelos.*;
import Persistencia.Persistencia;
import Utilidades.Comunicaciones;
import Utilidades.PlantillasCorreo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GestionAPP {
    private DaoManager dao;
    private DaoUsuarioSQL daoUsuario;
    private DaoProductoSQL daoProducto;
    private DaoTratoSQL daoTrato;
    private DaoChatSQL daoChat;
    private DaoMensajeSQL daoMensaje;
    private DaoBloqueoSQL daoBloqueo;
    private Usuario usuario;

    public GestionAPP() {
        dao = DaoManager.getSinglentonInstance();
        daoUsuario = new DaoUsuarioSQL();
        daoTrato = new DaoTratoSQL();
        daoProducto = new DaoProductoSQL();
        daoChat = new DaoChatSQL();
        daoMensaje = new DaoMensajeSQL();
        daoBloqueo = new DaoBloqueoSQL();
        //Persistencia.existenCarpetas();
        //usuario = cogeUsuarioSesionAnt();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public ArrayList<Trato> getHistoricoCompras(){
        return daoTrato.historicoCompras(dao,usuario,daoProducto,daoUsuario);
    }
    public ArrayList<Trato> getSolicitudesCompra(){
        return daoTrato.comprasPendientes(dao,usuario,daoProducto,daoUsuario);
    }
    public ArrayList<Trato> getHistoricoVentas(){
        return daoTrato.historicoVentas(dao,usuario,daoProducto,daoUsuario);
    }
    public ArrayList<Integer> getValoracionesPendientes(){
        return daoTrato.valoracionesPendientes(dao,usuario);
    }
    public ArrayList<Trato> getVentasPendientes(){
        return daoTrato.ventasPendientes(dao,usuario,daoProducto,daoUsuario);
    }
    public int valoracionesPendientes(){
        return daoTrato.valoracionesPendientesNum(dao,usuario);
    }

    //consulta de productos en venta
    public int getTotalProductos(String textoBuscar,int precioMin,int precioMax){
        if (precioMax == 2000) precioMax = Integer.MAX_VALUE;
        if (usuario != null) return daoProducto.totalProductosEnVenta(dao,daoTrato,daoProducto,usuario,textoBuscar,precioMin,precioMax);
        else return daoProducto.totalProductosEnVenta(dao,textoBuscar,precioMin,precioMax);
    }
    public long generaIdProducto(){
        return daoProducto.generaId(dao);
    }
    public boolean buscaIdUser(int id){
        return daoUsuario.buscaUsuarioId(dao,id) != null;
    }

    public Usuario buscaMail(String mail){
        return daoUsuario.buscaUsuarioMail(dao,mail);
    }
    //consulta select
    public boolean addUsuario(String email,String nombre,String apel,String clave,int movil){
        clave = encriptarClave(clave);
        if (daoUsuario.insertaUsuario(dao,email,nombre,apel,clave,movil)) {
            usuario = daoUsuario.buscaUsuarioMail(dao,email);
            return true;
        } else return false;
    }
    public boolean eliminaSolicitudCompra(int id){
        return daoTrato.eliminaTrato(dao,id);
    }
    public boolean addProducto(String titulo,String descripcion,String estado,double precio){
        if (!Comunicaciones.enviarEmail(usuario.getEmail(),"Nuevo producto publicado",PlantillasCorreo.emailNuevoProducto(titulo,descripcion,precio))) return false;
        if(daoProducto.insertarProducto(dao,usuario,titulo,descripcion,estado,precio)) {
            //LogManager.logNuevoProducto(producto, usuario);
            return true;
        }
        return false;
    }
    public boolean addProducto(String titulo,String descripcion,String estado,double precio,long id,String nombreImagen){
        if (!Comunicaciones.enviarEmail(usuario.getEmail(),"Nuevo producto publicado",PlantillasCorreo.emailNuevoProducto(titulo,descripcion,precio))) return false;
        if(daoProducto.insertarProducto(dao,usuario,titulo,descripcion,estado,precio,id,nombreImagen)) {
            //LogManager.logNuevoProducto(producto, usuario);
            return true;
        }
        return false;
    }
    //consulta select
    public boolean login(String email,String clave){
        usuario = daoUsuario.login(dao,email,clave);
        return usuario != null;
    }
    public ArrayList<Producto> getProductosUser(){
        return daoProducto.buscaProductoIdUser(dao,usuario.getId());
    }

    //Se te pueden colar productos con ventaPendiente que se quitan solos pero no busca mas es decir deja el hueco
    public ArrayList<Producto> getPaginaProductos(ArrayList<Producto> productos,String textoBuscar,String orden,int precioMin,int precioMax){
        if (precioMax == 2000) precioMax = Integer.MAX_VALUE;
        if (usuario == null) return daoProducto.getPaginaProductos(dao,productos,textoBuscar,orden,precioMin,precioMax);
        return daoProducto.getPaginaProductos(dao,usuario,daoTrato,productos,daoTrato.productosVentasPendientesConParametros(dao,usuario),textoBuscar,orden,precioMin,precioMax);
    }
    public Producto buscarProductoId(long id){
        return daoProducto.buscaProductoId(dao,id);
    }

    public ArrayList<Producto> buscaProductoTexto(String titulo){
        return daoProducto.buscaProductoPorTexto(dao,titulo,usuario,daoTrato);
    }
    public long getProductoAleatorio(){
        return daoProducto.getProductoAleatorio(dao,daoTrato,usuario);
    }
    //Borra un usuario
    public boolean eliminarUsuario(){
        if (eliminaAllProductos() && eliminaAllTratosPendientes()) {
            if (daoUsuario.eliminaUsuario(dao, usuario, daoProducto)) {
                usuario = null;
                return true;
            }
        }
        return false;
    }
    public boolean recuperaUsuario(String email){
        return daoUsuario.recuperaUsuario(dao,buscaMail(email));
    }

    public boolean usuarioActivo(String email){
        return daoUsuario.usuarioActivo(dao,email);
    }
    public boolean usuarioBorrado(String email){
        return daoUsuario.usuarioBorrado(dao,email);
    }
    public boolean eliminaAllProductos(){
        ArrayList<Producto> productos = daoProducto.buscaProductoIdUser(dao,usuario.getId());
        if (!productos.isEmpty()){
            for (Producto p : productos){
                if (!eliminarProducto(p)) return false;
            }
        }
        return true;
    }
    public boolean eliminaAllTratosPendientes(){
        ArrayList<Trato> tratosPendientes = daoTrato.comprasPendientes(dao,usuario,daoProducto,daoUsuario);
        tratosPendientes.addAll(daoTrato.ventasPendientes(dao,usuario,daoProducto,daoUsuario));
        if (!tratosPendientes.isEmpty()){
            for (Trato t : tratosPendientes){
                if (!daoTrato.eliminaTrato(dao,t)) return false;
            }
        }
        return true;
    }
    public Trato buscarTratoId(int id){
        return daoTrato.buscaTratoId(dao,id,daoProducto,daoUsuario);
    }
    //Busca al usuario dueño del producto
    public Usuario buscaUserPorProducto(Producto producto){
        return daoUsuario.buscaUsuarioPorProducto(dao,producto,daoProducto);
    }

    //actualizar un trato al añadir una puntuacion
    public boolean actualizaTrato(Trato trato,String comentario, int puntuacion){
        if (daoTrato.actualizaTratos(dao,trato,comentario,puntuacion)) {
            trato.setComentario(comentario);
            trato.setPuntuacion(puntuacion);
            trato.setTipo("Comprado");
            return true;
        }
        return false;
    }
    //sirve para crear la solicitud de compra
    public boolean addTratoCompra(Usuario vendedor, Producto producto, double precio) {
        if (!Comunicaciones.enviarEmail(vendedor.getEmail(),"Solicitud de compra",PlantillasCorreo.emailSolicitud(producto,usuario,precio))) return false;
        return daoTrato.addtratoCompra(dao, vendedor, producto.getId(), usuario.getId(), precio, daoProducto,daoUsuario);
    }
    public boolean eliminarProducto(Producto p){
        if (daoProducto.borrarProducto(dao,p)){
            if (p.getNombreImagen() != null) eliminaImagen(p.getNombreImagen());
            return true;
        }
        return false;
    }
    public boolean vendeProducto(Trato t, Usuario comprador){
        if (daoTrato.addtratoVenta(dao, t, usuario, daoUsuario, daoProducto)) {
            Comunicaciones.enviarEmailConPDF(comprador.getEmail(), "¡Enhorabuena, compra realizada!", PlantillasCorreo.emailProductoVendido(t.getProducto(), usuario), t, comprador.getEmail(), usuario.getEmail());
            Comunicaciones.enviarEmailConPDF(usuario.getEmail(), "¡Enhorabuena, venta realizada!", PlantillasCorreo.emailProductoVendidoComprador(t.getProducto(), comprador), t, comprador.getEmail(), usuario.getEmail());
            return true;
        }
        return false;
    }
    public boolean rechazaSolicitudVenta(Trato t){
        return daoTrato.eliminaTrato(dao,t);
    }
    public boolean cambiaEmail(String email){
        String emailAnt = usuario.getEmail();
        usuario.setEmail(email);
        if (daoUsuario.actualizaUsuario(dao,usuario)){
            return true;
        }
        usuario.setEmail(emailAnt);
        return false;
    }
    public boolean cambiaClave(String clave){
        clave = encriptarClave(clave);
        String claveAnt = usuario.getClave();
        usuario.setClave(clave);
        if (daoUsuario.actualizaUsuario(dao,usuario)){
            return true;
        }
        usuario.setClave(claveAnt);
        return false;
    }
    public boolean cambiaNombre(String nombre){
        String nombreAnt = usuario.getNombre();
        usuario.setNombre(nombre);
        if (daoUsuario.actualizaUsuario(dao,usuario)){
            Persistencia.ponerUserEnUso(usuario);
            return true;
        }
        usuario.setNombre(nombreAnt);
        return false;
    }
    public boolean cambiaApel(String apel){
        String apelAnt = usuario.getApel();
        usuario.setApel(apel);
        if (daoUsuario.actualizaUsuario(dao,usuario)){
            return true;
        }
        usuario.setApel(apelAnt);
        return false;
    }
    public boolean cambiaTelefono(int telefono){
        int telefonoAnt = usuario.getMovil();
        usuario.setMovil(telefono);
        if (daoUsuario.actualizaUsuario(dao,usuario)){
            return true;
        }
        usuario.setMovil(telefonoAnt);
        return false;
    }
    public boolean permisoSinLogeo(){
        return Persistencia.permisoUsoSinLogeo();
    }

    public boolean copiaSeguridad(String ruta){
        return Persistencia.copiaSeguridad(ruta,daoUsuario.getAllUsuarios(dao));
    }

    public boolean enviaCorreoProductos(Usuario uTemp){
        if (!Persistencia.creaFicheroProductos(new GestionAPP())) return false;
        if (!Comunicaciones.enviarEmailConProductos(uTemp.getEmail(),"Productos","Adjunto todos los productos")) return false;
        Persistencia.eliminaFicheroProductos();
        return true;
    }
    /*public void mock(){
        addUsuario("mocavil1107@g.educaand.es","Mario","Ocaña Vílchez","1234",0 );
        addUsuario("carnivor3426@gmail.com","Roberto","Ortega Molina", "1234",0);
        login("mocavil1107@g.educaand.es","1234");
        addProducto(new Producto(generaIdProducto(),"Gafas de sol", "Buenas contra el sol", 19.99,"Como nuevo"));
        addProducto(new Producto(generaIdProducto(),"Mesa", "genial para una comida familiar", 34,"Bien cuidado"));
        addProducto(new Producto(generaIdProducto(),"Auriculares", "Son inalámbricos y de buena calidad", 17,"Usado"));
        addProducto(new Producto(generaIdProducto(),"Ratón", "Logitech G203 Lightsync 2nd Gen 8000DPI RGB negro", 19.99,"Bien cuidado"));
        addProducto(new Producto(generaIdProducto(),"Enciclopedia", "Es una joya histórica que lleva en mi familia desde 1879", 19.99,"Usado"));
        addProducto(new Producto(generaIdProducto(),"Televisión", "De 2024 1440p ful HD", 499.99,"Poco uso"));
        login("carnivor3426@gmail.com","1234");
        addProducto(new Producto(generaIdProducto(),"Lámpara","Muy útil para ver de noche",9.5,"Usado"));
        addProducto(new Producto(generaIdProducto(),"coche","Seat ibiza del 2004 180.000Km (ITV sin pasar)",3500,"Bien cuidado"));
        addProducto(new Producto(generaIdProducto(),"Iphone 15","Iphone 15 Pro Max 256GB 12GB RAM",450,"Como nuevo"));
        addProducto(new Producto(generaIdProducto(),"Pecera","Amplia y con decoración incluida",50,"Poco uso"));
        addProducto(new Producto(generaIdProducto(),"Barco en una botella","Lo encontre en el mar",4.99,"Deteriorado"));
        addProducto(new Producto(generaIdProducto(),"Barril de petróleo","El oro negro actual",120,"Como nuevo"));
        addProducto(new Producto(generaIdProducto(),"PS5 + juegos","1 TB de almacenamiento",399.99,"Poco uso"));
        usuario = null;

    }*/

    public String generarNumEntrada(){
        int num = (int) (Math.random() * 999999);
        String numFinal = String.valueOf(num);
        numFinal = finalizarNum(numFinal);
        return numFinal;
    }
    public String finalizarNum(String num){
        if (num.length() != 6){
            for (int i = 0; i < 6 - num.length(); i++) {
                num = "0" + num;
            }
        }
        return num;
    }
    public boolean enviarCorreoVerificacion(String num,String email){
        return Comunicaciones.enviarEmail(email, "Correo de verificación", PlantillasCorreo.emailNumero(num));
    }
    public void subeImagen( HttpServletRequest request, HttpSession session){
        String accion = (String) session.getAttribute("accion");
        if (accion != null && accion.equals("actualizaProducto")) Persistencia.subeImagen((Long) session.getAttribute("idProducto"), request, session);
        else Persistencia.subeImagen(generaIdProducto(), request, session);
    }
    public void eliminaImagen(String nombre){
        Persistencia.eliminaImagen(nombre);
    }
    public boolean estaEnVenta(Producto producto){
        return daoProducto.estaEnVenta(dao,producto);
    }
    public boolean actualizaProducto(String titulo,String descripcion,String estado,double precio,long id){
        return daoProducto.actualizaProducto(dao,titulo,descripcion,estado,precio,id);
    }
    public boolean actualizaProducto(String titulo,String descripcion,String estado,double precio,String nombreImagen,long id){
        return daoProducto.actualizaProducto(dao,titulo,descripcion,estado,precio,nombreImagen,id);
    }
    public boolean esTuProducto(Producto producto){
        return daoProducto.esTuProducto(dao,producto,usuario);
    }
    public String encriptarClave(String clave){
        return BCrypt.hashpw(clave,BCrypt.gensalt());
    }

    //Chats
    public ArrayList<Chat> getChats(){
        return daoChat.getChats(dao,daoUsuario,daoMensaje,usuario);
    }
    public Chat getChat(long idChat){
        return daoChat.getChat(dao,daoUsuario,daoMensaje,usuario,idChat);
    }
    public Chat recargaChat(long id){
        Chat chat = daoChat.cargaChat(dao,daoMensaje,daoUsuario,usuario,id);
        if (chat != null) daoMensaje.leeMensajesChat(dao,chat.getId(),usuario);
        return chat;
    }
    public boolean marcarMensajesLeidos(long idChat){
        return daoMensaje.leeMensajesChat(dao,idChat,usuario);
    }
    public long buscaChat(int idUser){
        return daoChat.buscaChat(dao,usuario,idUser);
    }
    public Usuario buscaUsuarioId(int id){
        return daoUsuario.buscaUsuarioId(dao,id);
    }
    public boolean creaChat(int idUser){
        Usuario[] usuarios = new Usuario[2];
        usuarios[0] = usuario;
        usuarios[1] = buscaUsuarioId(idUser);
        if (daoChat.crearChat(dao,usuarios)){
            enviaPrimerMensaje(buscaChat(idUser),usuarios);
            return true;
        }
        return false;
    }
    public boolean enviaPrimerMensaje(long idChat,Usuario[] usuarios){
        String contenido = "bienvenido a este nuevo chat";
        return daoMensaje.enviaPrimerMensaje(dao,contenido,idChat,usuarios);
    }
    public boolean enviarMensaje(long idChat,String mensaje){
        Usuario [] usuarios = daoChat.getUsuariosChat(dao,daoUsuario,idChat);
        if (daoMensaje.insertaMensaje(dao,mensaje,idChat,usuarios,LocalDateTime.now(),usuario.getId())){
            daoChat.actualizaUltimoMensaje(dao,LocalDateTime.now(),idChat);
            return true;
        }
        return false;
    }
    public int getTotalMensajesNoLeidos(){
        return daoMensaje.determinarTotalMensajesSinLeer(dao,usuario);
    }
    public int getMensajesNoLeidos(long idChat){
        return daoMensaje.determinarMensajesSinLeer(dao,idChat,usuario);
    }
    public boolean eliminarMensajeParaUser(long idChat,long idMensaje){
        return daoMensaje.eliminaMensaje(dao,idChat,idMensaje,usuario);
    }
    public boolean eliminarMensajeChat(long idChat,long idMensaje){
        return daoMensaje.eliminaContenidoMensaje(dao,idChat,idMensaje);
    }
    //bloqueos
    public boolean userBloqueado(int idUser){
        return daoBloqueo.userBloqueado(dao,usuario.getId(),idUser);
    }
    public boolean otroUserBloqueoAUser(int idUser){
        return daoBloqueo.userBloqueado(dao,idUser,usuario.getId());
    }
    public boolean bloqueaUser(int idUser){
        return daoBloqueo.bloqueaUser(dao,daoMensaje,usuario.getId(),idUser,buscaChat(idUser));
    }
    public boolean desbloquearUser(int idUser){
        return daoBloqueo.desbloquearUser(dao,usuario.getId(),idUser);
    }
    public boolean enviarMensajeDeBloqueado(long idChat,String mensaje){
        return daoMensaje.insertaMensajeDeBloqueado(dao,mensaje,idChat,LocalDateTime.now(),usuario.getId());
    }
    public boolean vaciarChat(long idChat){
        return daoMensaje.eliminaMensajes(dao,idChat,usuario.getId());
    }
    public Mensaje buscaMensaje(long idChat,long idMensaje){
        return daoMensaje.buscaMensajeChatUsuario(dao,idChat,idMensaje,usuario);
    }
    public boolean actualizaMensaje(long idChat,long idMensaje,String nuevoMensaje){
        return daoMensaje.actualizaMensaje(dao,idChat,idMensaje,nuevoMensaje,usuario);
    }

    //Chatbot
    public String consultarIA(String pregunta) {
        // API Key
        String apiKey = Persistencia.leeProperties("apiKeyIA");

        String urlAnythingLLM = Persistencia.leeProperties("rutaIA");

        JSONObject body = new JSONObject();
        body.put("message", pregunta);
        body.put("mode", "chat");
        body.put("stream", false);

        String jsonBody = body.toString();
        try {
            System.setProperty("jdk.httpclient.allowRestrictedHeaders", "true");

            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlAnythingLLM))
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONObject jsonResponse = new JSONObject(response.body());
                return jsonResponse.getString("textResponse");
            } else {
                return "Lo lamento señor pero el servicio está caído, contacte con el administrador";
            }
        } catch (Exception e){
            return "Lo lamento señor pero el servicio está caído, contacte con el administrador";
        }
    }
    //Esto solo se ejecuta la primera vez para saber cuantas páginas hay
    public String completaPeticionIANumProductos(String peticion) {
        peticion += " and id_usuario !=" + usuario.getId();
        ArrayList<Long> ids = daoTrato.productosVentasPendientesConParametros(dao, usuario);
        if (!ids.isEmpty()) {
            peticion += " and id not in(";
            for (long id : ids){
                peticion += id + ",";
            }
            peticion = peticion.substring(0,peticion.length() - 1) + ") order by rand()";
        }
        return peticion;
    }
    //Esto solo se ejecuta la primera vez para saber cuantas páginas hay
    public int totalProductosPeticionIA(String peticion){
        peticion = completaPeticionIANumProductos(peticion);
        return daoProducto.cuentaProductosPeticionIA(dao,peticion);
    }
    public ArrayList<Producto> getPaginaProductosPeticionIA(ArrayList<Producto> productosActuales,String peticion){
        ArrayList<Long> productosSolicitados = daoTrato.productosVentasPendientesConParametros(dao, usuario);
        return daoProducto.getPaginaProductosPeticionIA(dao,usuario,peticion,productosActuales,productosSolicitados);
    }
    public Producto rellenaHuecoProducto(ArrayList<Producto> productosActuales,String textoBuscar,String orden,int precioMin,int precioMax){
        return daoProducto.getProducto(dao,usuario,productosActuales,textoBuscar,orden,precioMin,precioMax,daoTrato.productosVentasPendientesConParametros(dao,usuario));
    }
    public Producto rellenaHuecoProductoPeticionIA(ArrayList<Producto> productosActuales,String peticion){
        return daoProducto.getProducto(dao,usuario,productosActuales,daoTrato.productosVentasPendientesConParametros(dao,usuario),peticion);
    }


}
