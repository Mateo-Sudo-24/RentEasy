package DAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import Roles.Administrador;
import Roles.Cliente;
import Roles.Usuario;
import Roles.Vendedor;
import Conexion.Conexion;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class UsuarioDAO {
    private MongoCollection<Document> collection;

    public UsuarioDAO() {
        MongoDatabase db = Conexion.getInstance().getDatabase();
        collection = db.getCollection("usuarios");
    }

    // ✅ Registrar usuario con verificación de duplicados
    public boolean registrarUsuario(Usuario usuario) {
        if (buscarPorCorreo(usuario.getCorreo()) != null) {
            System.out.println("❌ Error: El correo ya está registrado.");
            return false;
        }

        Document doc = new Document("_id", new ObjectId())
                .append("nombre", usuario.getNombre())
                .append("correo", usuario.getCorreo())
                .append("contraseña", usuario.getContraseña())
                .append("rol", usuario.getRol());

        if (usuario instanceof Vendedor) {
            doc.append("empresa", ((Vendedor) usuario).getEmpresa());
        }

        collection.insertOne(doc);
        return true;
    }

    // ✅ Buscar usuario por correo
    public Usuario buscarPorCorreo(String correo) {
        Document doc = collection.find(eq("correo", correo)).first();

        if (doc != null) {
            String id = doc.getObjectId("_id").toString();
            String nombre = doc.getString("nombre");
            String contraseña = doc.getString("contraseña");
            String rol = doc.getString("rol");

            switch (rol) {
                case "Administrador":
                    return new Administrador(id, nombre, correo, contraseña);
                case "Vendedor":
                    return new Vendedor(id, nombre, correo, contraseña, doc.getString("empresa") != null ? doc.getString("empresa") : "Sin empresa");
                case "Cliente":
                    return new Cliente(id, nombre, correo, contraseña);
                default:
                    return null;
            }
        }
        return null;
    }
}
