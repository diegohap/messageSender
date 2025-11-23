package com.msgsched.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Reminder {
    private int id;
    private String message; //message
    private LocalDateTime send; //send
    private boolean email; //to send by email
    private boolean whatsapp; //to send by whatsapp
    private boolean state;
}
