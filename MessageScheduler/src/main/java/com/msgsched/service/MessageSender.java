package com.msgsched.service;

import com.msgsched.entity.Reminder;
import com.msgsched.service.domain.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.function.Consumer;

public interface MessageSender extends Consumer<MessageSender.Input> {

    boolean support(Reminder reminder);

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    class Input {
        private Message reminder;
    }
}
