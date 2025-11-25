package com.msgsched.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.msgsched.config.ConnectionDB;
import com.msgsched.entity.Reminder;
import com.msgsched.repository.MessageDAO;
import com.msgsched.repository.impl.MessageDAOImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

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
                try {
                    Connection conn = ConnectionDB.getConnection();
                    MessageDAO dao = new MessageDAOImpl(conn);

                    Reminder reminder = Reminder.builder()
                            .message(message)
                            .email(cbEmailSelected)
                            .whatsapp(cbWhatsAppSelected)
                            .send(LocalDateTime.now().plusSeconds(10)) // prueba
                            .state(false)
                            .build();

                    dao.save(reminder);

                    JOptionPane.showMessageDialog(null, "Mensaje guardado en la DB!");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "Error guardando mensaje: " + ex.getMessage());
                }
            }
        });
    }

    public JPanel getPanel1() {
        return panel1;
    }
}
