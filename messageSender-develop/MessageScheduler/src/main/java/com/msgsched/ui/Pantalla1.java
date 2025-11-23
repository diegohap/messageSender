package com.msgsched.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.msgsched.entity.Reminder;
import com.msgsched.repository.MessageDAO;
import com.msgsched.repository.impl.MessageDAOImpl;
import com.msgsched.config.ConnectionDB;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Pantalla1 extends JFrame {
    private JButton btn_send;
    private JPanel panel1;
    private JTextArea txt_message;
    private JCheckBox cb_email;
    private JCheckBox cb_whatsapp;

    public Pantalla1() {


        setTitle("Message Scheduler");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // centrar
        setLayout(new BorderLayout(10, 10)); // BorderLayout con espacio horizontal/vertical


        JLabel lblMensaje = new JLabel("Mensaje:");
        add(lblMensaje, BorderLayout.NORTH);



        txt_message = new JTextArea(5, 20);
        txt_message.setLineWrap(true);
        txt_message.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txt_message);
        add(scrollPane, BorderLayout.CENTER);


        JPanel panelSur = new JPanel();

        cb_email = new JCheckBox("Email");

        cb_whatsapp = new JCheckBox("WhatsApp");

        btn_send = new JButton("Enviar");
        btn_send.setOpaque(true);
        btn_send.setBackground(Color.GREEN);


        panelSur.add(cb_email);
        panelSur.add(cb_whatsapp);
        panelSur.add(btn_send);

        add(panelSur, BorderLayout.SOUTH);

        getContentPane().setBackground(new Color(230, 240, 255));

        // ActionListener del botón
        btn_send.addActionListener(e -> {
            String messageText = txt_message.getText();
            boolean cbEmailSelected = cb_email.isSelected();
            boolean cbWhatsAppSelected = cb_whatsapp.isSelected();

            if (messageText.isBlank()) {
                JOptionPane.showMessageDialog(null, "Por favor ingrese un mensaje.");
                return;
            }
            if (!cbEmailSelected && !cbWhatsAppSelected) {
                JOptionPane.showMessageDialog(null, "Por favor seleccione una opción de envío.");
                return;
            }

            Reminder reminder = Reminder.builder()
                    .message(messageText)
                    .send(LocalDateTime.now())
                    .email(cbEmailSelected)
                    .whatsapp(cbWhatsAppSelected)
                    .state(false)
                    .build();

            try {
                MessageDAO dao = new MessageDAOImpl(ConnectionDB.getConnection());
                dao.save(reminder);
                JOptionPane.showMessageDialog(null, "Mensaje guardado correctamente!");
                // reiniciar campos
                txt_message.setText("");
                cb_email.setSelected(false);
                cb_whatsapp.setSelected(false);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al guardar el mensaje: " + ex.getMessage());
            }
        });

        setVisible(true);
    }

        /*btn_send.addActionListener(new ActionListener() {
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
        });*/


    public JPanel getPanel1() {
        return panel1;
    }
}
