package Utilidades;


import Modelos.Trato;
import Persistencia.Persistencia;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

public class Comunicaciones {
    public static boolean enviarMensajeTelegram(String mensaje) {
         //URL de la API de mi bot en mi conversación
        String fijo = Persistencia.leeProperties("rutaTelegram");
        String direccion = fijo + URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
        //Metemos el mensaje
        URL url;
        boolean dev = false;
        try {
            url = new URL(direccion); //Creando un objeto URL con la dirección de la API de mi bot
            URLConnection con = url.openConnection(); //Realizando la peticción GET
            //Con esto, copiamos en in la respuesta HTTP , por si lo necesitamos
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            dev = true;//Ha tenido éxito
        } catch (IOException e) {
            return false;
        }
        return dev;//Devuelvo si ha tenido éxito
    }
    public static boolean enviarEmail(String destinatario, String asunto, String cuerpo) {

        String remitente = Persistencia.leeProperties("correo");
        String clave = Persistencia.leeProperties("claveCorreo");

        // Propiedades de la conexión que se va a establecer con el servidor de correo SMTP
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // Servidor SMTP de Google
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", clave);
        props.put("mail.smtp.auth", "true"); // Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", "true"); // Conectar de manera segura
        props.put("mail.smtp.port", "587"); // Puerto SMTP seguro de Google

        // Se obtiene la sesión en el servidor de correo
        Session session = Session.getDefaultInstance(props);

        try {
            // Creación del mensaje a enviar
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            //message.setText(cuerpo); // Para enviar texto plano
            message.setContent(cuerpo, "text/html; charset=utf-8"); // Para enviar html

            // Definición de los parámetros del protocolo de transporte
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, clave);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (MessagingException me) {
            return false;
        }
        return true;
    }
    public static boolean enviarEmailConPDF(String destinatario, String asunto, String cuerpo, Trato t, String emailVendedor, String emailComprador) {

        String remitente = Persistencia.leeProperties("correo");
        String clave = Persistencia.leeProperties("claveCorreo");

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", clave);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props);

        try {
            String ruta;
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);

            MimeBodyPart texto = new MimeBodyPart();
            texto.setContent(cuerpo, "text/html; charset=utf-8");
            MimeBodyPart archivo = new MimeBodyPart();

            try {
                ruta = Pdfs.pdf(emailVendedor, emailComprador, t.getProducto().getTitulo(), t.getPrecio(), "C:\\1ºDam\\Programación\\proyectosjava\\fernanpop\\logo fernanpop.png");
                if (ruta == null) return false;

                // Adjuntar archivo al correo
                archivo.attachFile(new File(ruta));
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(texto);
            multipart.addBodyPart(archivo);
            message.setContent(multipart);

            // Envío del correo
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, clave);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            // --- SECCIÓN DE GUARDADO EN CARPETA ---

            // 1. Reemplazamos los ':' por '_' para evitar caracteres prohibidos en Windows
            String nombreLimpio = "Producto_" + t.getProducto().getId() + "_Titulo_" + t.getProducto().getTitulo() + ".pdf";
            // Eliminamos otros caracteres ilegales que pueda tener el título del producto (\ / : * ? " < > |)
            nombreLimpio = nombreLimpio.replaceAll("[\\\\/:*?\"<>|]", "_");

            File carpetaDestino = new File("C:\\1ºDam\\Programación\\proyectosjava\\fernanpop\\Tratos");
            File archivoDestino = new File(carpetaDestino, nombreLimpio);

            try {
                // 2. Crear las carpetas si no existen
                if (!carpetaDestino.exists()) {
                    carpetaDestino.mkdirs();
                }

                // 3. Mover el archivo reemplazando si ya existe
                Files.move(
                        new File(ruta).toPath(),
                        archivoDestino.toPath(),
                        StandardCopyOption.REPLACE_EXISTING
                );
                System.out.println("PDF guardado con éxito en: " + archivoDestino.getAbsolutePath());

            } catch (IOException e) {
                System.err.println("Error al mover el archivo PDF: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (MessagingException me) {
            me.printStackTrace();
            return false;
        }
        return true;
    }
    public static boolean enviarEmailConProductos(String destinatario, String asunto, String cuerpo) {

        String remitente = Persistencia.leeProperties("correo");
        String clave = Persistencia.leeProperties("claveCorreo");

        // Propiedades de la conexión que se va a establecer con el servidor de correo SMTP
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // Servidor SMTP de Google
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", clave);
        props.put("mail.smtp.auth", "true"); // Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", "true"); // Conectar de manera segura
        props.put("mail.smtp.port", "587"); // Puerto SMTP seguro de Google

        // Se obtiene la sesión en el servidor de correo
        Session session = Session.getDefaultInstance(props);

        try {
            // Creación del mensaje a enviar
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(destinatario));
            message.setSubject(asunto);



            MimeBodyPart texto = new MimeBodyPart();
            texto.setContent(cuerpo,"text/html; charset=utf-8");
            MimeBodyPart archivo = new MimeBodyPart();
            try {
                archivo.attachFile(new File("productos.csv"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(texto);
            multipart.addBodyPart(archivo);
            message.setContent(multipart);
            //message.setText(cuerpo); // Para enviar texto plano


            // Definición de los parámetros del protocolo de transporte
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, clave);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (MessagingException me) {
            return false;
        }
        return true;
    }
}
