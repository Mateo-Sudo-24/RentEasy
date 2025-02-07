package Roles;

public class Administrador extends Usuario {
    public Administrador(String id, String nombre, String correo, String contraseña) {
        super(id, nombre, correo, contraseña, "Administrador"); // 🔹 Rol definido directamente
    }

    @Override
    public void mostrarRol() {
        System.out.println("🔹 Soy un Administrador.");
    }

    public void gestionarUsuarios() {
        System.out.println("Gestionando usuarios...");
    }
}
