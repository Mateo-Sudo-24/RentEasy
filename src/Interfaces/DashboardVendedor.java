package Interfaces;

import DAO.CasaDAO;
import DAO.TransaccionDAO;
import Modelos.Casa;
import Modelos.Transaccion;
import Roles.Usuario;
import utils.PDFGenerator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DashboardVendedor extends JFrame {
    private final Usuario vendedor;
    private JPanel panelPrincipal;
    private JTable tablacasas;
    private JButton btnAgregarCasa;
    private JButton btnEliminarCasa;
    private JButton btnGenerarPDF;
    private JLabel TituloVen;
    private JScrollPane scrollPane;
    private JButton button1;

    private final CasaDAO casaDAO = new CasaDAO();
    private final TransaccionDAO transaccionDAO = new TransaccionDAO();

    public DashboardVendedor(Usuario vendedor) {
        if (vendedor == null) {
            JOptionPane.showMessageDialog(null, "Acceso denegado. Inicia sesión primero.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        this.vendedor = vendedor;
        setTitle("Dashboard Vendedor - " + vendedor.getNombre());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelPrincipal);

        cargarCasas();

        btnAgregarCasa.addActionListener(e -> agregarCasa());
        btnEliminarCasa.addActionListener(e -> eliminarCasa());
        btnGenerarPDF.addActionListener(e -> generarPDF());
        button1.addActionListener(e -> regresarAlLogin());

        setVisible(true);
    }

    private void cargarCasas() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Dirección", "Precio", "Estado"});
        List<Casa> casas = casaDAO.listarCasasDisponibles();

        for (Casa casa : casas) {
            model.addRow(new Object[]{casa.getId(), casa.getDireccion(), casa.getPrecio(), casa.getEstado()});
        }

        tablacasas.setModel(model);
    }

    private void agregarCasa() {
        String direccion = JOptionPane.showInputDialog("Ingrese la dirección:");
        double precio;
        try {
            precio = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio:"));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un precio válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String estado = JOptionPane.showInputDialog("Ingrese el estado (Disponible, Vendida, Rentada):");

        casaDAO.registrarCasa(new Casa(null, direccion, precio, estado, vendedor.getId()));
        cargarCasas();
    }

    private void eliminarCasa() {
        int selectedRow = tablacasas.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una casa para eliminar.");
            return;
        }

        String id = tablacasas.getValueAt(selectedRow, 0).toString();
        casaDAO.eliminarCasa(id);
        cargarCasas();
    }

    private void generarPDF() {
        StringBuilder listaCasas = new StringBuilder();
        DefaultTableModel model = (DefaultTableModel) tablacasas.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            listaCasas.append("ID: ").append(model.getValueAt(i, 0)).append(" - ")
                    .append("Dirección: ").append(model.getValueAt(i, 1)).append(" - ")
                    .append("Precio: $").append(model.getValueAt(i, 2)).append(" - ")
                    .append("Estado: ").append(model.getValueAt(i, 3)).append("\n");
        }

        if (listaCasas.toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay casas registradas para generar el PDF.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PDFGenerator.generarPDFDesdeDashboardVendedor("DashboardVendedor.pdf", vendedor.getNombre(), listaCasas.toString());
        JOptionPane.showMessageDialog(this, "📄 PDF generado correctamente.");
    }

    private void regresarAlLogin() {
        dispose();
        new Login().setVisible(true);
    }
}
