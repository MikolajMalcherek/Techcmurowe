package com.example.Techcmurowe.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageResponseDTO {
//    private Long id;
    private String message;
    private Long senderId;
    private Long receiverId;
//    private LocalDateTime datetime;
    private Long chatId;
}
