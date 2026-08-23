package Dao;

import Modelos.Producto;
import Modelos.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DaoProductoSQL {
    final int LIMITE_PAGINA = 12;
    public boolean insertarProducto(DaoManager dao, Usuario usuario, String titulo,String descripcion,String estado,double precio){
        String sentencia = "insert into producto values (" + generaId(dao) + ",'" + titulo + "','" + descripcion + "','" + estado + "'," +precio + "," + usuario.getId() + "," + true + "," + null + ")";
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            stmt.executeUpdate(sentencia);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean insertarProducto(DaoManager dao, Usuario usuario, String titulo,String descripcion,String estado,double precio,long id,String nombreImagen){
        String sentencia = "insert into producto values (" + id + ",'" + titulo + "','" + descripcion + "','" + estado + "'," +precio + "," + usuario.getId() + "," + true + ",'" + nombreImagen + "')";
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            stmt.executeUpdate(sentencia);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean actualizaProducto(DaoManager dao, String titulo,String descripcion,String estado,double precio,long id){
        String sentencia = "update producto set titulo='" + titulo + "', descripcion='" + descripcion + "', estado='" + estado + "', precio=" + precio + " where id=" + id;
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            stmt.executeUpdate(sentencia);
            dao.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean actualizaProducto(DaoManager dao, String titulo,String descripcion,String estado,double precio,String nombreImagen,long id){
        String sentencia = "update producto set titulo='" + titulo + "', descripcion='" + descripcion + "', estado='" + estado + "', precio=" + precio + ", nombreImagen='" + nombreImagen + "' where id=" + id;
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            stmt.executeUpdate(sentencia);
            dao.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean borrarProducto(DaoManager dao,Producto p){
        String sentencia = "delete from producto where id = " + p.getId();
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            stmt.executeUpdate(sentencia);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    //Metodo que debe englobar todas las posibilidades de busqueda para usuario no registrado
    public ArrayList<Producto> getPaginaProductos(DaoManager dao, ArrayList<Producto> productosActuales,String textoBuscar,String orden,int precioMin,int precioMax){
        ArrayList<Producto> productos = new ArrayList<>();
        String sentencia = determinarSentenciaUsuarioNoRegistrado(productosActuales,textoBuscar,orden,precioMin,precioMax);
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            while (rs.next()){
                productos.add(new Producto(rs.getLong("id"),rs.getString("titulo"),rs.getString("descripcion"),rs.getDouble("precio"),rs.getString("estado"),rs.getString("nombreImagen")));
            }
            dao.close();
            return productos;
        } catch (SQLException e) {
            return null;
        }
    }
    //Metodo que debe englobar todas las posibilidades de busqueda para un usuario registrado
    public ArrayList<Producto> getPaginaProductos(DaoManager dao,Usuario usuario,DaoTratoSQL daoTrato,ArrayList<Producto> productosActuales,ArrayList<Long> idProductosSolicitados,String textoBuscar,String orden,int precioMin,int precioMax){
        ArrayList<Producto> productos = new ArrayList<>();
        String sentencia = determinarSentenciaUsuarioRegistrado(usuario,productosActuales,idProductosSolicitados,textoBuscar,orden,precioMin,precioMax,LIMITE_PAGINA);
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            while (rs.next()){
                productos.add(new Producto(rs.getLong("id"),rs.getString("titulo"),rs.getString("descripcion"),rs.getDouble("precio"),rs.getString("estado"),rs.getString("nombreImagen")));
            }
            dao.close();
            return productos;
        } catch (SQLException e) {
            return null;
        }
    }
    public String determinarSentenciaUsuarioRegistrado(Usuario usuario,ArrayList<Producto> productosActuales,ArrayList<Long> idProductosSolicitados,String textoBuscar,String orden,int precioMin,int precioMax,int limit){
        String sentencia = "";
        if (textoBuscar == null){
            switch (orden){
                case "aleatorio":
                    if (productosActuales != null) {
                        sentencia = "select * from producto where enVenta = true and precio between " + precioMin + " and " + precioMax +" and id not in(";
                        for (Producto p : productosActuales) {
                            sentencia += p.getId() + ",";
                        }
                        for (long idProducto : idProductosSolicitados){
                            sentencia += idProducto + ",";
                        }
                        sentencia = sentencia.substring(0, sentencia.length() - 1);
                        sentencia += ") and id_usuario != " + usuario.getId() + " order by rand() limit " + limit;
                    } else {
                        sentencia = "select * from producto where enVenta = true and precio between " + precioMin + " and " + precioMax + " and id_usuario != " + usuario.getId();
                        if (!idProductosSolicitados.isEmpty()) {
                            sentencia += " and id not in (";
                            for (long idProducto : idProductosSolicitados) {
                                sentencia += idProducto + ",";
                            }
                            sentencia = sentencia.substring(0, sentencia.length() - 1) + ")";
                        }
                        sentencia += " order by rand() limit " + limit;
                    }
                    break;
                case "menorMayor":
                    if (productosActuales != null) {
                        sentencia = "select * from producto where enVenta = true and precio between " + precioMin + " and " + precioMax +" and id not in(";
                        for (Producto p : productosActuales) {
                            sentencia += p.getId() + ",";
                        }
                        for (long idProducto : idProductosSolicitados){
                            sentencia += idProducto + ",";
                        }
                        sentencia = sentencia.substring(0, sentencia.length() - 1);
                        sentencia += ") and id_usuario != " + usuario.getId() + " order by precio asc limit " + limit;
                    } else {
                        sentencia = "select * from producto where enVenta = true and precio between " + precioMin + " and " + precioMax + " and id_usuario != " + usuario.getId();
                        if (!idProductosSolicitados.isEmpty()) {
                            sentencia += " and id not in (";
                            for (long idProducto : idProductosSolicitados) {
                                sentencia += idProducto + ",";
                            }
                            sentencia = sentencia.substring(0, sentencia.length() - 1) + ")";
                        }
                        sentencia += " order by precio asc limit " + limit;
                    }
                    break;
                case "mayorMenor":
                    if (productosActuales != null) {
                        sentencia = "select * from producto where enVenta = true and precio between " + precioMin + " and " + precioMax +" and id not in(";
                        for (Producto p : productosActuales) {
                            sentencia += p.getId() + ",";
                        }
                        for (long idProducto : idProductosSolicitados){
                            sentencia += idProducto + ",";
                        }
                        sentencia = sentencia.substring(0, sentencia.length() - 1);
                        sentencia += ") and id_usuario != " + usuario.getId() + " order by precio desc limit " + limit;
                    } else {
                        sentencia = "select * from producto where enVenta = true and precio between " + precioMin + " and " + precioMax + " and id_usuario != " + usuario.getId();
                        if (!idProductosSolicitados.isEmpty()) {
                            sentencia += " and id not in (";
                            for (long idProducto : idProductosSolicitados) {
                                sentencia += idProducto + ",";
                            }
                            sentencia = sentencia.substring(0, sentencia.length() - 1) + ")";
                        }
                        sentencia += " order by precio desc limit " + limit;
                    }
                    break;
            }
        } else {
            switch (orden){
                case "aleatorio":
                    if (productosActuales != null) {
                        sentencia = "select * from producto where enVenta = true and precio between " + precioMin + " and " + precioMax +" and id not in(";
                        for (Producto p : productosActuales) {
                            sentencia += p.getId() + ",";
                        }
                        for (long idProducto : idProductosSolicitados){
                            sentencia += idProducto + ",";
                        }
                        sentencia = sentencia.substring(0, sentencia.length() - 1);
                        sentencia += ") and id_usuario != " + usuario.getId() + " and (lower(titulo) like '%" + textoBuscar + "%' or lower(descripcion) like '%" + textoBuscar + "%') order by rand() limit " + limit;
                    } else {
                        sentencia = "select * from producto where enVenta = true and precio between " + precioMin + " and " + precioMax + " and (lower(titulo) like '%" + textoBuscar + "%' or lower(descripcion) like '%" + textoBuscar + "%') and id_usuario != " + usuario.getId();
                        if (!idProductosSolicitados.isEmpty()) {
                            sentencia += " and id not in (";
                            for (long idProducto : idProductosSolicitados) {
                                sentencia += idProducto + ",";
                            }
                            sentencia = sentencia.substring(0, sentencia.length() - 1) + ")";
                        }
                        sentencia += " order by rand() limit " + limit;
                    }
                    break;
                case "menorMayor":
                    if (productosActuales != null) {
                        sentencia = "select * from producto where enVenta = true and precio between " + precioMin + " and " + precioMax +" and id not in(";
                        for (Producto p : productosActuales) {
                            sentencia += p.getId() + ",";
                        }
                        for (long idProducto : idProductosSolicitados){
                            sentencia += idProducto + ",";
                        }
                        sentencia = sentencia.substring(0, sentencia.length() - 1);
                        sentencia += ") and id_usuario != " + usuario.getId() + " and (lower(titulo) like '%" + textoBuscar + "%' or lower(descripcion) like '%" + textoBuscar + "%') order by precio asc limit " + limit;
                    } else {
                        sentencia = "select * from producto where enVenta = true and precio between " + precioMin + " and " + precioMax + " and (lower(titulo) like '%" + textoBuscar + "%' or lower(descripcion) like '%" + textoBuscar + "%') and id_usuario != " + usuario.getId();
                        if (!idProductosSolicitados.isEmpty()) {
                            sentencia += " and id not in (";
                            for (long idProducto : idProductosSolicitados) {
                                sentencia += idProducto + ",";
                            }
                            sentencia = sentencia.substring(0, sentencia.length() - 1) + ")";
                        }
                        sentencia += " order by precio asc limit " + limit;
                    }
                    break;
                case "mayorMenor":
                    if (productosActuales != null) {
                        sentencia = "select * from producto where enVenta = true and precio between " + precioMin + " and " + precioMax +" and id not in(";
                        for (Producto p : productosActuales) {
                            sentencia += p.getId() + ",";
                        }
                        for (long idProducto : idProductosSolicitados){
                            sentencia += idProducto + ",";
                        }
                        sentencia = sentencia.substring(0, sentencia.length() - 1);
                        sentencia += ") and id_usuario != " + usuario.getId() + " and (lower(titulo) like '%" + textoBuscar + "%' or lower(descripcion) like '%" + textoBuscar + "%') order by precio desc limit " + limit;
                    } else {
                        sentencia = "select * from producto where enVenta = true and precio between " + precioMin + " and " + precioMax + " and (lower(titulo) like '%" + textoBuscar + "%' or lower(descripcion) like '%" + textoBuscar + "%') and id_usuario != " + usuario.getId();
                        if (!idProductosSolicitados.isEmpty()) {
                            sentencia += " and id not in (";
                            for (long idProducto : idProductosSolicitados) {
                                sentencia += idProducto + ",";
                            }
                            sentencia = sentencia.substring(0, sentencia.length() - 1) + ")";
                        }
                        sentencia += " order by precio desc limit " + limit;
                    }
                    break;
            }
        }
        return sentencia;
    }
    public String determinarSentenciaUsuarioNoRegistrado(ArrayList<Producto> productosActuales,String textoBuscar,String orden,int precioMin,int precioMax){
        String sentencia = "";
        if (textoBuscar == null){
            switch (orden){
                case "aleatorio":
                    if (productosActuales != null) {
                        sentencia = "select * from producto where enVenta = true and precio between " + precioMin + " and " + precioMax +" and id not in(";
                        for (Producto p : productosActuales) {
                            sentencia += p.getId() + ",";
                        }
                        sentencia = sentencia.substring(0, sentencia.length() - 1);
                        sentencia += ") order by rand() limit " + LIMITE_PAGINA;
                    } else sentencia = "select * from producto where enVenta = true and precio between " + precioMin + " and " + precioMax +" order by rand() limit " + LIMITE_PAGINA;
                    break;
                case "menorMayor":
                    if (productosActuales != null) {
                        sentencia = "select * from producto where enVenta = true and precio between " + precioMin + " and " + precioMax +" and id not in(";
                        for (Producto p : productosActuales) {
                            sentencia += p.getId() + ",";
                        }
                        sentencia = sentencia.substring(0, sentencia.length() - 1);
                        sentencia += ") order by precio asc limit " + LIMITE_PAGINA;
                    } else sentencia = "select * from producto where enVenta = true and precio between " + precioMin + " and " + precioMax +" order by precio asc limit " + LIMITE_PAGINA;
                    break;
                case "mayorMenor":
                    if (productosActuales != null) {
                        sentencia = "select * from producto where enVenta = true and precio between " + precioMin + " and " + precioMax +" and id not in(";
                        for (Producto p : productosActuales) {
                            sentencia += p.getId() + ",";
                        }
                        sentencia = sentencia.substring(0, sentencia.length() - 1);
                        sentencia += ") order by precio desc limit " + LIMITE_PAGINA;
                    } else sentencia = "select * from producto where enVenta = true and precio between " + precioMin + " and " + precioMax +" order by precio desc limit " + LIMITE_PAGINA;
                    break;
            }
        } else {
            switch (orden){
                case "aleatorio":
                    if (productosActuales != null) {
                        sentencia = "select * from producto where enVenta = true and precio between " + precioMin + " and " + precioMax +" and id not in(";
                        for (Producto p : productosActuales) {
                            sentencia += p.getId() + ",";
                        }

                        sentencia = sentencia.substring(0, sentencia.length() - 1);
                        sentencia += ") and (lower(titulo) like '%" + textoBuscar + "%' or lower(descripcion) like '%" + textoBuscar + "%') order by rand() limit " + LIMITE_PAGINA;
                    } else sentencia = "select * from producto where enVenta = true and precio between " + precioMin + " and " + precioMax +" and (lower(titulo) like '%" + textoBuscar + "%' or lower(descripcion) like '%" + textoBuscar + "%') order by rand() limit " + LIMITE_PAGINA;
                    break;
                case "menorMayor":
                    if (productosActuales != null) {
                        sentencia = "select * from producto where enVenta = true and precio between " + precioMin + " and " + precioMax +" and id not in(";
                        for (Producto p : productosActuales) {
                            sentencia += p.getId() + ",";
                        }

                        sentencia = sentencia.substring(0, sentencia.length() - 1);
                        sentencia += ") and (lower(titulo) like '%" + textoBuscar + "%' or lower(descripcion) like '%" + textoBuscar + "%') order by precio asc limit " + LIMITE_PAGINA;
                    } else sentencia = "select * from producto where enVenta = true and precio between " + precioMin + " and " + precioMax +" and (lower(titulo) like '%" + textoBuscar + "%' or lower(descripcion) like '%" + textoBuscar + "%') order by precio asc limit " + LIMITE_PAGINA;
                    break;
                case "mayorMenor":
                    if (productosActuales != null) {
                        sentencia = "select * from producto where enVenta = true and precio between " + precioMin + " and " + precioMax +" and id not in(";
                        for (Producto p : productosActuales) {
                            sentencia += p.getId() + ",";
                        }

                        sentencia = sentencia.substring(0, sentencia.length() - 1);
                        sentencia += ") and (lower(titulo) like '%" + textoBuscar + "%' or lower(descripcion) like '%" + textoBuscar + "%') order by precio desc limit " + LIMITE_PAGINA;
                    } else sentencia = "select * from producto where enVenta = true and precio between " + precioMin + " and " + precioMax +" and (lower(titulo) like '%" + textoBuscar + "%' or lower(descripcion) like '%" + textoBuscar + "%') order by precio desc limit " + LIMITE_PAGINA;
                    break;
            }
        }
        return sentencia;
    }
    //De todos los productos en ventaPendiente seleccionamos los que coincidan con los parámetros de búsqueda
    public int numVentasPendientesConParametros(DaoManager dao,ArrayList<Integer> idsProductos,Usuario uTemp,String textoBuscar , int precioMin , int precioMax){
        if (idsProductos.isEmpty()) return 0;
        int num;
        String sentencia = "select count(*) from producto where id in (";
        for (int id : idsProductos){
            sentencia += id + ",";
        }
        sentencia = sentencia.substring(0,sentencia.length() - 1) + ") and ";
        sentencia += "enVenta = true and precio between " + precioMin + " and " + precioMax + " ";
        if (textoBuscar != null && !textoBuscar.isEmpty()) sentencia += "and (lower(titulo) like '%" + textoBuscar + "%' or lower(descripcion) like '%" + textoBuscar + "%')";
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            rs.next();
            num = rs.getInt("count(*)");
            dao.close();
            return num;
        } catch (SQLException e) {
            return 0;
        }

    }
    public long generaId(DaoManager dao) {
        long id;
        do{
            id = (long) (Math.random() * 999999998 + 1);
        }while(buscaProductoId(dao,id) != null);
        return id;
    }
    public Producto buscaProductoId(DaoManager dao, long id) {
        String sentencia = "select * from producto where id = " + id;
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            if (rs.next()){
                Producto p = new Producto(rs.getLong("id"),rs.getString("titulo"),rs.getString("descripcion"),rs.getDouble("precio"),rs.getString("estado"),rs.getString("nombreImagen"));
                dao.close();
                return p;
            }
            dao.close();
            return null;
        } catch (SQLException e) {
            return null;
        }
    }
    public ArrayList<Producto> buscaProductoIdUser(DaoManager dao, int id) {
        String sentencia = "select * from producto where id_usuario = " + id + " and enVenta = true";
        ArrayList<Producto> productos = new ArrayList<>();
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            while (rs.next()){
                productos.add(new Producto(rs.getLong("id"),rs.getString("titulo"),rs.getString("descripcion"),rs.getDouble("precio"),rs.getString("estado"),rs.getString("nombreImagen")));
            }
            dao.close();
            return productos;
        } catch (SQLException e) {
            return null;
        }
    }
    public ArrayList<Producto> buscaProductoPorTexto(DaoManager dao,String texto,Usuario usuario,DaoTratoSQL daoTrato){
        texto = texto.toLowerCase();
        ArrayList<Producto> productos = new ArrayList<>();
        ArrayList<Producto> productosValidos = new ArrayList<>();
        String sentencia = "select * from producto where lower(titulo) like '%" + texto + "%' or lower(descripcion) like '%" + texto + "%' and id_usuario != " + usuario.getId() + "order by rand() limit " + LIMITE_PAGINA;
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            while (rs.next()){
                productos.add(new Producto(rs.getLong("id"),rs.getString("titulo"),rs.getString("descripcion"),rs.getDouble("precio"),rs.getString("estado"),rs.getString("nombreImagen")));
            }
            dao.close();
            for (Producto p : productos){
                if (!daoTrato.existeTratoPendiente(dao,p,usuario)) productosValidos.add(p);
            }
            return productosValidos;
        } catch (SQLException e) {
            return null;
        }
    }
    //Metodo que debe devolver el numero de productos para cualquier metodo de busqueda
    public int totalProductosEnVenta(DaoManager dao,DaoTratoSQL daoTrato,DaoProductoSQL daoProducto,Usuario usuario,String textoBuscar , int precioMin , int precioMax){
        String sentencia;
        if (textoBuscar == null) sentencia = "select count(*) from producto where enVenta = true and id_usuario != " + usuario.getId() + " and precio between " + precioMin + " and " + precioMax;
        else sentencia = "select count(*) from producto where enVenta = true and (lower(titulo) like '%" + textoBuscar + "%' or lower(descripcion) like '%" + textoBuscar + "%') and  id_usuario != " + usuario.getId() + " and precio between " + precioMin + " and " + precioMax;
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            rs.next();
            int num = rs.getInt("count(*)");
            dao.close();
            return num - daoTrato.ventasPendientesConParametros(dao,daoProducto,usuario,textoBuscar,precioMin,precioMax);
        } catch (SQLException e) {
            return -1;
        }
    }
    //Metodo que debe devolver el numero de productos para cualquier metodo de busqueda
    public int totalProductosEnVenta(DaoManager dao,String textoBuscar , int precioMin , int precioMax){
        String sentencia;
        if (textoBuscar == null) sentencia = "select count(*) from producto where enVenta = true and precio between " + precioMin + " and " + precioMax;
        else sentencia = "select count(*) from producto where enVenta = true and (lower(titulo) like '%" + textoBuscar + "%' or lower(descripcion) like '%" + textoBuscar + "%') and precio between " + precioMin + " and " + precioMax;
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            rs.next();
            int num = rs.getInt("count(*)");
            dao.close();
            return num;
        } catch (SQLException e) {
            return -1;
        }
    }
    public int buscaUsuarioPorProducto(DaoManager dao,Producto p){
        String sentencia = "select * from producto where id = " + p.getId();
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            if (rs.next()){
                int id = rs.getInt("id_usuario");
                dao.close();
                return id;
            }
            dao.close();
            return -1;
        } catch (SQLException e) {
            return -1;
        }
    }
    public ArrayList<Producto> consultaPersonalizada(DaoManager dao,String consulta,Usuario usuario,DaoTratoSQL daoTrato){
        ArrayList<Producto> productos = new ArrayList<>();
        ArrayList<Producto> productosValidos = new ArrayList<>();
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(consulta);
            while (rs.next()){
                productos.add(new Producto(rs.getLong("id"),rs.getString("titulo"),rs.getString("descripcion"),rs.getDouble("precio"),rs.getString("estado"),rs.getString("nombreImagen")));
            }
            dao.close();
            for (Producto p : productos){
                if (!daoTrato.existeTratoPendiente(dao,p,usuario)) productosValidos.add(p);
            }
            return productosValidos;

        } catch (SQLException e) {
            return null;
        }

    }
    public boolean quitaProductosEnVenta(DaoManager dao,Usuario usuario){
        String sentencia = "delete from producto where id_usuario = " + usuario.getId() + " and enVenta = true";
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            stmt.executeUpdate(sentencia);
            dao.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean quitaProductoEnVenta(DaoManager dao,Producto p){
        String sentencia = "update producto set enVenta = false where id = " + p.getId();
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            stmt.executeUpdate(sentencia);
            dao.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public int consultaPersonalizadaCount(String consulta,DaoManager dao){
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(consulta);
            rs.next();
            int num = rs.getInt("COUNT(*)");
            dao.close();
            return num;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean estaEnVenta(DaoManager dao,Producto producto){
        String sentencia = "select enVenta from producto where id=" + producto.getId();
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            if (rs.next()){
                boolean enVenta = rs.getBoolean("enVenta");
                dao.close();
                return enVenta;
            }
            dao.close();
            return false;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean esTuProducto(DaoManager dao,Producto producto,Usuario uTemp){
        String sentencia = "select id_usuario from producto where id=" + producto.getId();
        int idDuenioProducto = 0;
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            if (rs.next()) idDuenioProducto = rs.getInt("id_usuario");
            dao.close();
            return idDuenioProducto == uTemp.getId();
        } catch (SQLException e) {
            return false;
        }
    }
    public long getProductoAleatorio(DaoManager dao,DaoTratoSQL daoTrato,Usuario uTemp){
        String sentencia;
        if (uTemp == null){
            sentencia = "select * from producto where enVenta=true order by rand() limit 1";
            try {
                dao.open();
                Statement stmt = dao.getConexion().createStatement();
                ResultSet rs = stmt.executeQuery(sentencia);
                rs.next();
                return rs.getLong("id");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            ArrayList<Long> idsProductosSolicitados = daoTrato.productosVentasPendientesConParametros(dao,uTemp);
            sentencia = "select * from producto where enVenta=true and id_usuario!=" + uTemp.getId();
            if (idsProductosSolicitados != null && !idsProductosSolicitados.isEmpty()) {
                sentencia += " and id not in(";
                for (long id : idsProductosSolicitados) {
                    sentencia += id + ",";
                }
                sentencia = sentencia.substring(0,sentencia.length() - 1) +  ")";
            }
            sentencia += " order by rand()";
            try {
                dao.open();
                Statement stmt = dao.getConexion().createStatement();
                ResultSet rs = stmt.executeQuery(sentencia);
                rs.next();
                return rs.getLong("id");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public int cuentaProductosPeticionIA(DaoManager dao,String peticion){
        peticion = peticion.replace("*","count(*)");
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(peticion);
            rs.next();
            int numProductos = rs.getInt("count(*)");
            dao.close();
            return numProductos;
        } catch (SQLException e) {
            return -1;
        }
    }
    public ArrayList<Producto> getPaginaProductosPeticionIA(DaoManager dao,Usuario uTemp,String peticion,ArrayList<Producto> productosActuales,ArrayList<Long> productosSolicitados){
        peticion = generaPeticion(uTemp,peticion,productosActuales,productosSolicitados, LIMITE_PAGINA);
        ArrayList<Producto> productos = new ArrayList<>();
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(peticion);
            while (rs.next()){
                productos.add(new Producto(rs.getLong("id"),rs.getString("titulo"),rs.getString("descripcion"),rs.getDouble("precio"),rs.getString("estado"),rs.getString("nombreImagen")));
            }
            dao.close();
            return productos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public String generaPeticion(Usuario uTemp,String peticion,ArrayList<Producto> productosActuales,ArrayList<Long> productosSolicitados,int limit){
        peticion += " and id_usuario != " + uTemp.getId();
        if (productosActuales != null && !productosActuales.isEmpty()){
            peticion += " and id not in(";
            for (Producto p : productosActuales){
                peticion += p.getId() + ",";
            }
            peticion = peticion.substring(0,peticion.length() - 1) + ")";
        }
        if (productosSolicitados != null && !productosSolicitados.isEmpty()){
            peticion += " and id not in(";
            for (long id : productosSolicitados){
                peticion += id + ",";
            }
            peticion = peticion.substring(0,peticion.length() - 1) + ")";
        }
        peticion += " order by rand() limit " + limit;
        return peticion;
    }
    public Producto getProducto(DaoManager dao,Usuario uTemp,ArrayList<Producto> productosActuales,String textoBuscar,String orden,int precioMin,int precioMax,ArrayList<Long> idProductosSolicitados){
        Producto producto = null;
        String sentencia = determinarSentenciaUsuarioRegistrado(uTemp,productosActuales,idProductosSolicitados,textoBuscar,orden,precioMin,precioMax,1);
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            if (rs.next()){
                producto = new Producto(rs.getLong("id"),rs.getString("titulo"),rs.getString("descripcion"),rs.getDouble("precio"),rs.getString("estado"),rs.getString("nombreImagen"));
            }
            dao.close();
            return producto;
        } catch (SQLException e) {
            return null;
        }
    }
    public Producto getProducto(DaoManager dao,Usuario uTemp,ArrayList<Producto> productosActuales,ArrayList<Long> idProductosSolicitados,String peticion){
        Producto producto = null;
        peticion = completaPeticionIA(idProductosSolicitados,peticion,productosActuales,uTemp);
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(peticion);
            if (rs.next()){
                producto = new Producto(rs.getLong("id"),rs.getString("titulo"),rs.getString("descripcion"),rs.getDouble("precio"),rs.getString("estado"),rs.getString("nombreImagen"));
            }
            dao.close();
            return producto;
        } catch (SQLException e) {
            return null;
        }
    }
    public String completaPeticionIA(ArrayList<Long> idProductosSolicitados,String peticion,ArrayList<Producto> productosActuales,Usuario usuario) {
        peticion += " and id_usuario !=" + usuario.getId();
        if (!idProductosSolicitados.isEmpty()) {
            peticion += " and id not in(";
            for (long id : idProductosSolicitados){
                peticion += id + ",";
            }
            peticion = peticion.substring(0,peticion.length() - 1) + ")";
        }
        if (productosActuales != null && !productosActuales.isEmpty()){
            peticion += " and id not in(";
            for (Producto p : productosActuales){
                peticion += p.getId() + ",";
            }
            peticion = peticion.substring(0,peticion.length() - 1) + ")";
        }
        peticion += " order by rand()";
        return peticion;
    }
}
