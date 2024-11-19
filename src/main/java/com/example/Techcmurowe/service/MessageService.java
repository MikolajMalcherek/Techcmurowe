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
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    private final ChatRepository chatRepository;

    private final UserRepository userRepository;

    private final MessageRepository messageRepository;

    private final SimpMessagingTemplate messagingTemplate;

    public MessageService(MessageRepository messageRepository,
                          ChatRepository chatRepository,
                          UserRepository userRepository,
                          SimpMessagingTemplate messagingTemplate) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public MessageResponseDTO sendMessage(Long chatId, MessageDTO message) {
        System.out.println("Inside sendMessage");
        Chat chat = chatRepository.findById(chatId).orElse(null);
        User sender = userRepository.findById(message.getSenderId()).orElse(null);
        User receiver = userRepository.findById(message.getReceiverId()).orElse(null);

        if (chat == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat not found");
        }
        if (sender == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        if (receiver == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        if (!chatRepository.existsByUser1_IdAndUser2_Id(sender.getId(), receiver.getId()) && !chatRepository.existsByUser1_IdAndUser2_Id(receiver.getId(), sender.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat not found for those users");
        }

        LocalDateTime messageCreatedAt = LocalDateTime.now();

        // Saving message to database
        Message newMessage = new Message();
        newMessage.setMessage(message.getMessage());
        newMessage.setSender(sender);
        newMessage.setReceiver(receiver);
        newMessage.setDatetime(messageCreatedAt);
        newMessage.setChat(chat);

        messageRepository.save(newMessage);

        // Converting to MessageResponseDTO

        MessageResponseDTO messageResponse = new MessageResponseDTO();

//        messageResponse.setId(newMessage.getId());
        messageResponse.setMessage(newMessage.getMessage());
        messageResponse.setSenderId(newMessage.getSender().getId());
        messageResponse.setReceiverId(newMessage.getReceiver().getId());
//        messageResponse.setDatetime(messageCreatedAt);
        messageResponse.setChatId(newMessage.getChat().getId());

        // Send a new message to a specific route
        messagingTemplate.convertAndSend("/topic/chat/" + chatId, messageResponse);

        return messageResponse;
    }

    public List<MessageResponseDTO> findAllByChatId(Long chatId) {
        System.out.println("Inside findAllByChatId");
        List<Message> messages = messageRepository.findAllByChatId(chatId);

        List<MessageResponseDTO> messageResponses = new ArrayList<>();

        for (Message message : messages) {
            MessageResponseDTO messageResponse = new MessageResponseDTO();
//            messageResponse.setId(message.getId());
            messageResponse.setMessage(message.getMessage());
            messageResponse.setSenderId(message.getSender().getId());
            messageResponse.setReceiverId(message.getReceiver().getId());
//            messageResponse.setDatetime(message.getDatetime());
            messageResponse.setChatId(message.getChat().getId());
            messageResponses.add(messageResponse);
        }
        return messageResponses;
    }
}
