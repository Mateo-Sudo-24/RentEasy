package utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDFGenerator {

    // Ruta del directorio donde se guardarán los PDFs en Windows
    private static final String DIRECTORY_PATH = "C:\\Users\\User\\Desktop\\PDFS";

    // ✅ Método para generar PDF con los datos del Dashboard Vendedor
    public static void generarPDFDesdeDashboardVendedor(String filePath, String nombreVendedor, String listaCasas) {
        try {
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
            filePath = DIRECTORY_PATH + "\\DashboardVendedor.pdf";

            // Crear el documento PDF
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Agregar encabezado
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("📄 RentEasy - Dashboard del Vendedor", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph("\nDatos del Vendedor:"));
            document.add(new Paragraph("------------------------------------------------------\n"));
            document.add(new Paragraph("📛 Nombre del Vendedor: " + nombreVendedor));
            document.add(new Paragraph("\n🏠 Casas Listadas:"));
            document.add(new Paragraph(listaCasas));

            document.add(new Paragraph("\n------------------------------------------------------"));
            document.add(new Paragraph("✅ Información exportada con éxito."));

            // Cerrar el documento
            document.close();
            System.out.println("📄 PDF de Dashboard Vendedor generado con éxito: " + filePath);
        } catch (DocumentException | IOException e) {
            System.err.println("❌ Error al generar el PDF: " + e.getMessage());
        }
    }
}