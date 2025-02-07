package DAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import Modelos.Transaccion;
import Conexion.Conexion;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class TransaccionDAO {
    private MongoCollection<Document> collection;

    public TransaccionDAO() {
        MongoDatabase db = Conexion.getInstance().getDatabase();
        collection = db.getCollection("transacciones");
    }

    // ✅ Registrar una transacción con ObjectId
    public void registrarTransaccion(Transaccion transaccion) {
        Document doc = new Document("_id", new ObjectId()) // Mongo genera el ObjectId automáticamente
                .append("tipo", transaccion.getTipo()) // Compra o Renta
                .append("usuarioId", transaccion.getUsuarioId())
                .append("casaId", transaccion.getCasaId())
                .append("fecha", transaccion.getFecha().toString());

        collection.insertOne(doc);
        System.out.println("✅ Transacción registrada en MongoDB: " + transaccion.getId());
    }

    // ✅ Buscar todas las transacciones de un usuario
    public List<Transaccion> buscarTransaccionesPorUsuario(String usuarioId) {
        List<Transaccion> transacciones = new ArrayList<>();

        for (Document doc : collection.find(eq("usuarioId", usuarioId))) {
            transacciones.add(new Transaccion(
                    doc.getObjectId("_id").toString(),
                    doc.getString("tipo"),
                    doc.getString("usuarioId"),
                    doc.getString("casaId")
            ));
        }

        return transacciones;
    }

    // ✅ Buscar una transacción por su ID
    public Transaccion buscarTransaccionPorId(String transaccionId) {
        try {
            ObjectId transaccionObjectId = new ObjectId(transaccionId); // Convertir a ObjectId
            Document doc = collection.find(eq("_id", transaccionObjectId)).first();

            if (doc != null) {
                return new Transaccion(
                        doc.getObjectId("_id").toString(),
                        doc.getString("tipo"),
                        doc.getString("usuarioId"),
                        doc.getString("casaId")
                );
            }
        } catch (IllegalArgumentException e) {
            System.err.println("❌ Error: El transaccionId no es un ObjectId válido - " + e.getMessage());
        }
        return null;
    }
}
