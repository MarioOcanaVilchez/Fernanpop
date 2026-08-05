package Dao;


import Modelos.Producto;
import Modelos.Trato;
import Modelos.Usuario;
import Utilidades.Utilidades;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class DaoTratoSQL {
    public boolean addtratoCompra(DaoManager dao, Usuario vendedor, long idProducto, int idOtroUser, double precio,DaoProductoSQL daoProducto,DaoUsuarioSQL daoUsuario){
        String sentencia = "insert into trato (id,tipo,idOtroUser,fecha,precio,idProducto,idUsuario,puntuacion) values (" + generaId(dao,daoProducto,daoUsuario) + ",'VentaPendiente','" + idOtroUser + "','" + Utilidades.pasarFechaBBDD(LocalDate.now()) + "'," + precio + "," + idProducto + "," + vendedor.getId() + ",-1)";
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            stmt.executeUpdate(sentencia);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean addtratoVenta(DaoManager dao, Trato trato,Usuario uTemp,DaoUsuarioSQL daoUsuario,DaoProductoSQL daoProducto){
        String sentencia = "update trato set tipo ='Vendido', fecha = '" + Utilidades.pasarFechaBBDD(LocalDate.now()) + "' where id = " + trato.getId();
        int idOtroUser = daoUsuario.buscaUsuarioMail(dao,trato.getEmailOtroUser()).getId();
        String sentencia2 = "insert into trato (id,tipo,idOtroUser,fecha,precio,idProducto,idUsuario,puntuacion) values (" + generaId(dao,daoProducto,daoUsuario) + ",'CompraNoCalificada','" + uTemp.getId() + "','" + Utilidades.pasarFechaBBDD(LocalDate.now()) + "'," + trato.getPrecio() + "," + trato.getProducto().getId() + "," + idOtroUser + ",-1)";
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            stmt.executeUpdate(sentencia);
            stmt.executeUpdate(sentencia2);
            dao.close();
            daoProducto.quitaProductoEnVenta(dao,trato.getProducto());
            quitarTratosMismoProducto(dao,trato);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean eliminaTrato(DaoManager dao,Trato trato){
        String sentencia = "delete from trato where id = " + trato.getId();
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            stmt.executeUpdate(sentencia);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean eliminaTrato(DaoManager dao,int id){
        String sentencia = "delete from trato where id = " +id;
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            stmt.executeUpdate(sentencia);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean quitarTratosMismoProducto(DaoManager dao,Trato trato){
        String sentencia = "delete from trato where idProducto = " + trato.getProducto().getId() + " and tipo ='VentaPendiente'";
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
    public Trato buscaTratoId(DaoManager dao, long id,DaoProductoSQL daoProducto,DaoUsuarioSQL daoUsuario) {
        String sentencia = "select * from trato where id = " + id;
        int idProducto;
        int idOtroUser;
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            if (rs.next()){
                Trato t = new Trato(rs.getInt("id"),rs.getString("tipo"),null,rs.getString("comentario"),Utilidades.pasarFechaLocaldate(rs.getString("fecha")),rs.getInt("puntuacion"),rs.getDouble("precio"),null);
                idProducto = rs.getInt("idProducto");
                idOtroUser = rs.getInt("idOtroUser");
                dao.close();
                t.setEmailOtroUser(daoUsuario.buscaUsuarioId(dao,idOtroUser).getEmail());
                t.setProducto(daoProducto.buscaProductoId(dao,idProducto));
                return t;
            }
            dao.close();
            return null;
        } catch (SQLException e) {
            return null;
        }
    }
    public int generaId(DaoManager dao,DaoProductoSQL daoProducto,DaoUsuarioSQL daoUsuario) {
        int id;
        do{
            id = (int) (Math.random() * 999998 + 1);
        }while(buscaTratoId(dao,id,daoProducto,daoUsuario) != null);
        return id;
    }
    public boolean actualizaTratos(DaoManager dao,Trato trato,String comentario, int puntuacion){
        String sentencia = "update trato set comentario = '" + comentario + "', puntuacion = " + puntuacion + " where idProducto = " + trato.getProducto().getId();
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            stmt.executeUpdate(sentencia);
            sentencia = "update trato set tipo = 'Comprado' where id= " + trato.getId();
            stmt.executeUpdate(sentencia);
            dao.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public ArrayList<Trato> historicoCompras(DaoManager dao,Usuario uTemp,DaoProductoSQL daoProducto,DaoUsuarioSQL daoUsuario){
        String sentencia = "select * from trato where idUsuario = " + uTemp.getId() + " and lower(tipo) like '%compra%'";
        ArrayList<Integer> idProductos = new ArrayList<>();
        ArrayList<Integer> idOtroUsers = new ArrayList<>();
        ArrayList<Trato> tratos = new ArrayList<>();
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            while (rs.next()){
                tratos.add(new Trato(rs.getInt("id"),rs.getString("tipo"),null,rs.getString("comentario"),Utilidades.pasarFechaLocaldate(rs.getString("fecha")),rs.getInt("puntuacion"),rs.getDouble("precio"),null));
                idProductos.add(rs.getInt("idProducto"));
                idOtroUsers.add(rs.getInt("idOtroUser"));
            }
            for (int i = 0; i < tratos.size(); i++) {
                tratos.get(i).setProducto(daoProducto.buscaProductoId(dao,idProductos.get(i)));
                tratos.get(i).setEmailOtroUser(daoUsuario.buscaUsuarioId(dao,idOtroUsers.get(i)).getEmail());
            }
            dao.close();
            return tratos;
        } catch (SQLException e) {
            return null;
        }
    }
    public ArrayList<Trato> historicoVentas(DaoManager dao,Usuario uTemp,DaoProductoSQL daoProducto,DaoUsuarioSQL daoUsuario){
        String sentencia = "select * from trato where idUsuario = " + uTemp.getId() + " and tipo = 'Vendido'";
        ArrayList<Integer> idProductos = new ArrayList<>();
        ArrayList<Trato> tratos = new ArrayList<>();
        ArrayList<Integer> idOtroUsers = new ArrayList<>();
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            while (rs.next()){
                tratos.add(new Trato(rs.getInt("id"),rs.getString("tipo"),null,rs.getString("comentario"),Utilidades.pasarFechaLocaldate(rs.getString("fecha")),rs.getInt("puntuacion"),rs.getDouble("precio"),null));
                idProductos.add(rs.getInt("idProducto"));
                idOtroUsers.add(rs.getInt("idOtroUser"));
            }
            for (int i = 0; i < tratos.size(); i++) {
                tratos.get(i).setProducto(daoProducto.buscaProductoId(dao,idProductos.get(i)));
                tratos.get(i).setEmailOtroUser(daoUsuario.buscaUsuarioId(dao,idOtroUsers.get(i)).getEmail());
            }
            dao.close();
            return tratos;
        } catch (SQLException e) {
            return null;
        }
    }
    public ArrayList<Trato> ventasPendientes(DaoManager dao,Usuario uTemp,DaoProductoSQL daoProducto,DaoUsuarioSQL daoUsuario){
        String sentencia = "select * from trato where idUsuario = " + uTemp.getId() + " and tipo = 'VentaPendiente'";
        ArrayList<Integer> idProductos = new ArrayList<>();
        ArrayList<Integer> idOtrosUsuarios = new ArrayList<>();
        ArrayList<Trato> tratos = new ArrayList<>();
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            while (rs.next()){
                tratos.add(new Trato(rs.getInt("id"),rs.getString("tipo"),null,rs.getString("comentario"),Utilidades.pasarFechaLocaldate(rs.getString("fecha")),rs.getInt("puntuacion"),rs.getDouble("precio"),null));
                idProductos.add(rs.getInt("idProducto"));
                idOtrosUsuarios.add(rs.getInt("idOtroUser"));
            }
            for (int i = 0; i < tratos.size(); i++) {
                tratos.get(i).setProducto(daoProducto.buscaProductoId(dao,idProductos.get(i)));
                tratos.get(i).setEmailOtroUser(daoUsuario.buscaUsuarioId(dao,idOtrosUsuarios.get(i)).getEmail());
            }
            dao.close();
            return tratos;
        } catch (SQLException e) {
            return null;
        }
    }
    public ArrayList<Trato> comprasPendientes(DaoManager dao,Usuario uTemp,DaoProductoSQL daoProducto,DaoUsuarioSQL daoUsuario){
        String sentencia = "select * from trato where idOtroUser = " + uTemp.getId() + " and tipo = 'VentaPendiente'";
        ArrayList<Integer> idProductos = new ArrayList<>();
        ArrayList<Integer> idOtrosUsuarios = new ArrayList<>();
        ArrayList<Trato> tratos = new ArrayList<>();
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            while (rs.next()){
                tratos.add(new Trato(rs.getInt("id"),rs.getString("tipo"),null,rs.getString("comentario"),Utilidades.pasarFechaLocaldate(rs.getString("fecha")),rs.getInt("puntuacion"),rs.getDouble("precio"),null));
                idProductos.add(rs.getInt("idProducto"));
                idOtrosUsuarios.add(rs.getInt("idUsuario"));
            }
            for (int i = 0; i < tratos.size(); i++) {
                tratos.get(i).setProducto(daoProducto.buscaProductoId(dao,idProductos.get(i)));
                tratos.get(i).setEmailOtroUser(daoUsuario.buscaUsuarioId(dao,idOtrosUsuarios.get(i)).getEmail());
            }
            dao.close();
            return tratos;
        } catch (SQLException e) {
            return null;
        }
    }
    public boolean existeTratoPendiente(DaoManager dao, Producto p,Usuario u){
        String sentencia = "select * from trato where idProducto = " + p.getId() + " and idOtroUser = " + u.getId();
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            if (rs.next()){
                dao.close();
                return true;
            }
            dao.close();
            return false;
        } catch (SQLException e) {
            return false;
        }
    }
    public ArrayList<Integer> valoracionesPendientes(DaoManager dao,Usuario uTemp){
        String sentencia = "select * from trato where idUsuario = " + uTemp.getId() + " and tipo = 'CompraNoCalificada'";
        ArrayList<Integer> tratos = new ArrayList<>();
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            while (rs.next()){
                tratos.add(rs.getInt("id"));
            }
            dao.close();
            return tratos;
        } catch (SQLException e) {
            return null;
        }
    }
    public int valoracionesPendientesNum(DaoManager dao,Usuario uTemp){
        String sentencia = "select count(*) from trato where idUsuario = " + uTemp.getId() + " and tipo = 'CompraNoCalificada'";
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            rs.next();
            int num = rs.getInt("count(*)");
            dao.close();
            return num;
        } catch (SQLException e) {
            return 0;
        }
    }
    //
    public int ventasPendientesConParametros(DaoManager dao,DaoProductoSQL daoProducto,Usuario uTemp,String textoBuscar , int precioMin , int precioMax){
        ArrayList<Integer> idsProductos = new ArrayList<>();
        String sentencia = "select * from trato where idOtroUser = " + uTemp.getId() + " and tipo = 'VentaPendiente'";
        try {
            dao.open();
            Statement stmp = dao.getConexion().createStatement();
            ResultSet rs = stmp.executeQuery(sentencia);
            while (rs.next()){
                idsProductos.add(rs.getInt("idProducto"));
            }
            dao.close();
            //De todos los tratos devolvemos solo los que coincidan con los parámetros de búsqueda
            return daoProducto.numVentasPendientesConParametros(dao,idsProductos,uTemp,textoBuscar,precioMin,precioMax);
        } catch (SQLException e) {
            return 0;
        }
    }
    //devuelve el id del producto el cual el usuario tenga una ventaPendiente
    public ArrayList<Integer> productosVentasPendientesConParametros(DaoManager dao,Usuario uTemp){
        ArrayList<Integer> idsProductos = new ArrayList<>();
        String sentencia = "select * from trato where idOtroUser = " + uTemp.getId() + " and tipo = 'VentaPendiente'";
        try {
            dao.open();
            Statement stmp = dao.getConexion().createStatement();
            ResultSet rs = stmp.executeQuery(sentencia);
            while (rs.next()){
                idsProductos.add(rs.getInt("idProducto"));
            }
            dao.close();
            return idsProductos;
        } catch (SQLException e) {
            return idsProductos;
        }
    }
}
