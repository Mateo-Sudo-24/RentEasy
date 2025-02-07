package Modelos;

import java.util.Date;

public class Transaccion {
    private String id;
    private String tipo; // "Compra" o "Renta"
    private String usuarioId; // Cliente que realizó la transacción
    private String casaId; // Casa afectada
    private Date fecha;

    public Transaccion(String id, String tipo, String usuarioId, String casaId) {
        this.id = id;
        this.tipo = tipo;
        this.usuarioId = usuarioId;
        this.casaId = casaId;
        this.fecha = new Date();
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getTipo() { return tipo; }
    public String getUsuarioId() { return usuarioId; }
    public String getCasaId() { return casaId; }
    public Date getFecha() { return fecha; }

    @Override
    public String toString() {
        return "Transaccion{" +
                "id='" + id + '\'' +
                ", tipo='" + tipo + '\'' +
                ", usuarioId='" + usuarioId + '\'' +
                ", casaId='" + casaId + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}
