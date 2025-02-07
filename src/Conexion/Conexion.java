package Conexion;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Conexion {
    private static Conexion instance;
    private MongoClient mongoClient;
    private MongoDatabase database;

    private Conexion() {
        try {
            String uri = "mongodb://localhost:27017"; // Se recomienda mover a un archivo de configuración
            mongoClient = MongoClients.create(uri);
            database = mongoClient.getDatabase("RentEasyDB");
            System.out.println("✅ Conectado a MongoDB correctamente.");
        } catch (Exception e) {
            System.err.println("❌ Error al conectar con MongoDB: " + e.getMessage());
        }
    }

    public static Conexion getInstance() {
        if (instance == null) {
            instance = new Conexion();
        }
        return instance;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("🔌 Conexión cerrada correctamente.");
        }
    }

    public static void main(String[] args) {
        Conexion conexion = Conexion.getInstance();
        System.out.println("Base de datos: " + conexion.getDatabase().getName());
        conexion.closeConnection();
    }
}

