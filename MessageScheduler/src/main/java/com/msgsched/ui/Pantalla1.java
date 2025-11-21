package com.msgsched.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pantalla1 extends JFrame {
    private JButton btn_send;
    private JPanel panel1;
    private JTextArea txt_message;
    private JCheckBox cb_email;
    private JCheckBox cb_whatsapp;

    public Pantalla1() {
        btn_send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Hola Mundo");
                System.out.println("accion: " + e.getActionCommand());
                boolean cbEmailSelected = cb_email.isSelected();
                boolean cbWhatsAppSelected = cb_whatsapp.isSelected();
                if (!cbEmailSelected && !cbWhatsAppSelected) {
                    JOptionPane.showMessageDialog(null, "Por favor seleccione una opción de envío.");
                    return;
                }
                String message = txt_message.getText();
                if(message.isBlank()) {
                    JOptionPane.showMessageDialog(null, "Por favor ingrese un mensaje.");
                    return;
                }
                System.out.println("Enviar por Email: " + cbEmailSelected);
                System.out.println("Enviar por WhatsApp: " + cbWhatsAppSelected);
                System.out.println("Mensaje: " + message);
            }
        });
    }

    public JPanel getPanel1() {
        return panel1;
    }
}
