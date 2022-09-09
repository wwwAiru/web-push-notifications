package com.webpush.application.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDto {

    private String title;
    private String clickTarget;
    private String message;
}
