package DAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import Modelos.Casa;
import Conexion.Conexion;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class CasaDAO {
    private final MongoCollection<Document> collection;

    public CasaDAO() {
        MongoDatabase db = Conexion.getInstance().getDatabase();
        collection = db.getCollection("casas");
    }

    // ✅ **Registrar una casa en MongoDB**
    public void registrarCasa(Casa casa) {
        Document doc = new Document("_id", new ObjectId()) // Mongo genera el ObjectId automáticamente
                .append("direccion", casa.getDireccion())
                .append("precio", casa.getPrecio())
                .append("estado", casa.getEstado()); // Disponible, Vendida, Rentada

        // 🔹 Agrega propietario solo si no es nulo
        if (casa.getPropietarioId() != null) {
            doc.append("propietarioId", casa.getPropietarioId());
        }

        collection.insertOne(doc);
        System.out.println("✅ Casa registrada en MongoDB con ID: " + doc.getObjectId("_id"));
    }

    // ✅ **Buscar una casa por ID**
    public Casa buscarCasaPorId(String id) {
        try {
            Document doc = collection.find(eq("_id", new ObjectId(id))).first();
            if (doc != null) {
                return new Casa(
                        doc.getObjectId("_id").toString(),
                        doc.getString("direccion"),
                        doc.getDouble("precio"),
                        doc.getString("estado"),
                        doc.getString("propietarioId")
                );
            }
        } catch (IllegalArgumentException e) {
            System.err.println("❌ Error: El ID de la casa no es válido - " + e.getMessage());
        }
        return null;
    }

    // ✅ Método corregido para listar todas las casas
    public List<Casa> listarCasas() {
        List<Casa> casas = new ArrayList<>();
        for (Document doc : collection.find()) {
            double precio;
            Object precioObj = doc.get("precio"); // Obtiene el valor desde MongoDB

            if (precioObj instanceof Integer) {
                precio = ((Integer) precioObj).doubleValue(); // Convierte Integer a Double
            } else {
                precio = (double) precioObj; // Si ya es Double, lo asigna directamente
            }

            casas.add(new Casa(
                    doc.getObjectId("_id").toString(),
                    doc.getString("direccion"),
                    precio,  // ✅ Manejo de Integer y Double correctamente
                    doc.getString("estado"),
                    doc.getString("propietarioId")
            ));
        }
        return casas;
    }

    // ✅ Método corregido para listar solo casas disponibles
    public List<Casa> listarCasasDisponibles() {
        List<Casa> casas = new ArrayList<>();
        for (Document doc : collection.find(eq("estado", "Disponible"))) {
            double precio;
            Object precioObj = doc.get("precio"); // Obtiene el valor desde MongoDB

            if (precioObj instanceof Integer) {
                precio = ((Integer) precioObj).doubleValue(); // Convierte Integer a Double
            } else {
                precio = (double) precioObj; // Si ya es Double, lo asigna directamente
            }

            casas.add(new Casa(
                    doc.getObjectId("_id").toString(),
                    doc.getString("direccion"),
                    precio,  // ✅ Manejo de Integer y Double correctamente
                    doc.getString("estado"),
                    doc.getString("propietarioId")
            ));
        }
        return casas;
    }


    // ✅ **Actualizar datos de una casa**
    public void actualizarCasa(Casa casa) {
        try {
            Document query = new Document("_id", new ObjectId(casa.getId()));
            Document update = new Document("$set", new Document("direccion", casa.getDireccion())
                    .append("precio", casa.getPrecio())
                    .append("estado", casa.getEstado()));

            // 🔹 Agrega propietario solo si no es nulo
            if (casa.getPropietarioId() != null) {
                update.append("$set", new Document("propietarioId", casa.getPropietarioId()));
            }

            collection.updateOne(query, update);
            System.out.println("✅ Casa actualizada correctamente.");
        } catch (IllegalArgumentException e) {
            System.err.println("❌ Error: No se pudo actualizar la casa - " + e.getMessage());
        }
    }

    // ✅ **Actualizar solo el estado de una casa**
    public boolean actualizarEstadoCasa(String id, String nuevoEstado) {
        try {
            Document query = new Document("_id", new ObjectId(id));
            Document update = new Document("$set", new Document("estado", nuevoEstado));
            collection.updateOne(query, update);
            System.out.println("✅ Estado de la casa actualizado a: " + nuevoEstado);
            return true;
        } catch (IllegalArgumentException e) {
            System.err.println("❌ Error: No se pudo actualizar el estado de la casa - " + e.getMessage());
            return false;
        }
    }

    // ✅ **Eliminar una casa por ID**
    public boolean eliminarCasa(String id) {
        try {
            collection.deleteOne(eq("_id", new ObjectId(id)));
            System.out.println("✅ Casa eliminada correctamente.");
            return true;
        } catch (IllegalArgumentException e) {
            System.err.println("❌ Error: No se pudo eliminar la casa - " + e.getMessage());
            return false;
        }
    }
}