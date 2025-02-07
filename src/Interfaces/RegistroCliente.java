package Interfaces;

import DAO.UsuarioDAO;
import Roles.Cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroCliente extends JFrame {
    private JPanel PanelRC;
    private JTextField TFRCnom, TFRCcor, TFRCcontra, TFRCCContra;
    private JButton buttonRC, buttonrcretorno;
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public RegistroCliente() {
        setTitle("Registro Cliente - RentEasy");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        if (PanelRC == null) {
            PanelRC = new JPanel(); // ✅ Asegura que el JPanel está inicializado
        }
        setContentPane(PanelRC);
        setVisible(true);

        buttonRC.addActionListener(e -> registrarCliente());

        buttonrcretorno.addActionListener(e -> {
            dispose();
            new SeleccionRegistros().setVisible(true);
        });
    }

    private Cliente obtenerClienteDesdeFormulario() {
        String nombre = TFRCnom.getText().trim();
        String correo = TFRCcor.getText().trim();
        String contraseña = TFRCcontra.getText().trim();
        String confirmarContraseña = TFRCCContra.getText().trim();

        if (nombre.isEmpty() || correo.isEmpty() || contraseña.isEmpty() || confirmarContraseña.isEmpty()) {
            throw new IllegalArgumentException("Todos los campos son obligatorios.");
        }

        if (!contraseña.equals(confirmarContraseña)) {
            throw new IllegalArgumentException("Las contraseñas no coinciden.");
        }

        return new Cliente(null, nombre, correo, contraseña);
    }

    private void registrarCliente() {
        try {
            Cliente cliente = obtenerClienteDesdeFormulario();
            if (usuarioDAO.registrarUsuario(cliente)) {
                JOptionPane.showMessageDialog(this, "✅ Cliente registrado con éxito.");
                dispose();
                new Login().setVisible(true);
            } else {
                throw new IllegalArgumentException("❌ El correo ya está en uso.");
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
