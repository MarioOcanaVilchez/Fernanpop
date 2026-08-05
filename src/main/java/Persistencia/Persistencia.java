package Persistencia;

import Controller.GestionAPP;
import Modelos.Producto;
import Modelos.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import javax.mail.Session;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
@WebServlet("/imagenesProductos")
@MultipartConfig
public class Persistencia {
    public static void existenCarpetas(String ruta) {
        if (new File(ruta + "Data").exists()) {
            if (!new File(ruta + "Data/UserEnUso").exists()) new File("Data/UserEnUso").mkdir();
        } else {
            new File(ruta + "Data").mkdir();
        }
        if (!new File(ruta + "Config.properties").exists()) {
            try {
                new File(ruta + "Config.properties").createNewFile();
                Properties properties = new Properties();
                properties.load(new FileReader("Config.properties"));
                guardaProperties(ruta , properties);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String leeProperties(String propiedad){
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("C:\\1ºDam\\Programación\\proyectosjava\\fernanpop\\Config.properties"));
            return properties.getProperty(propiedad);
        } catch (IOException e) {
            return null;
        }
    }
    public static void setProperties(String propiedad,String valor){
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("Config.properties"));
            properties.setProperty(propiedad,valor);
            guardaProperties(properties);
        } catch (IOException e) {
        }
    }
    public static void guardaProperties(Properties properties){
        String datos = properties.toString().replace("{","").replace("}","");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("Config.properties"));
            int posComa = datos.indexOf(',');
            do{
                bw.write(datos.substring(0,posComa) + "\n");
                datos = datos.substring(datos.indexOf(',') + 2);
                posComa = datos.indexOf(',');
            }while(posComa != -1);
            bw.write(datos);
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void guardaProperties(String ruta,Properties properties){
        String datos = properties.toString().replace("{","").replace("}","");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(ruta + "Config.properties"));
            int posComa = datos.indexOf(',');
            do{
                bw.write(datos.substring(0,posComa) + "\n");
                datos = datos.substring(datos.indexOf(',') + 2);
                posComa = datos.indexOf(',');
            }while(posComa != -1);
            bw.write(datos);
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String leeProperties(){
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("Config.properties"));
            return properties.toString().replace("{","").replace("}","").replace(", ","\n");
        } catch (IOException e) {
            return null;
        }
    }

    public static boolean permisoUsoSinLogeo(){
        String permiso =  leeProperties("permisoSinLogeo");
        if (permiso == null) return false;
        return permiso.equalsIgnoreCase("si");
    }
    public static boolean ponerUserEnUso(Usuario usuario){
        String ruta = leeProperties("rutaUserEnUso");
        if (ruta == null) return false;
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta + "/" + usuario.getId() + ".bin"));
            oos.writeObject(usuario);
            oos.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    public static Usuario cogerUserEnUso(){
        String ruta = leeProperties("rutaUserEnUso");
        if (ruta == null) return null;
        String [] ficheros = new File(ruta).list();
        if (ficheros != null && ficheros.length == 1) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta + "/" + ficheros[0]));
                Usuario u = (Usuario) ois.readObject();
                ois.close();
                return u;
            } catch (IOException e) {
                return null;
            } catch (ClassNotFoundException e) {
                return null;
            }
        } else if (ficheros != null){
            for (String fichero : ficheros){
                new File(ruta + "/" + fichero).delete();
            }
        }
        return null;
    }
    public static void quitarUserEnUso(Usuario usuario) {
        String ruta = leeProperties("rutaUserEnUso");
        if (ruta != null) {
            new File(ruta + "/" + usuario.getId() + ".bin").delete();
        }
    }
    public static boolean guardaUser(String ruta,Usuario usuario){
        if (ruta == null) return false;
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta + "/" + usuario.getId() + ".bin"));
            oos.writeObject(usuario);
            oos.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    public static boolean copiaSeguridad(String ruta,ArrayList<Usuario> usuarios){
        if (!ruta.endsWith("\\")) ruta = ruta + "\\";
        existenCarpetas(ruta);
        for (Usuario u : usuarios){
            guardaUser(ruta + "Data",u);
        }
        return true;
    }
    public static boolean creaFicheroProductos(GestionAPP fernanpop){
        try {
            fernanpop.setUsuario(null);
            new File("productos.csv").createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter("productos.csv"));
            bw.write("id;nombre;descripción;email vendedor;precio;estado;\n");
            for (Producto p: fernanpop.getPaginaProductos(null,null,"aleatorio",0,Integer.MAX_VALUE)){
                bw.write(p.getId() + ";" + p.getTitulo() + ";" + p.getDescripcion() + ";" + fernanpop.buscaUserPorProducto(p).getEmail() + ";" + p.getPrecio() + " €;" + p.getEstado() + ";\n");
            }
            bw.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    public static void eliminaFicheroProductos(){
        new File("productos.csv").delete();
    }
    public static void subeImagen(long id, HttpServletRequest request, HttpSession session){
        Part imagen;
        try {
            imagen = request.getPart("imagen");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
        if (imagen != null && imagen.getSize() != 0) {
            String nombreOriginal = imagen.getSubmittedFileName();
            String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
            session.setAttribute("id", id);
            String nombreNuevo = id + extension;
            String ruta = "C:\\1ºDam\\Programación\\proyectosjava\\fernanpop\\imagenesProductos";
            try {
                imagen.write(new File(ruta, nombreNuevo).getAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            session.setAttribute("nombreImagen", nombreNuevo);
        }
    }
    public static void eliminaImagen(String nombre){
        File imagen = new File("C:\\1ºDam\\Programación\\proyectosjava\\fernanpop\\imagenesProductos\\" + nombre);
        if (imagen.exists()) imagen.delete();
    }
}

