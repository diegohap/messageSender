package com.msgsched.ui;

import com.msgsched.config.ConnectionDB;
import com.msgsched.entity.Reminder;
import com.msgsched.repository.MessageDAO;
import com.msgsched.repository.impl.MessageDAOImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.util.Objects.isNull;

public class Pantalla1 extends JFrame {
    private JButton btn_send;
    private JPanel panel1;
    private JTextArea txt_message;
    private JCheckBox cb_email;
    private JCheckBox cb_whatsapp;
    private JComboBox<Integer> cmb_day;
    private JComboBox<Integer> cmb_month;
    private JComboBox<Integer> cmb_year;
    private JComboBox<Integer> cmb_hr;
    private JComboBox<Integer> cmb_min;
    private JTextField txt_email;
    private JTextField txt_phone;

    public Pantalla1() {

        if (isNull(cmb_day)) cmb_day = new JComboBox<Integer>();
        if (isNull(cmb_month)) cmb_month = new JComboBox<Integer>();
        if (isNull(cmb_year)) cmb_year = new JComboBox<Integer>();
        if (isNull(cmb_hr)) cmb_hr = new JComboBox<Integer>();
        if (isNull(cmb_min)) cmb_min = new JComboBox<Integer>();

        for (int i = 0; i <= 3; i++) {
            cmb_year.addItem(LocalDate.now().getYear() + i);
        }

        cmb_year.setSelectedIndex(0);
        cmb_month.setSelectedIndex(LocalDateTime.now().getMonthValue() - 1);
        cmb_day.setSelectedIndex(LocalDateTime.now().getDayOfMonth() - 1);

        cmb_hr.setSelectedIndex(LocalDateTime.now().getHour());
        cmb_min.setSelectedIndex(LocalDateTime.now().getMinute());

        updateDays();

        btn_send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

                //"2024-06-10 10:00"

                int day = Integer.valueOf(cmb_day.getSelectedItem().toString());
                int month = Integer.valueOf(cmb_month.getSelectedItem().toString());
                int year = Integer.valueOf(cmb_year.getSelectedItem().toString());
                int hr = Integer.valueOf(cmb_hr.getSelectedItem().toString());
                int min = Integer.valueOf(cmb_min.getSelectedItem().toString());

                String email = txt_email.getText();
                String phone = txt_phone.getText();

                Reminder reminder = Reminder.builder()
                        .message(message)
                        .send(LocalDate.of(year, month, day).atTime(hr, min))
                        .email(cbEmailSelected)
                        .whatsapp(cbWhatsAppSelected)
                        .emailAddress(email)
                        .phoneNumber(phone)
                        .state(false)
                        .build();
                saveMessage(reminder);

                System.out.println("Enviar por Email: " + cbEmailSelected);
                System.out.println("Enviar por WhatsApp: " + cbWhatsAppSelected);
                System.out.println("Mensaje: " + message);
            }
        });
        cmb_month.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDays();
            }
        });
        cmb_year.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDays();
            }
        });
    }

    public JPanel getPanel1() {
        return panel1;
    }

    private void saveMessage(Reminder reminder) {
        try {
            MessageDAO dao = new MessageDAOImpl(ConnectionDB.getConnection());
            dao.save(reminder);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateDays() {
        int selectedMonth = Integer.valueOf(cmb_month.getSelectedItem().toString());
        int selectedYear = Integer.valueOf(cmb_year.getSelectedItem().toString());

        int daysInMonth;

        switch (selectedMonth) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                daysInMonth = 31;
                break;
            case 4: case 6: case 9: case 11:
                daysInMonth = 30;
                break;
            case 2:
                daysInMonth = java.time.Year.isLeap(selectedYear) ? 29 : 28;
                break;
            default:
                daysInMonth = 30;
        }

        cmb_day.removeAllItems();
        for (int i = 1; i <= daysInMonth; i++) {
            cmb_day.addItem(i);
        }
    }

}
