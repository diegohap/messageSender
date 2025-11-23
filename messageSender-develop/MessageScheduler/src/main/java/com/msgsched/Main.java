package com.msgsched;

import com.msgsched.config.ConnectionDB;
import com.msgsched.entity.Reminder;
import com.msgsched.repository.MessageDAO;
import com.msgsched.repository.impl.MessageDAOImpl;
import com.msgsched.service.MessageScheduler;
import com.msgsched.service.MessageSender;
import com.msgsched.service.MessageSenderStrategy;
import com.msgsched.service.domain.Message;
import com.msgsched.service.impl.MessageSchedulerImpl;
import com.msgsched.ui.Pantalla1;


import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Map<MessageSenderStrategy, MessageSender> senders = new HashMap<MessageSenderStrategy, MessageSender>();
        senders.put(MessageSenderStrategy.EMAIL, MessageSenderStrategy.getStrategy(MessageSenderStrategy.StrategyNames.EMAIL));
        senders.put(MessageSenderStrategy.WHATSAPP, MessageSenderStrategy.getStrategy(MessageSenderStrategy.StrategyNames.WHATSAPP));

//        try {
//            MessageDAO msgDao = new MessageDAOImpl(ConnectionDB.getConnection());
//            msgDao.save(
//                    Reminder.builder()
//                            .message("mensaje de prueba")
//                            .email(true)
//                            .whatsapp(false)
//                            .state(false)
//                            .send(LocalDateTime.parse("2025-10-31T14:30:00"))
//                            .build()
//            );
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

//        try {
//            MessageDAO msgDao = new MessageDAOImpl(ConnectionDB.getConnection());
//            List<Reminder> reminders = msgDao.findAll();
//            reminders.forEach(System.out::println);
//        }
//        catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

//        try {
//            MessageDAO msgDao = new MessageDAOImpl(ConnectionDB.getConnection());
//            Reminder reminder = msgDao.findById(1).orElseThrow();
//            System.out.println(reminder);
//        }
//        catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        try {
            MessageDAO dao = new MessageDAOImpl(ConnectionDB.getConnection());
            MessageScheduler scheduler = new MessageSchedulerImpl(dao, senders);
            scheduler.start();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


//        MessageSenderStrategy.getStrategy(MessageSenderStrategy.StrategyNames.WHATSAPP)
//                .accept(MessageSender.Input.builder()
//                        .reminder(Message.builder()
//                                .message("Tomar medicamento")
//                                .date("2024-06-10 10:00")
//                                .build())
//                        .build());
//
//        MessageSenderStrategy.getStrategy(MessageSenderStrategy.StrategyNames.EMAIL)
//                .accept(MessageSender.Input.builder()
//                        .reminder(Message.builder()
//                                .message("Tomar medicamento")
//                                .date("2024-06-10 10:00")
//                                .build())
//                        .build());

        SwingUtilities.invokeLater(() -> new Pantalla1());

    }
}