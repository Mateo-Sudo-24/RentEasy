package utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import Modelos.Transaccion;
import Modelos.Casa;
import DAO.TransaccionDAO;
import DAO.CasaDAO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class PDFGenerator {

    // Ruta del directorio donde se guardarán los PDFs en Windows
    private static final String DIRECTORY_PATH = "C:\\Users\\User\\Desktop\\PDFS";

    // ✅ Generar un PDF con datos de una transacción desde la base de datos
    public static void generarPDFDesdeBD(String transaccionId) {
        try {
            // Obtener datos de la transacción desde la BD
            TransaccionDAO transaccionDAO = new TransaccionDAO();
            CasaDAO casaDAO = new CasaDAO();

            Transaccion transaccion = transaccionDAO.buscarTransaccionPorId(transaccionId);
            if (transaccion == null) {
                System.out.println("❌ No se encontró la transacción en la base de datos.");
                return;
            }

            Casa casa = casaDAO.buscarCasaPorId(transaccion.getCasaId());
            if (casa == null) {
                System.out.println("❌ No se encontró la casa en la base de datos.");
                return;
            }

            // Crear la carpeta si no existe
            File directorio = new File(DIRECTORY_PATH);
            if (!directorio.exists()) {
                if (directorio.mkdirs()) {
                    System.out.println("📂 Carpeta creada en: " + DIRECTORY_PATH);
                } else {
                    System.err.println("❌ Error al crear la carpeta.");
                    return;
                }
            }

            // Ruta completa del archivo PDF
            String filePath = DIRECTORY_PATH + "\\transaccion_" + transaccion.getId() + ".pdf";

            // Crear el documento PDF
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Agregar encabezado
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("🏡 RentEasy - Comprobante de Transacción", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph("\nFecha: " + new Date()));
            document.add(new Paragraph("------------------------------------------------------\n"));

            // Información de la transacción
            document.add(new Paragraph("🆔 ID Transacción: " + transaccion.getId()));
            document.add(new Paragraph("🔹 Tipo de Transacción: " + transaccion.getTipo())); // Compra o Renta
            document.add(new Paragraph("👤 Cliente ID: " + transaccion.getUsuarioId()));
            document.add(new Paragraph("🏠 Casa ID: " + transaccion.getCasaId()));

            // Información de la casa
            document.add(new Paragraph("\n🏠 Información de la Casa:"));
            document.add(new Paragraph("📍 Dirección: " + casa.getDireccion()));
            document.add(new Paragraph("💰 Precio: $" + casa.getPrecio()));
            document.add(new Paragraph("📌 Estado: " + casa.getEstado())); // Disponible, Rentada o Vendida

            document.add(new Paragraph("\n------------------------------------------------------"));
            document.add(new Paragraph("✅ Transacción registrada con éxito."));

            // Cerrar el documento
            document.close();
            System.out.println("📄 PDF de transacción generado con éxito: " + filePath);

        } catch (DocumentException | IOException e) {
            System.err.println("❌ Error al generar el PDF: " + e.getMessage());
        }
    }
}
