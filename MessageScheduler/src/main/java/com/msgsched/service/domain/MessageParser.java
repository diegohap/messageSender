package com.msgsched.service.domain;

import com.msgsched.entity.Reminder;

public class MessageParser {
    public static Message reminderToMessage(Reminder reminder) {
        return Message.builder()
                .message(reminder.getMessage())
                .build();
    }
}
