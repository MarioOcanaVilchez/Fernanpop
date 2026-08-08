package Dao;

import Modelos.Chat;
import Modelos.Usuario;
import Utilidades.Utilidades;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class DaoMensajeSQL {
    public boolean insertaMensaje(DaoManager dao, String contenido, Chat chat, LocalDateTime fecha,int idUser){
        long idMensaje = generaId(dao,chat.getId());
        String sentencia = "insert into mensajesChat values (" + idMensaje + ",'" + contenido + "'," + chat.getId() + "," + idUser + ",'" + Utilidades.pasarFechaHoraBBDD(fecha) + "')";
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            stmt.executeUpdate(sentencia);
            for (Usuario u: chat.getUsuarios()){
                sentencia = "insert into mensajesUser values (" + idMensaje + ",'" + contenido + "'," + chat.getId() + "," + idUser + ",'" + Utilidades.pasarFechaHoraBBDD(fecha) + "'," + u.getId() + ")";
                stmt.executeUpdate(sentencia);
            }
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
}
