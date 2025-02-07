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
        Document doc = new Document("_id", new ObjectId())
                .append("tipo", transaccion.getTipo())
                .append("usuarioId", transaccion.getUsuarioId())
                .append("casaId", transaccion.getCasaId())
                .append("fecha", transaccion.getFecha().toString());

        collection.insertOne(doc);
        System.out.println("✅ Transacción registrada en MongoDB con ID: " + doc.getObjectId("_id").toString());
    }

    // ✅ Buscar todas las transacciones
    public List<Transaccion> buscarTodasTransacciones() {
        List<Transaccion> transacciones = new ArrayList<>();
        for (Document doc : collection.find()) {
            transacciones.add(new Transaccion(
                    doc.getObjectId("_id").toString(),
                    doc.getString("tipo"),
                    doc.getString("usuarioId"),
                    doc.getString("casaId"),
                    doc.getString("fecha")
            ));
        }
        return transacciones;
    }

    // ✅ Buscar todas las transacciones de un usuario
    public List<Transaccion> buscarTransaccionesPorUsuario(String usuarioId) {
        List<Transaccion> transacciones = new ArrayList<>();
        for (Document doc : collection.find(eq("usuarioId", usuarioId))) {
            transacciones.add(new Transaccion(
                    doc.getObjectId("_id").toString(),
                    doc.getString("tipo"),
                    doc.getString("usuarioId"),
                    doc.getString("casaId"),
                    doc.getString("fecha")
            ));
        }
        return transacciones;
    }

    // ✅ Buscar una transacción por su ID
    public Transaccion buscarTransaccionPorId(String transaccionId) {
        try {
            ObjectId transaccionObjectId = new ObjectId(transaccionId);
            Document doc = collection.find(eq("_id", transaccionObjectId)).first();

            if (doc != null) {
                return new Transaccion(
                        doc.getObjectId("_id").toString(),
                        doc.getString("tipo"),
                        doc.getString("usuarioId"),
                        doc.getString("casaId"),
                        doc.getString("fecha")
                );
            }
        } catch (IllegalArgumentException e) {
            System.err.println("❌ Error: El transaccionId no es un ObjectId válido - " + e.getMessage());
        }
        return null;
    }

    // ✅ Buscar una transacción por ID de Casa
    public Transaccion buscarTransaccionPorCasaId(String casaId) {
        try {
            Document doc = collection.find(eq("casaId", casaId)).first();
            if (doc != null) {
                return new Transaccion(
                        doc.getObjectId("_id").toString(),
                        doc.getString("tipo"),
                        doc.getString("usuarioId"),
                        doc.getString("casaId"),
                        doc.getString("fecha")
                );
            }
        } catch (Exception e) {
            System.err.println("❌ Error al buscar transacción por casa ID: " + e.getMessage());
        }
        return null;
    }

    // ✅ Actualizar una transacción
    public void actualizarTransaccion(String id, String nuevoTipo, String nuevoUsuarioId, String nuevaCasaId) {
        try {
            Document query = new Document("_id", new ObjectId(id));
            Document update = new Document("$set", new Document("tipo", nuevoTipo)
                    .append("usuarioId", nuevoUsuarioId)
                    .append("casaId", nuevaCasaId));

            collection.updateOne(query, update);
            System.out.println("✅ Transacción actualizada correctamente.");
        } catch (IllegalArgumentException e) {
            System.err.println("❌ Error: No se pudo actualizar la transacción - " + e.getMessage());
        }
    }

    // ✅ Eliminar una transacción
    public void eliminarTransaccion(String id) {
        try {
            collection.deleteOne(eq("_id", new ObjectId(id)));
            System.out.println("✅ Transacción eliminada correctamente.");
        } catch (IllegalArgumentException e) {
            System.err.println("❌ Error: No se pudo eliminar la transacción - " + e.getMessage());
        }
    }
}
