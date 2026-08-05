package Dao;

import Modelos.Producto;
import Modelos.Usuario;

public interface DaoUsuario {
    public boolean insertaUsuario(DaoManager dao,String email,String clave,String nombre,String apel,int movil);
    public boolean eliminaUsuario(DaoManager dao,Usuario usuario,DaoProductoSQL daoProducto);
    public boolean actualizaUsuario(DaoManager dao,Usuario usuario);
    public Usuario buscaUsuarioId(DaoManager dao,int id);
    public int generaId(DaoManager dao);
    public Usuario login(DaoManager dao,String email,String clave);
    public Usuario buscaUsuarioMail(DaoManager dao,String email);
    public Usuario buscaUsuarioPorProducto(DaoManager dao, Producto p,DaoProductoSQL daoProducto);

}
