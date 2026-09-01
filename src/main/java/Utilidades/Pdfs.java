package Utilidades;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import java.awt.*;

public class Pdfs {
    private static final Color VERDE_OSCURO   = new Color(12, 104, 67);   // #0C6843 - marca
    private static final Color VERDE_ACENTO   = new Color(46, 158, 111);  // acento / detalles
    private static final Color GRIS_CLARO     = new Color(240, 242, 240); // fondo de tarjetas
    private static final Color GRIS_BORDE     = new Color(210, 214, 210); // bordes sutiles
    private static final Color GRIS_OSCURO    = new Color(51, 51, 51);    // texto principal
    private static final Color GRIS_MEDIO     = new Color(120, 120, 120); // texto secundario
    private static final Color BLANCO         = Color.WHITE;

    public static String pdf(String comprador, String vendedor, String producto, double precio, String rutaLogo) {

        String nombreArchivo = "VentaFernanpop_mejorado.pdf";

        try (PDDocument doc = new PDDocument()) {

            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            float pageWidth = page.getMediaBox().getWidth();
            float pageHeight = page.getMediaBox().getHeight();
            float margin = 50f;
            float contentWidth = pageWidth - (margin * 2);

            // Fuentes (sintaxis PDFBox 3.x)
            PDFont fontBold   = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            PDFont fontRegular = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
            PDFont fontItalic  = new PDType1Font(Standard14Fonts.FontName.HELVETICA_OBLIQUE);

            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {

                // Variable de posición vertical relativa, se ajusta según se dibuja
                float cursorY = pageHeight;

                // ===== 1. ENCABEZADO (banda de color de marca) =====
                float headerHeight = 90f;
                cs.setNonStrokingColor(VERDE_OSCURO);
                cs.addRect(0, pageHeight - headerHeight, pageWidth, headerHeight);
                cs.fill();

                // Logo (izquierda del encabezado), con manejo robusto de errores
                float logoSize = 50f;
                float logoX = margin;
                float logoY = pageHeight - headerHeight + ((headerHeight - logoSize) / 2);
                boolean logoDibujado = false;

                if (rutaLogo != null && !rutaLogo.isBlank()) {
                    try {
                        File logoFile = new File(rutaLogo);
                        if (logoFile.exists()) {
                            PDImageXObject logoImg = PDImageXObject.createFromFileByExtension(logoFile, doc);
                            // Redimensionado proporcional respetando el aspect ratio original
                            float ratio = (float) logoImg.getHeight() / (float) logoImg.getWidth();
                            float drawW = logoSize;
                            float drawH = logoSize * ratio;
                            if (drawH > logoSize) {
                                drawH = logoSize;
                                drawW = logoSize / ratio;
                            }
                            cs.drawImage(logoImg, logoX, pageHeight - headerHeight + ((headerHeight - drawH) / 2), drawW, drawH);
                            logoDibujado = true;
                        }
                    } catch (IOException | IllegalArgumentException e) {
                        System.err.println("[Fernanpop] Error al cargar el logo: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                        // Si el logo falla o no existe, se continúa sin romper la generación del PDF
                        logoDibujado = false;
                    }
                }

                // Título del encabezado (se desplaza si el logo se dibujó)
                float tituloX = logoDibujado ? logoX + logoSize + 15 : margin;
                cs.setNonStrokingColor(BLANCO);
                cs.beginText();
                cs.setFont(fontBold, 20);
                cs.newLineAtOffset(tituloX, pageHeight - 42);
                cs.showText("Fernanpop");
                cs.endText();

                cs.beginText();
                cs.setFont(fontRegular, 11);
                cs.newLineAtOffset(tituloX, pageHeight - 60);
                cs.showText("Comprobante de Venta");
                cs.endText();

                cursorY = pageHeight - headerHeight - 40;

                // ===== 2. FECHA / REFERENCIA =====
                String fechaTexto = LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", new Locale("es", "ES")));
                cs.setNonStrokingColor(GRIS_MEDIO);
                cs.beginText();
                cs.setFont(fontRegular, 9);
                cs.newLineAtOffset(margin, cursorY);
                cs.showText("Fecha de emisión: " + fechaTexto);
                cs.endText();

                cursorY -= 30;

                // ===== 3. TARJETA - RESUMEN DE LA TRANSACCIÓN =====
                float filaAltura = 28f;
                int numFilas = 3;
                float cardPadding = 15f;
                float cardHeight = (filaAltura * numFilas) + (cardPadding * 2);
                float cardY = cursorY - cardHeight;

                // Fondo de la tarjeta
                cs.setNonStrokingColor(GRIS_CLARO);
                cs.addRect(margin, cardY, contentWidth, cardHeight);
                cs.fill();

                // Borde sutil
                cs.setStrokingColor(GRIS_BORDE);
                cs.setLineWidth(0.7f);
                cs.addRect(margin, cardY, contentWidth, cardHeight);
                cs.stroke();

                // Filas de datos: etiqueta en negrita + valor en fuente normal
                float filaY = cardY + cardHeight - cardPadding - 12;
                String[][] filas = {
                        {"Comprador:", comprador},
                        {"Vendedor:", vendedor},
                        {"Producto:", producto}
                };

                for (String[] fila : filas) {
                    cs.setNonStrokingColor(GRIS_OSCURO);
                    cs.beginText();
                    cs.setFont(fontBold, 11);
                    cs.newLineAtOffset(margin + cardPadding, filaY);
                    cs.showText(fila[0]);
                    cs.endText();

                    cs.beginText();
                    cs.setFont(fontRegular, 11);
                    cs.newLineAtOffset(margin + cardPadding + 100, filaY);
                    cs.showText(fila[1] != null ? fila[1] : "-");
                    cs.endText();

                    filaY -= filaAltura;
                }

                cursorY = cardY - 30;

                // ===== 4. TARJETA DESTACADA - PRECIO TOTAL =====
                float totalCardHeight = 70f;
                float totalCardY = cursorY - totalCardHeight;

                cs.setNonStrokingColor(VERDE_ACENTO);
                cs.addRect(margin, totalCardY, contentWidth, totalCardHeight);
                cs.fill();

                String labelTotal = "PRECIO TOTAL";
                String valorTotal = String.format(new Locale("es", "ES"), "$ %,.2f", precio);

                cs.setNonStrokingColor(BLANCO);
                cs.beginText();
                cs.setFont(fontBold, 12);
                cs.newLineAtOffset(margin + 20, totalCardY + totalCardHeight - 25);
                cs.showText(labelTotal);
                cs.endText();

                cs.beginText();
                cs.setFont(fontBold, 26);
                cs.newLineAtOffset(margin + 20, totalCardY + 15);
                cs.showText(valorTotal);
                cs.endText();

                cursorY = totalCardY - 40;

                // ===== 5. LÍNEA DECORATIVA =====
                cs.setStrokingColor(GRIS_BORDE);
                cs.setLineWidth(0.5f);
                cs.moveTo(margin, cursorY);
                cs.lineTo(pageWidth - margin, cursorY);
                cs.stroke();

                // ===== 6. PIE DE PÁGINA =====
                float footerY = 40f;
                cs.setNonStrokingColor(GRIS_MEDIO);
                cs.beginText();
                cs.setFont(fontItalic, 8);
                cs.newLineAtOffset(margin, footerY);
                cs.showText("Este comprobante certifica una transacción realizada a través de Fernanpop.");
                cs.endText();

                cs.beginText();
                cs.setFont(fontItalic, 8);
                cs.newLineAtOffset(margin, footerY - 12);
                cs.showText("Documento generado automáticamente. No requiere firma.");
                cs.endText();
            }

            doc.save(nombreArchivo);
            doc.close();
            return nombreArchivo;
        } catch (IOException e) {
            throw new RuntimeException("Error al generar el comprobante PDF de Fernanpop", e);
        }
    }
}
