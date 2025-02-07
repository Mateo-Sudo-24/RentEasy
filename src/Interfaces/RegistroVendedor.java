package Interfaces;

import DAO.UsuarioDAO;
import Roles.Vendedor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroVendedor extends JFrame {
    private JPanel PanelRV;
    private JTextField TFRVNombre, TFRVCorreo, TFRVEmpre, TFRVContra, TFRVCContra;
    private JButton resgistrateButton, buttonrcretorno;
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public RegistroVendedor() {
        setTitle("Registro Vendedor - RentEasy");
        setSize(800, 600); // ✅ Ajuste de tamaño para mejor visibilidad
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        PanelRV = new JPanel();
        PanelRV.setLayout(new GridLayout(7, 2, 10, 10));

        TFRVNombre = new JTextField();
        TFRVCorreo = new JTextField();
        TFRVEmpre = new JTextField();
        TFRVContra = new JPasswordField();
        TFRVCContra = new JPasswordField();
        resgistrateButton = new JButton("Registrarse");
        buttonrcretorno = new JButton("Volver");

        PanelRV.add(new JLabel("Nombre:"));
        PanelRV.add(TFRVNombre);
        PanelRV.add(new JLabel("Correo:"));
        PanelRV.add(TFRVCorreo);
        PanelRV.add(new JLabel("Empresa:"));
        PanelRV.add(TFRVEmpre);
        PanelRV.add(new JLabel("Contraseña:"));
        PanelRV.add(TFRVContra);
        PanelRV.add(new JLabel("Confirmar Contraseña:"));
        PanelRV.add(TFRVCContra);
        PanelRV.add(resgistrateButton);
        PanelRV.add(buttonrcretorno);

        setContentPane(PanelRV);
        setVisible(true);

        resgistrateButton.addActionListener(e -> registrarVendedor());
        buttonrcretorno.addActionListener(e -> {
            dispose();
            new SeleccionRegistros().setVisible(true);
        });
    }

    private Vendedor obtenerVendedorDesdeFormulario() {
        String nombre = TFRVNombre.getText().trim();
        String correo = TFRVCorreo.getText().trim();
        String empresa = TFRVEmpre.getText().trim();
        String contraseña = new String(((JPasswordField) TFRVContra).getPassword()).trim();
        String confirmarContraseña = new String(((JPasswordField) TFRVCContra).getPassword()).trim();

        if (nombre.isEmpty() || correo.isEmpty() || empresa.isEmpty() || contraseña.isEmpty() || confirmarContraseña.isEmpty()) {
            throw new IllegalArgumentException("⚠ Todos los campos son obligatorios.");
        }

        if (!contraseña.equals(confirmarContraseña)) {
            throw new IllegalArgumentException("❌ Las contraseñas no coinciden.");
        }

        return new Vendedor(null, nombre, correo, contraseña, empresa);
    }

    private void registrarVendedor() {
        try {
            Vendedor vendedor = obtenerVendedorDesdeFormulario();
            if (usuarioDAO.registrarUsuario(vendedor)) {
                JOptionPane.showMessageDialog(this, "✅ Vendedor registrado con éxito.");
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