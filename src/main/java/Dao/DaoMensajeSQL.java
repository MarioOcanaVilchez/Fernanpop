package Dao;

import Modelos.Mensaje;
import Modelos.Usuario;
import Utilidades.Utilidades;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DaoMensajeSQL {
    public boolean insertaMensaje(DaoManager dao, String contenido, long idChat,Usuario[] usuarios, LocalDateTime fecha,int idUserEnvia){
        long idMensaje = generaId(dao,idChat);
        String sentencia = "insert into mensajesChat values (" + idMensaje + ",'" + contenido + "'," + idChat + "," + idUserEnvia + ",'" + Utilidades.pasarFechaHoraBBDD(fecha) + "')";
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            stmt.executeUpdate(sentencia);
            for (Usuario u: usuarios){
                sentencia = "insert into mensajesUser values (" + idMensaje + ",'" + contenido + "'," + idChat + "," + idUserEnvia + ",'" + Utilidades.pasarFechaHoraBBDD(fecha) + "'," + u.getId() + "," + ((idUserEnvia == u.getId())) + ",false)";
                stmt.executeUpdate(sentencia);
            }
            dao.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean insertaMensajeDeBloqueado(DaoManager dao, String contenido, long idChat, LocalDateTime fecha,int idUserEnvia){
        long idMensaje = generaId(dao,idChat);
        String sentencia = "insert into mensajesChat values (" + idMensaje + ",'" + contenido + "'," + idChat + "," + idUserEnvia + ",'" + Utilidades.pasarFechaHoraBBDD(fecha) + "')";
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            stmt.executeUpdate(sentencia);
            sentencia = "insert into mensajesUser values (" + idMensaje + ",'" + contenido + "'," + idChat + "," + idUserEnvia + ",'" + Utilidades.pasarFechaHoraBBDD(fecha) + "'," + idUserEnvia + ",true,false)";
            stmt.executeUpdate(sentencia);

            dao.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public long generaId(DaoManager dao,long idChat){
        long id;
        do{
            id = (long) (Math.random() * (Long.MAX_VALUE - 1) + 1);
        } while (existeMensaje(dao,idChat,id));
        return id;
    }
    public boolean existeMensaje(DaoManager dao,long idChat,long num){
        String sentenca = "select * from mensajesChat where idChat=" + idChat + " and id=" + num;
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentenca);
            if (rs.next()){
                dao.close();
                return true;
            }
            dao.close();
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Mensaje buscaMensaje(DaoManager dao,long idChat,long id,Usuario uTemp){
        String sentenca = "select * from mensajesChat where idChat=" + idChat + " and id=" + id;
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentenca);
            if (rs.next()){
                Mensaje mensaje = new Mensaje(id,rs.getString("mensaje"),uTemp,Utilidades.pasarFechaHoraLocaldate(rs.getString("fecha")),false);
                dao.close();
                return mensaje;
            }
            dao.close();
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Mensaje buscaMensajeChatUsuario(DaoManager dao,long idChat,long id,Usuario uTemp){
        String sentenca = "select * from mensajesUser where idChat=" + idChat + " and id=" + id + " and idUserRecibe=" + uTemp.getId();
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentenca);
            if (rs.next()){
                Mensaje mensaje = new Mensaje(id,rs.getString("mensaje"),uTemp,Utilidades.pasarFechaHoraLocaldate(rs.getString("fecha")),false);
                dao.close();
                return mensaje;
            }
            dao.close();
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean actualizaMensaje(DaoManager dao,long idChat,long idMensaje,String nuevoMensaje,Usuario uTemp){
        Mensaje mensaje = buscaMensaje(dao,idChat,idMensaje,uTemp);
        if (mensaje == null) return false;
        String sentencia = "update mensajesUser set mensaje='" + nuevoMensaje + "' where idChat=" + idChat + " and id=" + idMensaje;
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            stmt.executeUpdate(sentencia);
            nuevoMensaje = mensaje.getContenido() + " fue editado a " + nuevoMensaje;
            sentencia = "update mensajesChat set mensaje='" + nuevoMensaje + "' where idChat=" + idChat + " and id=" + idMensaje;
            stmt.executeUpdate(sentencia);
            dao.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Mensaje> cargaMensajes(DaoManager dao,DaoUsuarioSQL daoUsuario,Usuario uTemp,long idChat){
        ArrayList<Mensaje> mensajes = new ArrayList<>();
        LocalDateTime fecha;
        String sentencia = "select * from mensajesUser where idChat=" + idChat + " and idUserRecibe=" + uTemp.getId() + " order by fecha asc";
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            while (rs.next()){
                if (rs.getString("fecha") == null) fecha = null;
                else fecha = Utilidades.pasarFechaHoraLocaldate(rs.getString("fecha"));
                mensajes.add(new Mensaje(rs.getLong("id"),rs.getString("mensaje"),new Usuario(rs.getInt("idUserEnvia")),fecha,rs.getBoolean("eliminado")));
            }
            dao.close();
            mensajes = daoUsuario.determinaDuenioMensajes(dao,mensajes);
            return mensajes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean enviaPrimerMensaje(DaoManager dao,String mensaje,long idChat,Usuario[] usuarios){
        long idMensaje = generaId(dao,idChat);
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            for (Usuario u : usuarios){
                String sentencia = "insert into mensajesUser values (" + idMensaje + ",'" + mensaje + "'," + idChat + ",-1,'" + Utilidades.pasarFechaHoraBBDD(LocalDateTime.now()) + "'," + u.getId() + ",false,false)";
                stmt.executeUpdate(sentencia);
            }
            dao.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public int determinarMensajesSinLeer(DaoManager dao, long idChat,Usuario usuario){
        String sentencia = "select count(*) from mensajesUser where idChat=" + idChat + " and idUserRecibe=" + usuario.getId() + " and leido=false";
        int mensajesSinLeer;
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            rs.next();
            mensajesSinLeer = rs.getInt("count(*)");
            dao.close();
            return mensajesSinLeer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int determinarTotalMensajesSinLeer(DaoManager dao,Usuario usuario){
        String sentencia = "select count(*) from mensajesUser where idUserRecibe=" + usuario.getId() + " and leido=false";
        int mensajesSinLeer;
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            rs.next();
            mensajesSinLeer = rs.getInt("count(*)");
            dao.close();
            return mensajesSinLeer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean leeMensajesChat(DaoManager dao,long idChat,Usuario usuario){
        String sentencia = "update mensajesUser set leido=true where idChat=" + idChat + " and idUserRecibe=" + usuario.getId();
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
    public boolean eliminaMensaje(DaoManager dao,long idChat,long idMensaje,Usuario uTemp){
        String sentencia = "delete from mensajesUser where id=" + idMensaje + " and idChat=" + idChat + " and idUserRecibe=" + uTemp.getId();
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
    public boolean eliminaContenidoMensaje(DaoManager dao,long idChat,long idMensaje){
        String sentencia = "update mensajesUser set mensaje='⊘ Mensaje elimminado',eliminado=true where id=" + idMensaje + " and idChat=" + idChat;
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
    public boolean enviaMensajeBloqueo(DaoManager dao,long idChat,int idUserBloquea,int idUserBloqueado){
        long id = generaId(dao,idChat);
        String mensaje = idUserBloquea + " bloqueo a " + idUserBloqueado;
        String sentencia = "insert into mensajesChat values(" + id + ",'" + mensaje + "'," + idChat + ",-1,'" + Utilidades.pasarFechaHoraBBDD(LocalDateTime.now()) + "')";
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            stmt.executeUpdate(sentencia);
            mensaje = "Este contacto te bloqueo";
            sentencia = "insert into mensajesUser values(" + id + ",'" + mensaje + "'," + idChat + ",-1,'" + Utilidades.pasarFechaHoraBBDD(LocalDateTime.now()) + "'," + idUserBloqueado + ",false,false )";
            stmt.executeUpdate(sentencia);
            mensaje = "Bloqueaste a este contacto";
            sentencia = "insert into mensajesUser values(" + id + ",'" + mensaje + "'," + idChat + ",-1,'" + Utilidades.pasarFechaHoraBBDD(LocalDateTime.now()) + "'," + idUserBloquea + ",true,false )";
            stmt.executeUpdate(sentencia);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean eliminaMensajes(DaoManager dao,long idChat,int idUser){
        String sentencia = "delete from mensajesUser where idChat=" + idChat + " and idUserRecibe=" + idUser;
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            stmt.executeUpdate(sentencia);
            dao.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
