package Utilidades;
import Modelos.Producto;
import Modelos.Trato;
import Modelos.Usuario;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.awt.*;
import java.io.IOException;

public class PlantillasCorreo {
    public static String emailNumero(String num){
        String cuerpo = "";
        cuerpo += "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset='utf-8'>\n" +
                "    <meta http-equiv='X-UA-Compatible' content='IE=edge'>\n" +
                "    <title>Page Title</title>\n" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1'>\n" +
                "    <style>\n" +
                "    *{\n" +
                "    margin: 0px;\n" +
                "    padding: 0px;\n" +
                "    text-decoration: none;\n" +
                "    font-size: 20px;\n" +
                "}\n" +
                "#contenedor{\n" +
                "    width: 600px;\n" +
                "    margin: 20px auto;\n" +
                "}\n" +
                "#titulo{\n" +
                "    float: left;\n" +
                "    padding: 10px;\n" +
                "    width: 100%;\n" +
                "    color: white;\n" +
                "    background-color: rgb(69,229,110);" +
                "    font-size: 30px}\n" +
                "p{\n" +
                "    float:left;\n" +
                "    width: 90%;\n" +
                "    margin: 5%;\n" +
                "    margin-top: 1%;\n" +
                "    margin-bottom: 1%;\n" +
                "}\n" +
                "#inicio{margin-top:6%;}\n" +
                "h1{\n" +
                "    float:left;\n" +
                "    font-size: 47px;\n" +
                "    width: 30%;\n" +
                "    margin-left: 35%;\n" +
                "    margin-right: 35%;\n" +
                "    border:2px black solid;\n" +
                "    text-align: center;\n" +
                "    margin-bottom: 5%;\n" +
                "    margin-top: 5%;\n" +
                "}\n" +
                "#final{ margin-bottom: 6%;}\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div id='contenedor'>\n" +
                "     <div id='titulo'>Inicia sesión en tu cuenta</div>\n" +
                "    <p id='inicio'>Para continuar introduzca el siguiente número en la app de Fernanpop:</p>\n" +
                "    <h1>" + num + "</h1>\n" +
                "    <p id='final'>Gracias por confiar en fernanpop, la app creada para hacer que la compra-venta sea más sencilla, segura y cercana. Valoramos enormemente que formes parte de nuestra comunidad y que elijas nuestra plataforma para encontrar oportunidades, dar nueva vida a tus productos y conectar con otras personas. Cada usuario como tú nos impulsa a seguir mejorando, innovando y ofreciendo una experiencia más ágil y útil. Apreciamos tu tiempo, tu confianza y tu participación activa. Seguiremos trabajando para que fernanpop sea siempre tu mejor opción en el mundo de la compra-venta. ¡Gracias por estar con nosotros!</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

        return cuerpo;
    }
    public static String emailNuevoProducto(String titulo,String descripcion,double precio) {
        return """
        <div style="font-family: sans-serif; border: 1px solid #eee; padding: 20px; border-radius: 10px; max-width: 500px;">
            <h1 style="color: #2c3e50; border-bottom: 2px solid #27ae60; padding-bottom: 10px;">📦 Nuevo producto publicado</h1>
            <p>¡Hola! Has puesto a la venta:</p>
            <div style="background-color: #f9f9f9; padding: 15px; border-left: 4px solid #27ae60;">
                <h3 style="margin: 0;">%s</h3>
                <p style="color: #27ae60; font-weight: bold; font-size: 1.2em;">%s €</p>
                <p style="color: #7f8c8d; font-style: italic;">%s</p>
            </div>
            <p style="margin-top: 20px; font-size: 0.9em; color: #95a5a6;">Gracias por usar nuestra plataforma.</p>
        </div>
        """.formatted(titulo, precio, descripcion);
    }
    // Color anterior del borde de producto publicado #3498db

    public static String emailProductoVendido(Trato t, Usuario vendedor) {
        return """
        <div style="font-family: sans-serif; border: 1px solid #eee; padding: 20px; border-radius: 10px; max-width: 500px;">
            <h1 style="color: #27ae60;">💰 ¡Enhorabuena, compra realizada!</h1>
            <p>El usuario <strong>%s</strong> te ha vendido su producto:</p>
            <div style="padding: 10px; border: 1px dashed #27ae60; text-align: center;">
                <h2 style="margin: 5px 0;">%s</h2>
                <span style="font-size: 1.5em; font-weight: bold;">%s €</span>
            </div>
            <p style="margin-top: 15px;">Ya puedes ponerte en contacto con el vendedor para el envío.</p>
        </div>
        """.formatted(vendedor.getEmail(), t.getProducto().getTitulo(), t.getPrecio());
    }
    public static String emailProductoVendidoComprador(Trato t, Usuario comprador) {
        return """
        <div style="font-family: sans-serif; border: 1px solid #eee; padding: 20px; border-radius: 10px; max-width: 500px;">
            <h1 style="color: #27ae60;">💰 ¡Enhorabuena, venta realizada!</h1>
            <p>El usuario <strong>%s</strong> te ha comprado su producto:</p>
            <div style="padding: 10px; border: 1px dashed #27ae60; text-align: center;">
                <h2 style="margin: 5px 0;">%s</h2>
                <span style="font-size: 1.5em; font-weight: bold;">%s €</span>
            </div>
            <p style="margin-top: 15px;">Ya puedes ponerte en contacto con el vendedor para el envío.</p>
        </div>
        """.formatted(comprador.getEmail(), t.getProducto().getTitulo(), t.getPrecio());
    }
    public static String emailSolicitud(Producto productos, Usuario comprador,double precio){
        return "<div style=\"font-family: sans-serif; border: 1px solid #eee; padding: 20px; border-radius: 10px; max-width: 500px;\">"
                + "<h1 style=\"color: #27ae60;\">💰 ¡Tienes una solicitud de compra!</h1>"
                + "<p>El usuario <strong>" + comprador.getNombre() + "</strong> solicita comprar su producto:</p>"
                + "<div style=\"padding: 10px; background-color: #2bcd6e; border-radius: 0px 10px 10px 0px; text-align: center; width: 60%; overflow: hidden;\">"
                + "<h2 style=\"margin: 5px 0;\">" + productos.getTitulo() + "</h2>"
                + "<span style=\"font-size: 1.5em; font-weight: bold;\">" + precio +" €</span>"
                + "</div>"
                + "<p style=\"margin-top: 15px;\">Entra en la app para responder a su solicitud.</p>"
                + "</div>";
    }

    public static String pdf(String comprador,String vendedor,String producto,double precio) {
        try {
            PDDocument doc = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            PDPageContentStream cs = new PDPageContentStream(doc, page);
            float mediaPagina = page.getMediaBox().getWidth();
            float mediaTexto;
            //fondo
            cs.setNonStrokingColor(new Color(54, 181, 159));
            cs.addRect(0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());
            cs.fill();

            //recuadro blanco
            cs.setNonStrokingColor(Color.WHITE);
            cs.addRect(50, 400, 500, 300);
            cs.fill();

            PDType1Font font = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            //titulo
            cs.beginText();
            cs.setFont(font, 24);
            mediaTexto = calculaMediaTexto("Venta Fernanpop",font,24);
            cs.setNonStrokingColor(new Color(0, 102, 204));
            cs.newLineAtOffset((mediaPagina - mediaTexto) / 2, 720);
            cs.showText("Venta Fernanpop");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 16);
            cs.setNonStrokingColor(Color.BLACK);
            cs.newLineAtOffset(70, 650);
            cs.showText("Datos de la venta");
            cs.endText();

            //datos de la venta
            cs.setFont(font, 14);

            int y = 610;

            cs.beginText();
            cs.newLineAtOffset(70, y);
            cs.showText("Comprador: " + comprador);
            cs.endText();

            cs.beginText();
            cs.newLineAtOffset(70, y - 30);
            cs.showText("Vendedor: " + vendedor);
            cs.endText();

            cs.beginText();
            cs.newLineAtOffset(70, y - 60);
            cs.showText("Producto: " + producto);
            cs.endText();

            //precio
            cs.beginText();
            cs.setFont(font, 16);
            mediaTexto = calculaMediaTexto("Precio: ",font,16);
            cs.setNonStrokingColor(new Color(0, 153, 0));
            cs.newLineAtOffset((mediaPagina - mediaTexto) / 2, y - 100);
            cs.showText("Precio: ");
            cs.endText();


            cs.beginText();
            cs.setFont(font, 24);
            mediaTexto = calculaMediaTexto(precio + " €",font,30);
            cs.setNonStrokingColor(new Color(0, 153, 0));
            cs.newLineAtOffset((mediaPagina - mediaTexto) / 2, y - 140);
            cs.showText(precio + " €");
            cs.endText();

            cs.close();

            doc.save("VentaFernanpop_mejorado.pdf");
            doc.close();
            return "VentaFernanpop_mejorado.pdf";
        } catch (IOException e){
            return null;
        }
    }
    public static float calculaMediaTexto(String texto,PDType1Font font,int fontSize){
        try {
            return font.getStringWidth(texto) / 1000 * fontSize;
        } catch (IOException e) {
            return 0;
        }

    }
}
