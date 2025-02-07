package Interfaces;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeleccionRegistros extends JFrame {
    private JPanel PanelSR;
    private JButton buttonrv, buttonRC, buttonRetorno;
    private JLabel Titulo;

    public SeleccionRegistros() {
        setTitle("Seleccionar Tipo de Registro - RentEasy");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(PanelSR);
        setVisible(true);

        // ✅ Agregar Listeners a los botones
        buttonrv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RegistroVendedor().setVisible(true);
            }
        });

        buttonRC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RegistroCliente().setVisible(true);
            }
        });

        buttonRetorno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login().setVisible(true);
            }
        });
    }
}
