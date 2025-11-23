package com.msgsched.service.impl;

import com.msgsched.entity.Reminder;
import com.msgsched.service.MessageSender;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class WhatsAppMesseageSenderImpl implements MessageSender {

    @Override
    public boolean support(Reminder reminder) {
        return reminder.isWhatsapp();
    }

    @Override
    public void accept(Input input) {
        String text = String.format("WhatsApp message sent at %s : %s",
                input.getReminder().getDate(),
                input.getReminder().getMessage());
        System.out.println(text);
    }
}
