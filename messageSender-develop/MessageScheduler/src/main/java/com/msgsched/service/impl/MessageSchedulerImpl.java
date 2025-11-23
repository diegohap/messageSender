package com.msgsched.service.impl;

import com.msgsched.entity.Reminder;
import com.msgsched.repository.MessageDAO;
import com.msgsched.service.MessageScheduler;
import com.msgsched.service.MessageSender;
import com.msgsched.service.MessageSenderStrategy;
import com.msgsched.service.domain.MessageParser;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.isNull;

public class MessageSchedulerImpl implements MessageScheduler {

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final MessageDAO messageDAO;
    private final Map<MessageSenderStrategy, MessageSender> senders;

    private ScheduledFuture<?> currentTask = null;
    private Reminder currentReminder = null;

    public MessageSchedulerImpl(MessageDAO messageDAO, Map<MessageSenderStrategy, MessageSender> senders) {
        this.messageDAO = messageDAO;
        this.senders = senders;
    }

    @Override
    public void start() {
        scheduler.schedule(this::scheduleNext, 0, TimeUnit.SECONDS);
    }


    private void scheduleNext() {
        try {
            currentReminder = messageDAO.findNextPending().orElse(null);
            if(isNull(currentReminder)) {
                System.out.println("[Scheduler] No hay mensajes pendientes. Revisando en 1 minuto...");
                scheduler.schedule(this::scheduleNext, 1, TimeUnit.MINUTES);
                return;
            }
            System.out.println("[Scheduler] Enviando mensaje ID: " + currentReminder.getId());

            senders.forEach((strategy, sender) -> {
                if(sender.support(currentReminder)) {
                    sender.accept(MessageSender.Input.builder()
                            .reminder(MessageParser.reminderToMessage(currentReminder))
                            .build());
                }
            });

            messageDAO.updateState(currentReminder.getId(), true);

            scheduler.schedule(this::scheduleNext, 2, TimeUnit.SECONDS);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("[Scheduler] Error. Reintentando en 1 minuto...");
            scheduler.schedule(this::scheduleNext, 1, TimeUnit.MINUTES);
        }
    }
}
