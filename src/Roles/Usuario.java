package Roles;

public abstract class Usuario {
    protected String id;
    protected String nombre;
    protected String correo;
    protected String contraseña;
    protected String rol; // 🔹 Nuevo campo agregado

    public Usuario(String id, String nombre, String correo, String contraseña, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    // ✅ Método común a todos los usuarios
    public void mostrarInfo() {
        System.out.println("Nombre: " + nombre + " | Correo: " + correo + " | Rol: " + rol);
    }

    // 🔹 Método abstracto que las subclases deben implementar
    public abstract void mostrarRol();

    // ✅ Getters y Setters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getContraseña() { return contraseña; }
    public String getRol() { return rol; }
}
