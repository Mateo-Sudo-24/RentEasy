import Modelos.Transaccion;
import utils.PDFGenerator;
import DAO.TransaccionDAO;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TransaccionDAO transaccionDAO = new TransaccionDAO();

        // 🔹 Obtener todas las transacciones registradas en la BD
        List<Transaccion> transacciones = transaccionDAO.buscarTransaccionesPorUsuario("67a5334cd1e30f17f866309e"); // ID del usuario

        if (transacciones.isEmpty()) {
            System.out.println("❌ No hay transacciones registradas en la base de datos.");
        } else {
            // 🔹 Seleccionar la primera transacción y generar el PDF
            String transaccionId = transacciones.get(0).getId();
            PDFGenerator.generarPDFDesdeBD(transaccionId);
        }
    }
}
