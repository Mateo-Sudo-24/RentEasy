package Modelos;

public class Transaccion {
    private String id;
    private String tipo; // Compra o Renta
    private String usuarioId;
    private String casaId;
    private String fecha;

    public Transaccion(String id, String tipo, String usuarioId, String casaId, String fecha) {
        this.id = id;
        this.tipo = tipo;
        this.usuarioId = usuarioId;
        this.casaId = casaId;
        this.fecha = fecha;
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getTipo() { return tipo; }
    public String getUsuarioId() { return usuarioId; }
    public String getCasaId() { return casaId; }
    public String getFecha() { return fecha; }

    public void setId(String id) { this.id = id; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
    public void setCasaId(String casaId) { this.casaId = casaId; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    @Override
    public String toString() {
        return "Transaccion{" +
                "id='" + id + '\'' +
                ", tipo='" + tipo + '\'' +
                ", usuarioId='" + usuarioId + '\'' +
                ", casaId='" + casaId + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
