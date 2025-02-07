package Roles;

public class Vendedor extends Usuario {
    private String empresa;

    public Vendedor(String id, String nombre, String correo, String contraseña, String empresa) {
        super(id, nombre, correo, contraseña, "Vendedor");
        this.empresa = empresa;
    }

    @Override
    public void mostrarRol() {
        System.out.println("🔹 Soy un Vendedor.");
    }

    public String getEmpresa() { return empresa; }

    public void gestionarPropiedades() {
        System.out.println("Gestionando propiedades para la empresa: " + empresa);
    }
}
