package Roles;

public class Cliente extends Usuario {
    public Cliente(String id, String nombre, String correo, String contraseña) {
        super(id, nombre, correo, contraseña, "Cliente");
    }

    @Override
    public void mostrarRol() {
        System.out.println("🔹 Soy un Cliente.");
    }

    public void buscarCasas() {
        System.out.println("Buscando casas en la plataforma...");
    }
}
