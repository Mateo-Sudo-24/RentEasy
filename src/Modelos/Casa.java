package Modelos;

public class Casa {
    private String id;
    private String direccion;
    private double precio;
    private String estado; // Disponible, Vendida, Rentada
    private String propietarioId;

    public Casa(String id, String direccion, double precio, String estado, String propietarioId) {
        this.id = id;
        this.direccion = direccion;
        this.precio = precio;
        this.estado = estado;
        this.propietarioId = propietarioId;
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getDireccion() { return direccion; }
    public double getPrecio() { return precio; }
    public String getEstado() { return estado; }
    public String getPropietarioId() { return propietarioId; }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Casa{" +
                "id='" + id + '\'' +
                ", direccion='" + direccion + '\'' +
                ", precio=" + precio +
                ", estado='" + estado + '\'' +
                ", propietarioId='" + propietarioId + '\'' +
                '}';
    }
}
