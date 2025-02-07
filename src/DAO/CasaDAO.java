package DAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import Modelos.Casa;
import Conexion.Conexion;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class CasaDAO {
    private MongoCollection<Document> collection;

    public CasaDAO() {
        MongoDatabase db = Conexion.getInstance().getDatabase();
        collection = db.getCollection("casas");
    }

    // ✅ Registrar una casa con ObjectId
    public void registrarCasa(Casa casa) {
        Document doc = new Document("_id", new ObjectId()) // Mongo genera el ObjectId automáticamente
                .append("direccion", casa.getDireccion())
                .append("precio", casa.getPrecio())
                .append("estado", casa.getEstado()) // Disponible, Vendida, Rentada
                .append("propietarioId", casa.getPropietarioId());

        collection.insertOne(doc);
    }

    // ✅ Buscar una casa por ID
    public Casa buscarCasaPorId(String id) {
        Document query = new Document("_id", new ObjectId(id));
        Document doc = collection.find(query).first();

        if (doc != null) {
            return new Casa(
                    doc.getObjectId("_id").toString(),
                    doc.getString("direccion"),
                    doc.getDouble("precio"),
                    doc.getString("estado"),
                    doc.getString("propietarioId")
            );
        }
        return null;
    }

    // ✅ Listar todas las casas disponibles
    public List<Casa> listarCasasDisponibles() {
        List<Casa> casas = new ArrayList<>();

        for (Document doc : collection.find(new Document("estado", "Disponible"))) {
            casas.add(new Casa(
                    doc.getObjectId("_id").toString(),
                    doc.getString("direccion"),
                    doc.getDouble("precio"),
                    doc.getString("estado"),
                    doc.getString("propietarioId")
            ));
        }

        return casas;
    }

    // ✅ Actualizar estado de una casa
    public void actualizarEstadoCasa(String id, String nuevoEstado) {
        Document query = new Document("_id", new ObjectId(id));
        Document update = new Document("$set", new Document("estado", nuevoEstado));
        collection.updateOne(query, update);
    }

    // ✅ Eliminar una casa por ID
    public void eliminarCasa(String id) {
        Document query = new Document("_id", new ObjectId(id));
        collection.deleteOne(query);
    }
}
