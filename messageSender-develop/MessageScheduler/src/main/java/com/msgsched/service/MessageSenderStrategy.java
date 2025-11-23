package com.msgsched.service;

import com.msgsched.service.impl.EmailMesseageSenderImpl;
import com.msgsched.service.impl.WhatsAppMesseageSenderImpl;

import java.util.Arrays;

public enum MessageSenderStrategy {
    WHATSAPP("WHATSAPP", new WhatsAppMesseageSenderImpl()),
    EMAIL("EMAIL", new EmailMesseageSenderImpl());

    private String strategy;
    private MessageSender messageSender;

    MessageSenderStrategy(String value, MessageSender messageSender) {
        this.strategy = value;
        this.messageSender = messageSender;
    }

    public static MessageSender getStrategy(StrategyNames value) {
        return Arrays.stream(MessageSenderStrategy.values())
                .filter(strategy -> strategy.strategy.equalsIgnoreCase(value.name()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No strategy found for value: " + value.name()))
                .messageSender;
    }

    public enum StrategyNames {
        WHATSAPP,
        EMAIL
    }
}
