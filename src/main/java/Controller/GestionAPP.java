package Controller;

import Dao.*;
import Modelos.Chat;
import Modelos.Producto;
import Modelos.Trato;
import Modelos.Usuario;
import Persistencia.Persistencia;
import Utilidades.Comunicaciones;
import Utilidades.PlantillasCorreo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class GestionAPP {
    private DaoManager dao;
    private DaoUsuarioSQL daoUsuario;
    private DaoProductoSQL daoProducto;
    private DaoTratoSQL daoTrato;
    private DaoChatSQL daoChat;
    private DaoMensajeSQL daoMensaje;
    private Usuario usuario;

    public GestionAPP() {
        dao = DaoManager.getSinglentonInstance();
        daoUsuario = new DaoUsuarioSQL();
        daoTrato = new DaoTratoSQL();
        daoProducto = new DaoProductoSQL();
        daoChat = new DaoChatSQL();
        daoMensaje = new DaoMensajeSQL();
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
    public void quitarUserEnUso(Usuario uTemp){
        Persistencia.quitarUserEnUso(uTemp);
    }
    public Usuario cogeUsuarioSesionAnt(){
        Usuario u =  Persistencia.cogerUserEnUso();
        if (u != null) u = daoUsuario.buscaUsuarioId(dao,u.getId());
        return u;
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
    public Producto getProductoAleatorio(ArrayList<Producto> productos){
        int pos = (int) (Math.random() * productos.size());
        return productos.get(pos);
    }
    //Borra un usuario
    public boolean borrarUsuario(){
        if (daoUsuario.eliminaUsuario(dao,usuario,daoProducto)) {
            Persistencia.quitarUserEnUso(usuario);
            usuario = null;
            return true;
        }
        return false;
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
    public boolean quitarProducto(Producto p){
       return daoProducto.borrarProducto(dao,p);
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
    public LocalDateTime mensajeUltimaSesion(Usuario uTemp){
        String ultimaConexion =  Persistencia.leeProperties("ultimaConexion" + uTemp.getId());
        if (ultimaConexion == null) return null;
        else {
            return LocalDateTime.of(Integer.parseInt(ultimaConexion.substring(0,4)),Integer.parseInt(ultimaConexion.substring(5,7)),Integer.parseInt(ultimaConexion.substring(8,10)),Integer.parseInt(ultimaConexion.substring(11,13)),Integer.parseInt(ultimaConexion.substring(14,16)),Integer.parseInt(ultimaConexion.substring(17,19)));
        }
    }
    public void cambiaUltimaConexion(Usuario uTemp){
        Persistencia.setProperties("ultimaConexion" + uTemp.getId(), String.valueOf(LocalDateTime.now()));
        Persistencia.ponerUserEnUso(uTemp);
    }
    public String leeConfiguracion(){
        return Persistencia.leeProperties();
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
    public ArrayList<Producto> consultaPersonalizada(String consulta){
        if (consulta.contains("ORDER BY") || consulta.contains("GROUP BY")){
            if (consulta.contains("GROUP BY")) return null;
            consulta = consulta.substring(0,consulta.indexOf("ORDER BY")) + " AND id_usuario != " + usuario.getId() + " " + consulta.substring(consulta.indexOf("ORDER BY"));
            return daoProducto.consultaPersonalizada(dao,consulta.concat(" AND id_usuario != " + usuario.getId()),usuario,daoTrato);
        } else return daoProducto.consultaPersonalizada(dao,consulta.concat(" AND id_usuario != " + usuario.getId()),usuario,daoTrato);
    }
    public int consultaPersonalizadaCount(String consulta){
        return daoProducto.consultaPersonalizadaCount(consulta,dao);
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
        return daoChat.getChats(dao,daoUsuario,usuario);
    }
    public Chat recargaChat(long id){
        return null;
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
        return daoChat.crearChat(dao,usuarios);
    }

}
