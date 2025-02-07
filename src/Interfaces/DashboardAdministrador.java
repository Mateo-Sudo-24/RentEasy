package Interfaces;

import Roles.Administrador;
import javax.swing.*;

public class DashboardAdministrador extends JFrame {
    private Administrador admin;

    public DashboardAdministrador(Administrador admin) {
        if (admin == null) {
            JOptionPane.showMessageDialog(null, "Acceso denegado. Inicia sesión primero.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0); // Cierra la aplicación si no hay sesión válida
        }

        this.admin = admin;
        setTitle("Dashboard Administrador - " + admin.getNombre());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
