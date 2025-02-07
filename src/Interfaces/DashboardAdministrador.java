package Interfaces;

import DAO.CasaDAO;
import DAO.UsuarioDAO;
import DAO.TransaccionDAO;
import Modelos.Casa;
import Modelos.Transaccion;
import Roles.Usuario;
import Roles.Cliente;
import Roles.Vendedor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DashboardAdministrador extends JFrame {
    private final Usuario admin;
    private final DefaultTableModel tableModel = new DefaultTableModel();
    private String tablaActual = "";

    private JPanel panelBotones, panelCRUD;
    private JButton btnMostrarCasas, btnMostrarClientes, btnMostrarVendedores, btnMostrarTransacciones;
    private JButton btnAgregar, btnEditar, btnEliminar, btnRegresarLogin;
    private JTable TablaMostrarDatos;
    private JLabel Titulo;

    private final CasaDAO casaDAO = new CasaDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final TransaccionDAO transaccionDAO = new TransaccionDAO();

    public DashboardAdministrador(Usuario admin) {
        if (admin == null) {
            JOptionPane.showMessageDialog(null, "Acceso denegado. Inicia sesión primero.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        this.admin = admin;
        setTitle("Dashboard Administrador - " + admin.getNombre());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        btnMostrarCasas = new JButton("Mostrar Casas");
        btnMostrarClientes = new JButton("Mostrar Clientes");
        btnMostrarVendedores = new JButton("Mostrar Vendedores");
        btnMostrarTransacciones = new JButton("Mostrar Transacciones");
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnRegresarLogin = new JButton("Regresar al Login");

        panelBotones = new JPanel();
        panelBotones.add(btnMostrarCasas);
        panelBotones.add(btnMostrarClientes);
        panelBotones.add(btnMostrarVendedores);
        panelBotones.add(btnMostrarTransacciones);
        add(panelBotones, BorderLayout.NORTH);

        panelCRUD = new JPanel();
        panelCRUD.add(btnAgregar);
        panelCRUD.add(btnEditar);
        panelCRUD.add(btnEliminar);
        panelCRUD.add(btnRegresarLogin);
        add(panelCRUD, BorderLayout.SOUTH);

        TablaMostrarDatos = new JTable();
        TablaMostrarDatos.setModel(tableModel);
        JScrollPane scrollPane = new JScrollPane(TablaMostrarDatos);
        add(scrollPane, BorderLayout.CENTER);

        btnMostrarCasas.addActionListener(e -> cargarCasas());
        btnMostrarClientes.addActionListener(e -> cargarClientes());
        btnMostrarVendedores.addActionListener(e -> cargarVendedores());
        btnMostrarTransacciones.addActionListener(e -> cargarTransacciones());
        btnAgregar.addActionListener(e -> agregarRegistro());
        btnEditar.addActionListener(e -> editarRegistro());
        btnEliminar.addActionListener(e -> eliminarRegistro());
        btnRegresarLogin.addActionListener(e -> regresarAlLogin());

        cargarCasas();
        setVisible(true);
    }

    private void cargarCasas() {
        tablaActual = "Casas";
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new String[]{"ID", "Dirección", "Precio", "Estado"});
        List<Casa> casas = casaDAO.listarCasas();
        for (Casa casa : casas) {
            tableModel.addRow(new Object[]{casa.getId(), casa.getDireccion(), casa.getPrecio(), casa.getEstado()});
        }
    }

    private void cargarClientes() {
        tablaActual = "Clientes";
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new String[]{"ID", "Nombre", "Correo"});
        List<Cliente> clientes = usuarioDAO.listarClientes();
        for (Cliente cliente : clientes) {
            tableModel.addRow(new Object[]{cliente.getId(), cliente.getNombre(), cliente.getCorreo()});
        }
    }

    private void cargarVendedores() {
        tablaActual = "Vendedores";
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new String[]{"ID", "Nombre", "Correo", "Empresa"});
        List<Vendedor> vendedores = usuarioDAO.listarVendedores();
        for (Vendedor vendedor : vendedores) {
            tableModel.addRow(new Object[]{vendedor.getId(), vendedor.getNombre(), vendedor.getCorreo(), vendedor.getEmpresa()});
        }
    }

    private void cargarTransacciones() {
        tablaActual = "Transacciones";
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new String[]{"ID", "Tipo", "Usuario", "Casa", "Fecha"});
        List<Transaccion> transacciones = transaccionDAO.buscarTodasTransacciones();
        for (Transaccion transaccion : transacciones) {
            tableModel.addRow(new Object[]{transaccion.getId(), transaccion.getTipo(), transaccion.getUsuarioId(), transaccion.getCasaId(), transaccion.getFecha()});
        }
    }

    private void agregarRegistro() {
        if (tablaActual.equals("Casas")) {
            String direccion = JOptionPane.showInputDialog("Ingrese la dirección:");
            double precio = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio:"));
            casaDAO.registrarCasa(new Casa(null, direccion, precio, "Disponible", null));
            cargarCasas();
        }
    }

    private void editarRegistro() {
        int selectedRow = TablaMostrarDatos.getSelectedRow();
        if (selectedRow != -1 && tablaActual.equals("Casas")) {
            String id = (String) tableModel.getValueAt(selectedRow, 0);
            String nuevaDireccion = JOptionPane.showInputDialog("Nueva dirección:");
            double nuevoPrecio = Double.parseDouble(JOptionPane.showInputDialog("Nuevo precio:"));
            casaDAO.actualizarCasa(new Casa(id, nuevaDireccion, nuevoPrecio, "Disponible", null));
            cargarCasas();
        }
    }

    private void eliminarRegistro() {
        int selectedRow = TablaMostrarDatos.getSelectedRow();
        if (selectedRow != -1 && tablaActual.equals("Casas")) {
            String id = (String) tableModel.getValueAt(selectedRow, 0);
            casaDAO.eliminarCasa(id);
            cargarCasas();
        }
    }

    private void regresarAlLogin() {
        this.dispose();
        new Login().setVisible(true);
    }
}
