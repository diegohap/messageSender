package com.msgsched.service.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Message {
    private String message;
    private String date;
}
