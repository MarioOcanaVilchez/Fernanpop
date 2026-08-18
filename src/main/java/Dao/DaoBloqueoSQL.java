package Dao;

import Modelos.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoBloqueoSQL {
    public boolean userBloqueado(DaoManager dao, int idUserBloquea,int idUserBloqueado){
        String sentencia = "select * from bloqueos where idUserBloquea=" + idUserBloquea + " and idUserBloqueado=" + idUserBloqueado;
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
            return true;
        }
    }
    public boolean bloqueaUser(DaoManager dao,DaoMensajeSQL daoMensaje,int idUserBloquea,int idUserBloqueado,long idChat){
        String sentencia = "insert into bloqueos values(" + idUserBloquea + ","  + idUserBloqueado + ")";
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            stmt.executeUpdate(sentencia);
            dao.close();
            daoMensaje.enviaMensajeBloqueo(dao,idChat,idUserBloquea,idUserBloqueado);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean desbloquearUser(DaoManager dao,int idUserBloquea,int idUserBloqueado){
        String sentencia = "delete from bloqueos where idUserBloquea=" + idUserBloquea + " and idUserBloqueado=" + idUserBloqueado;
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
