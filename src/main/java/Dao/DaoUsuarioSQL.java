package Dao;

import Modelos.Producto;
import Modelos.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DaoUsuarioSQL implements DaoUsuario{

    @Override
    public boolean insertaUsuario(DaoManager dao, String email,String nombre,String apel,String clave,int movil) {
        String sentencia = "insert into usuario values (" + generaId(dao) + ",'" + email + "','" + apel + "','" + clave + "'," + movil + "," + false + ",'" + nombre + "')";
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            stmt.executeUpdate(sentencia);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }


    @Override
    public boolean eliminaUsuario(DaoManager dao, Usuario usuario,DaoProductoSQL daoProducto) {
        String sentencia = "delete from usuario where id = " + usuario.getId();
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            stmt.executeUpdate(sentencia);
            sentencia = "insert into usuarioBorrado values (" + usuario.getId() + ",'" + usuario.getEmail() + "','" + usuario.getApel() + "','" + usuario.getClave() + "'," + usuario.getMovil() + "," + usuario.isAdmin() + ")";
            stmt.executeUpdate(sentencia);
            dao.close();
            return daoProducto.quitaProductosEnVenta(dao,usuario);
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean actualizaUsuario(DaoManager dao, Usuario usuario) {
        String sentencia = "update usuario set nombre ='" + usuario.getNombre() + "',apel ='" + usuario.getApel() + "', email ='" + usuario.getEmail() + "', movil = " + usuario.getMovil() + ", clave = '" + usuario.getClave() + "'" + " where id='" + usuario.getId() + "'";
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            stmt.executeUpdate(sentencia);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Usuario> getAllUsuarios(DaoManager dao){
        String sentencia = "select * from usuario";
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            while (rs.next()){
                usuarios.add(new Usuario(rs.getInt("id"),rs.getString("email"),rs.getString("nombre"),rs.getString("apel"),rs.getString("clave"),rs.getInt("movil"),rs.getBoolean("admin")));
            }
            dao.close();
            return usuarios;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Usuario buscaUsuarioId(DaoManager dao, int id) {
        String sentencia = "select * from usuario where id = " + id;
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            if (rs.next()){
                Usuario u = new Usuario(rs.getInt("id"),rs.getString("email"),rs.getString("nombre"),rs.getString("apel"),rs.getString("clave"),rs.getInt("movil"),rs.getBoolean("admin"));
                dao.close();
                return u;
            }
            dao.close();
            return null;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public int generaId(DaoManager dao) {
        int id;
        do{
            id = (int) (Math.random() * 999998 + 1);
        }while(buscaUsuarioId(dao,id) != null);
        return id;
    }

    @Override
    public Usuario login(DaoManager dao, String email, String clave) {
        String sentencia = "select * from usuario where email = ?";
        try {
            dao.open();
            PreparedStatement stmt = dao.getConexion().prepareStatement(sentencia);
            stmt.setString(1,email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                Usuario u = new Usuario(rs.getInt("id"),rs.getString("email"),rs.getString("nombre"),rs.getString("apel"),rs.getString("clave"),rs.getInt("movil"),rs.getBoolean("admin"));
                dao.close();
                if (BCrypt.checkpw(clave,u.getClave())) return u;
                else return null;
            }
            dao.close();
            return null;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Usuario buscaUsuarioMail(DaoManager dao, String email) {
        String sentencia = "select * from usuario where email = ?";
        try {
            dao.open();
            PreparedStatement stmt = dao.getConexion().prepareStatement(sentencia);
            stmt.setString(1,email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                Usuario u = new Usuario(rs.getInt("id"),rs.getString("email"),rs.getString("nombre"),rs.getString("apel"),rs.getInt("movil"));
                dao.close();
                return u;
            }
            dao.close();
            return null;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Usuario buscaUsuarioPorProducto(DaoManager dao, Producto p,DaoProductoSQL daoProducto) {
        int id = daoProducto.buscaUsuarioPorProducto(dao,p);
        return buscaUsuarioId(dao,id);
    }


}
