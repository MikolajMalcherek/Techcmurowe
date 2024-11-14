package com.example.Techcmurowe.controller;

import com.example.Techcmurowe.DTO.MessageDTO;
import com.example.Techcmurowe.DTO.MessageResponseDTO;
import com.example.Techcmurowe.model.Chat;
import com.example.Techcmurowe.model.Message;
import com.example.Techcmurowe.model.User;
import com.example.Techcmurowe.repository.ChatRepository;
import com.example.Techcmurowe.repository.MessageRepository;
import com.example.Techcmurowe.repository.UserRepository;
import com.example.Techcmurowe.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageRepository messageRepository;

    private final MessageService messageService;

    public MessageController(MessageRepository messageRepository,
                             MessageService messageService) {
        this.messageRepository = messageRepository;
        this.messageService = messageService;
    }

    @MessageMapping("/sendmessage/{chatId}}")
    // TUTAJ DODAĆ KONKRENTY WYMAGANY SCOPE DO DOSTĘPU
    //  @PreAuthorize("hasAuthority('SCOPE_users')")
    public MessageResponseDTO sendMessage(@DestinationVariable Long chatId, @Payload Message message) {

        return messageService.sendMessage(chatId, message);
    }

    @GetMapping("/{chatId}")
    public List<Message> getMessagesForChat(@PathVariable Long chatId) {
        return messageRepository.findAllByChatId(chatId);
    }
}
