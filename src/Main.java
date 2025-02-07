import Interfaces.Login;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // ✅ Configuración para que la interfaz gráfica use el tema del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("No se pudo establecer el tema de la interfaz: " + e.getMessage());
        }

        // ✅ Ejecutar la aplicación en el hilo de la GUI de Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login();
            }
        });
    }
}
