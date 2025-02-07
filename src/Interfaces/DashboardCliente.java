package Interfaces;

import DAO.CasaDAO;
import Modelos.Casa;
import Roles.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DashboardCliente extends JFrame {
    private final CasaDAO casaDAO = new CasaDAO();
    private final DefaultTableModel tableModel;

    private JTable tablacasas;
    private JButton seleccionarCasaButton;
    private JButton comprarCasaButton;
    private JButton rentarCasaButton;
    private JLabel tituloDC;
    private JPanel Descrip2;
    private JLabel descrip1;
    private JButton btnretorno;

    public DashboardCliente(Usuario cliente) {
        setTitle("Dashboard Cliente - " + cliente.getNombre());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ✅ Configurar la tabla
        tableModel = new DefaultTableModel();
        tablacasas = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tablacasas);

        // ✅ Agregar columnas a la tabla
        tableModel.setColumnIdentifiers(new String[]{"ID", "Dirección", "Precio", "Estado", "PropietarioID"});
        cargarCasasDisponibles(); // 🔹 Cargar datos al abrir la ventana

        // ✅ Configurar panel de botones
        JPanel panelBotones = new JPanel();
        seleccionarCasaButton = new JButton("Seleccionar Casa");
        comprarCasaButton = new JButton("Comprar Casa");
        rentarCasaButton = new JButton("Rentar Casa");
        btnretorno = new JButton("🔙 Regresar");

        panelBotones.add(seleccionarCasaButton);
        panelBotones.add(comprarCasaButton);
        panelBotones.add(rentarCasaButton);
        panelBotones.add(btnretorno); // ✅ Se agrega el botón de retorno al panel

        // ✅ Configurar panel de tabla y descripción
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        Descrip2 = new JPanel();
        descrip1 = new JLabel("Bienvenido, selecciona una casa para comprar o rentar.");
        Descrip2.add(descrip1);

        // ✅ Agregar componentes a la ventana
        add(tituloDC, BorderLayout.NORTH);
        add(Descrip2, BorderLayout.NORTH);
        add(panelTabla, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // ✅ Configurar Listeners con `ActionListener`
        seleccionarCasaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarCasa();
            }
        });

        rentarCasaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rentarCasa(cliente);
            }
        });

        comprarCasaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comprarCasa(cliente);
            }
        });

        btnretorno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra la ventana actual
                new Login().setVisible(true); // ✅ Retorna al Login
            }
        });

        setVisible(true);
    }

    // ✅ Método para cargar casas disponibles en la tabla con seguridad y verificación
    private void cargarCasasDisponibles() {
        tableModel.setRowCount(0); // 🔹 Limpiar tabla antes de agregar datos
        List<Casa> casas = casaDAO.listarCasasDisponibles();

        // 🔹 Verificar si hay casas disponibles antes de actualizar la tabla
        if (casas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay casas disponibles en este momento.", "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Casa casa : casas) {
                tableModel.addRow(new Object[]{
                        casa.getId(),   // 🔹 Mostrar el ID sin la estructura JSON
                        casa.getDireccion(),
                        casa.getPrecio(),
                        casa.getEstado(),
                        casa.getPropietarioId()
                });
            }
        }

        // ✅ Asegurar que el modelo se asigne correctamente a la tabla
        tablacasas.setModel(tableModel);
    }

    private void seleccionarCasa() {
        int filaSeleccionada = tablacasas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una casa primero.");
            return;
        }

        String idCasa = (String) tableModel.getValueAt(filaSeleccionada, 0);
        JOptionPane.showMessageDialog(this, "Casa seleccionada con ID: " + idCasa);
    }

    private void rentarCasa(Usuario cliente) {
        int filaSeleccionada = tablacasas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una casa para rentar.");
            return;
        }

        String idCasa = (String) tableModel.getValueAt(filaSeleccionada, 0);
        String estadoActual = (String) tableModel.getValueAt(filaSeleccionada, 3);

        // ✅ Verificar que la casa esté disponible
        if (!estadoActual.equalsIgnoreCase("Disponible")) {
            JOptionPane.showMessageDialog(this, "Esta casa ya está ocupada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ✅ Actualizar la base de datos
        casaDAO.actualizarEstadoCasa(idCasa, "Rentada");
        JOptionPane.showMessageDialog(this, cliente.getNombre() + " ha rentado la casa con ID: " + idCasa);

        // 🔹 Recargar la tabla
        cargarCasasDisponibles();
    }

    private void comprarCasa(Usuario cliente) {
        int filaSeleccionada = tablacasas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una casa para comprar.");
            return;
        }

        String idCasa = (String) tableModel.getValueAt(filaSeleccionada, 0);
        String estadoActual = (String) tableModel.getValueAt(filaSeleccionada, 3);

        // ✅ Verificar que la casa esté disponible
        if (!estadoActual.equalsIgnoreCase("Disponible")) {
            JOptionPane.showMessageDialog(this, "Esta casa ya está ocupada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ✅ Actualizar la base de datos
        casaDAO.actualizarEstadoCasa(idCasa, "Vendida");
        JOptionPane.showMessageDialog(this, cliente.getNombre() + " ha comprado la casa con ID: " + idCasa);

        // 🔹 Recargar la tabla
        cargarCasasDisponibles();
    }
}
