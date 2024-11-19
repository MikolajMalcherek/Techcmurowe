package com.example.Techcmurowe.DTO;

import com.example.Techcmurowe.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatDTO {
    private Long id;
    private User user1;
    private User user2;
    private List<MessageResponseDTO> messages;

}
