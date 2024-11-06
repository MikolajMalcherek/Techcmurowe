package com.example.Techcmurowe.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {
    private String message;
    private Long senderId;
    private Long receiverId;
}
