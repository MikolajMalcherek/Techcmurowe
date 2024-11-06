package com.example.Techcmurowe.service;

import com.example.Techcmurowe.DTO.MessageDTO;
import com.example.Techcmurowe.DTO.MessageResponseDTO;
import com.example.Techcmurowe.model.Chat;
import com.example.Techcmurowe.model.Message;
import com.example.Techcmurowe.model.User;
import com.example.Techcmurowe.repository.ChatRepository;
import com.example.Techcmurowe.repository.MessageRepository;
import com.example.Techcmurowe.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class MessageService {

    private final ChatRepository chatRepository;

    private final UserRepository userRepository;

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository,
                          ChatRepository chatRepository,
                          UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    public MessageResponseDTO sendMessage(Long chatId, MessageDTO messageDTO) {
        Chat chat = chatRepository.findById(chatId).orElse(null);
        User sender = userRepository.findById(messageDTO.getSenderId()).orElse(null);
        User receiver = userRepository.findById(messageDTO.getReceiverId()).orElse(null);

        if (chat == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat not found");
        }
        if (sender == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        if (receiver == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        if (!chatRepository.existsByUser1_IdAndUser2_Id(sender.getId(), receiver.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat not found for those users");
        }

        LocalDateTime messageCreatedAt = LocalDateTime.now();

        // Saving message to database
        Message message = new Message();
        message.setMessage(messageDTO.getMessage());
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setDatetime(messageCreatedAt);
        message.setChat(chat);

        Message savedMessage = messageRepository.save(message);

        // Converting to MessageResponseDTO

        MessageResponseDTO messageResponse = new MessageResponseDTO();

        messageResponse.setId(message.getId());
        messageResponse.setMessage(message.getMessage());
        messageResponse.setSenderId(sender.getId());
        messageResponse.setReceiverId(receiver.getId());
        messageResponse.setDatetime(messageCreatedAt);
        messageResponse.setChatId(chat.getId());

        return messageResponse;
    }
}
