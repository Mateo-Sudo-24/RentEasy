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
                .append("rol", usuario.getClass().getSimpleName());

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
                    return new Vendedor(id, nombre, correo, contraseña, doc.getString("empresa"));
                case "Cliente":
                    return new Cliente(id, nombre, correo, contraseña);
                default:
                    return null;
            }
        }
        return null;
    }

    // ✅ Buscar usuario por ID
    public Usuario buscarPorId(String id) {
        Document doc = collection.find(eq("_id", new ObjectId(id))).first();

        if (doc != null) {
            String nombre = doc.getString("nombre");
            String correo = doc.getString("correo");
            String contraseña = doc.getString("contraseña");
            String rol = doc.getString("rol");

            switch (rol) {
                case "Administrador":
                    return new Administrador(id, nombre, correo, contraseña);
                case "Vendedor":
                    return new Vendedor(id, nombre, correo, contraseña, doc.getString("empresa"));
                case "Cliente":
                    return new Cliente(id, nombre, correo, contraseña);
                default:
                    return null;
            }
        }
        return null;
    }

    // ✅ Listar todos los usuarios
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();

        for (Document doc : collection.find()) {
            String id = doc.getObjectId("_id").toString();
            String nombre = doc.getString("nombre");
            String correo = doc.getString("correo");
            String contraseña = doc.getString("contraseña");
            String rol = doc.getString("rol");

            switch (rol) {
                case "Administrador":
                    usuarios.add(new Administrador(id, nombre, correo, contraseña));
                    break;
                case "Vendedor":
                    usuarios.add(new Vendedor(id, nombre, correo, contraseña, doc.getString("empresa")));
                    break;
                case "Cliente":
                    usuarios.add(new Cliente(id, nombre, correo, contraseña));
                    break;
            }
        }

        return usuarios;
    }

    // ✅ Listar solo clientes
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();

        for (Document doc : collection.find(eq("rol", "Cliente"))) {
            clientes.add(new Cliente(
                    doc.getObjectId("_id").toString(),
                    doc.getString("nombre"),
                    doc.getString("correo"),
                    doc.getString("contraseña")
            ));
        }

        return clientes;
    }

    // ✅ Listar solo vendedores
    public List<Vendedor> listarVendedores() {
        List<Vendedor> vendedores = new ArrayList<>();

        for (Document doc : collection.find(eq("rol", "Vendedor"))) {
            vendedores.add(new Vendedor(
                    doc.getObjectId("_id").toString(),
                    doc.getString("nombre"),
                    doc.getString("correo"),
                    doc.getString("contraseña"),
                    doc.getString("empresa")
            ));
        }

        return vendedores;
    }

    // ✅ Actualizar un usuario por ID
    public void actualizarUsuario(String id, String nuevoNombre, String nuevoCorreo, String nuevaContraseña) {
        Document query = new Document("_id", new ObjectId(id));
        Document update = new Document("$set", new Document("nombre", nuevoNombre)
                .append("correo", nuevoCorreo)
                .append("contraseña", nuevaContraseña));

        collection.updateOne(query, update);
        System.out.println("✅ Usuario actualizado correctamente.");
    }

    // ✅ Eliminar usuario por ID
    public void eliminarUsuario(String id) {
        collection.deleteOne(eq("_id", new ObjectId(id)));
        System.out.println("✅ Usuario eliminado correctamente.");
    }
}
