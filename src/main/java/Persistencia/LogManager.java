package Persistencia;

import Modelos.Producto;
import Modelos.Trato;
import Modelos.Usuario;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogManager {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String FILE_NAME = "registros.log";

    private static void grabarLog(String mensaje){
        try  {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true));
            bw.write(mensaje);
            bw.newLine();
            bw.close();
        } catch (IOException e) {

            System.out.println("Error al escribir el archivo de log: " + e.getMessage());
        }
    }

    public static void logInicioSesion(Usuario uTemp){
        String fechaHora = LocalDateTime.now().format(FORMATTER);
        grabarLog("Inicio sesión del usuario " + uTemp.getId() + " con email " + uTemp.getEmail() + " el " + fechaHora);
    }

    public static void logCierreSesion(Usuario uTemp){
        String fechaHora = LocalDateTime.now().format(FORMATTER);
        grabarLog("Cierre sesión del usuario " + uTemp.getId() + " con email " + uTemp.getEmail() + " el " + fechaHora);
    }

    public static void logNuevoProducto(Producto p, Usuario uTemp){
        String fechaHora = LocalDateTime.now().format(FORMATTER);
        grabarLog("Nuevo producto + " + p.getTitulo() + " en venta por " + p.getPrecio() + " € por el usuario " + uTemp.getEmail() + " el " + fechaHora);
    }

    public static void logVentaCerrada(Trato t, Usuario vendedor,Usuario comprador){
        String fechaHora = LocalDateTime.now().format(FORMATTER);
        grabarLog("El usuario " + vendedor.getEmail() + " con id " + vendedor.getId() + " vendió " + t.getProducto().getTitulo() + " por " + t.getPrecio() + " € a " + comprador.getEmail() + " con id " + comprador.getId() + " el " + fechaHora);
    }
}
