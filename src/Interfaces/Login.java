package Interfaces;

import DAO.UsuarioDAO;
import Roles.Usuario;
import javax.swing.*;

public class Login extends JFrame {
    public JPanel PanelGeneral;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton INGRESARButton;
    private JLabel TituloLogin;
    private JLabel Correo;
    private JLabel Contraseña;
    private JPanel PanelLogin;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Login() {
        setTitle("Login - RentEasy");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(PanelGeneral);
        setVisible(true);

        INGRESARButton.addActionListener(e -> autenticarUsuario());
    }

    // ✅ Método para autenticar usuario antes de abrir cualquier Dashboard
    private void autenticarUsuario() {
        String correo = textField1.getText();
        String contraseña = new String(passwordField1.getPassword());

        Usuario usuario = usuarioDAO.buscarPorCorreo(correo);

        if (usuario != null && usuario.getContraseña().equals(contraseña)) {
            JOptionPane.showMessageDialog(this, "Bienvenido " + usuario.getNombre());
            abrirDashboard(usuario);
        } else {
            JOptionPane.showMessageDialog(this, "Correo o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ✅ Solo abre el Dashboard correspondiente si el usuario está autenticado
    private void abrirDashboard(Usuario usuario) {
        dispose(); // Cierra la ventana de login

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
                JOptionPane.showMessageDialog(this, "Rol no reconocido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
