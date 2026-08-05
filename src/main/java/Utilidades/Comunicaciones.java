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
    public static boolean enviarEmailConPDF(String destinatario, String asunto, String cuerpo, Trato t,String emailVendedor,String emailComprador) {

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
            String ruta;
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
                ruta = PlantillasCorreo.pdf(emailVendedor,emailComprador,t.getProducto().getTitulo(),t.getPrecio());
                if (ruta == null) return false;
                archivo.attachFile(new File(ruta));
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
            new File(ruta).delete();
        }
        catch (MessagingException me) {
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
