package Interfaces;

import DAO.UsuarioDAO;
import Roles.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JPanel PanelGeneral;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton INGRESARButton;
    private JLabel TituloLogin;
    private JLabel Correo;
    private JLabel Contraseña;
    private JPanel PanelLogin; // 🔹 No se inicializa manualmente, ya lo hace IntelliJ
    private JButton registrateButton;
    private JLabel Login;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Login() {
        // ✅ Asegurar que los componentes se inicialicen antes de ser usados
        if (PanelGeneral == null) {
            JOptionPane.showMessageDialog(null, "⚠ ERROR: `PanelGeneral` no está inicializado en el UI Designer.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        if (PanelLogin == null) {
            JOptionPane.showMessageDialog(null, "⚠ ERROR: `PanelLogin` no está inicializado en el UI Designer.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        setTitle("Login - RentEasy");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(PanelGeneral); // ✅ Solo se usa el Panel generado en IntelliJ
        setVisible(true);

        // ✅ **Listeners con `ActionListener`**
        INGRESARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticarUsuario();
            }
        });

        registrateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirSeleccionRegistro();
            }
        });
    }

    // ✅ **Método para autenticar usuario con manejo de excepciones**
    private void autenticarUsuario() {
        try {
            String correo = textField1.getText().trim();
            String contraseña = new String(passwordField1.getPassword()).trim();

            if (correo.isEmpty() || contraseña.isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠ Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario usuario = usuarioDAO.buscarPorCorreo(correo);

            if (usuario != null && usuario.getContraseña().equals(contraseña)) {
                JOptionPane.showMessageDialog(this, "✅ Bienvenido " + usuario.getNombre(), "Acceso Exitoso", JOptionPane.INFORMATION_MESSAGE);
                abrirDashboard(usuario);
            } else {
                JOptionPane.showMessageDialog(this, "❌ Usuario o contraseña incorrectos.", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "⚠ Ocurrió un error al iniciar sesión: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ✅ **Método para abrir el Dashboard correspondiente**
    private void abrirDashboard(Usuario usuario) {
        dispose();

        switch (usuario.getRol()) {
            case "Administrador":
                new DashboardAdministrador(usuario).setVisible(true);
                break;
            case "Cliente":
                new DashboardCliente(usuario).setVisible(true);
                break;
            case "Vendedor":
                new DashboardVendedor(usuario).setVisible(true);
                break;
            default:
                JOptionPane.showMessageDialog(this, "⚠ Rol no reconocido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ✅ **Método para abrir la pantalla de registro**
    private void abrirSeleccionRegistro() {
        dispose();
        new SeleccionRegistros().setVisible(true);
    }

    // ✅ **Método Main para probar el Login**
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login();
            }
        });
    }
}
