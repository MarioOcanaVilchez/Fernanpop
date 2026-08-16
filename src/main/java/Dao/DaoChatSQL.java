package Dao;

import Modelos.Chat;
import Modelos.Usuario;
import Utilidades.Utilidades;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DaoChatSQL {
    public boolean crearChat(DaoManager dao, Usuario[] usuarios){
        long idChat = generaId(dao);
        String sentencia = "insert into chat values (" + idChat + ",null,'')";
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            stmt.executeUpdate(sentencia);
            for (Usuario u : usuarios){
                sentencia = "insert into chatUsuario values (" + u.getId() + "," + idChat + ")";
                stmt.executeUpdate(sentencia);
            }
            dao.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public boolean actualizaUltimoMensaje(DaoManager dao,String ultimoMensaje,LocalDateTime fecha,Usuario userEnvia){
        ultimoMensaje = userEnvia.getEmail() + ": " + ultimoMensaje;
        String sentencia = "update chat set ultimoMensaje='" + ultimoMensaje + "', fechaUltimoMensaje='" + Utilidades.pasarFechaHoraBBDD(fecha) + "'";
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
    public long generaId(DaoManager dao){
        long id;
        do{
            id = (long) (Math.random() * (Long.MAX_VALUE - 1) + 1);
        } while (existeChat(dao,id));
        return id;
    }
    public boolean existeChat(DaoManager dao,long id){
        String sentencia = "select * from chat where id=" + id;
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
            throw new RuntimeException(e);
        }
    }
    //Sirve para obtener los chats de un usuario (Sin mensajes)
    public ArrayList<Chat> getChats(DaoManager dao,DaoUsuarioSQL daoUsuario,DaoMensajeSQL daoMensaje, Usuario uTemp){
        ArrayList<Long> ids = new ArrayList<>();
        int mensajesNoLeidos;
        ArrayList<Chat> chats = new ArrayList<>();
        String sentencia = "select * from chatUsuario where idUser=" + uTemp.getId();
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            while (rs.next()){
                ids.add(rs.getLong("idChat"));
            }
            if (ids.isEmpty()) {
                dao.close();
                return null;
            }
            sentencia = "select * from chat where id in(";
            for (long id : ids){
                sentencia += id + ",";
            }
            sentencia = sentencia.substring(0,sentencia.length() - 1) + ") order by fechaUltimoMensaje desc";
            ids = new ArrayList<>();
            rs = stmt.executeQuery(sentencia);
            while (rs.next()){
                ids.add(rs.getLong("id"));
            }
            dao.close();
            for (long id : ids){
                String ultimoMensaje;
                Usuario [] usuarios = new Usuario[2];
                usuarios[0] = uTemp;
                LocalDateTime fecha;
                int idUser = 0;
                dao.open();
                sentencia = "select * from chatUsuario where idChat=" + id;
                stmt = dao.getConexion().createStatement();
                rs = stmt.executeQuery(sentencia);
                while (rs.next()){
                    if (rs.getInt("idUser") != uTemp.getId()) idUser = rs.getInt("idUser");
                }
                sentencia = "select * from chat where id =" + id;
                rs = stmt.executeQuery(sentencia);
                rs.next();
                if (rs.getString("fechaUltimoMensaje") != null) fecha = Utilidades.pasarFechaHoraLocaldate(rs.getString("fechaUltimoMensaje"));
                else fecha = null;
                ultimoMensaje = rs.getString("ultimoMensaje");
                dao.close();
                usuarios[1] = daoUsuario.buscaUsuarioId(dao,idUser);
                mensajesNoLeidos = daoMensaje.determinarMensajesSinLeer(dao,id,uTemp);
                chats.add(new Chat(id,usuarios,usuarios[1].getEmail(),fecha,ultimoMensaje,mensajesNoLeidos));
            }
            return chats;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Chat getChat(DaoManager dao,DaoUsuarioSQL daoUsuario,DaoMensajeSQL daoMensaje, Usuario uTemp,long idChat){
        String ultimoMensaje;
        Usuario [] usuarios = new Usuario[2];
        usuarios[0] = uTemp;
        LocalDateTime fecha;
        int idUser = 0;
        try {
            dao.open();
            String sentencia = "select * from chatUsuario where idChat=" + idChat;
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            while (rs.next()){
                if (rs.getInt("idUser") != uTemp.getId()) idUser = rs.getInt("idUser");
            }
            sentencia = "select * from chat where id =" + idChat;
            rs = stmt.executeQuery(sentencia);
            rs.next();
            if (rs.getString("fechaUltimoMensaje") != null) fecha = Utilidades.pasarFechaHoraLocaldate(rs.getString("fechaUltimoMensaje"));
            else fecha = null;
            ultimoMensaje = rs.getString("ultimoMensaje");
            dao.close();
            usuarios[1] = daoUsuario.buscaUsuarioId(dao,idUser);
            int mensajesNoLeidos = daoMensaje.determinarMensajesSinLeer(dao,idChat,uTemp);
            return new Chat(idChat,usuarios,usuarios[1].getEmail(),fecha,ultimoMensaje,mensajesNoLeidos);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    //En el hipotetico caso de querer permitir la creación de grupos ahí que cambiar el idUser
    // por un arraylist de ids y que cambiar en el bucle for
    // poner un boolean y si alguno de los ids de usuario no esta en el arraylist cambiarlo a false
    //despues en caso de que al salir del while la variable siga en true hay que hacer otra consulta para
    //saber si hay el numero de usuarios en el chat que en el arraylist
    public long buscaChat(DaoManager dao,Usuario uTemp,int idUser){
        String sentencia = "select * from chatUsuario where idUser=" + uTemp.getId();
        ArrayList<Long> ids = new ArrayList<>();
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            while (rs.next()){
                ids.add(rs.getLong("idChat"));
            }
            if (ids.isEmpty()) {
                dao.close();
                return -1;
            }
            for (long id: ids){
                sentencia = "select * from chatUsuario where idChat=" + id + " and idUser != " + uTemp.getId();
                rs = stmt.executeQuery(sentencia);
                while (rs.next()){
                    if (rs.getInt("idUser") == idUser){
                        dao.close();
                        return id;
                    }
                }
            }
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Usuario[] getUsuariosChat(DaoManager dao,DaoUsuarioSQL daoUsuario,long idChat){
        String sentencia = "select * from chatUsuario where idChat=" + idChat;
        Usuario[] usuarios = new Usuario[2];
        int i = 0;
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            while(rs.next()){
                usuarios[i] = new Usuario(rs.getInt("idUser"));
                i++;
            }
            dao.close();
            for (int j = 0; j < 2; j++) {
                usuarios[j] = daoUsuario.buscaUsuarioId(dao,usuarios[j].getId());
            }
            return usuarios;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Chat cargaChat(DaoManager dao,DaoMensajeSQL daoMensaje,DaoUsuarioSQL daoUsuario,Usuario uTemp,long idChat){
        Chat chat;
        LocalDateTime fecha;
        String sentencia = "select * from chatUsuario where idChat=" + idChat + " and idUser !=" + uTemp.getId();
        Usuario[] usuarios = new Usuario[2];
        usuarios[0] = uTemp;
        int idOtroUser;
        try {
            dao.open();
            Statement stmt = dao.getConexion().createStatement();
            ResultSet rs = stmt.executeQuery(sentencia);
            rs.next();
            idOtroUser = rs.getInt("idUser");
            dao.close();
            usuarios[1] = daoUsuario.buscaUsuarioId(dao,idOtroUser);
            dao.open();
            stmt = dao.getConexion().createStatement();
            sentencia = "select * from chat where id=" + idChat;
            rs = stmt.executeQuery(sentencia);
            rs.next();
            if (rs.getString("fechaUltimoMensaje") == null) fecha = null;
            else fecha = Utilidades.pasarFechaHoraLocaldate(rs.getString("fechaUltimoMensaje"));
            chat = new Chat(rs.getLong("id"),usuarios,usuarios[1].getEmail(),fecha,rs.getString("ultimoMensaje"));
            dao.close();
            chat.addMensajes(daoMensaje.cargaMensajes(dao,daoUsuario,uTemp,idChat));
            return chat;
        } catch (SQLException e) {
            return null;
        }
    }
}
