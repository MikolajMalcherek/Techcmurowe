package com.example.Techcmurowe.service;

import com.example.Techcmurowe.model.Chat;
import com.example.Techcmurowe.model.User;
import com.example.Techcmurowe.repository.ChatRepository;
import com.example.Techcmurowe.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    private final UserRepository userRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository,
                       UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Chat createChat(Long user1Id, Long user2Id) {

        User user1 = userRepository.findById(user1Id).orElse(null);
        User user2 = userRepository.findById(user2Id).orElse(null);

        // Check if there are those users in db
        if (user1 == null || user2 == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        // Check if there is a chat with those users already
        if (!chatRepository.existsByUser1_IdAndUser2_Id(user1.getId(), user2.getId()) && !chatRepository.existsByUser1_IdAndUser2_Id(user2.getId(), user1.getId())) {
            Chat chat = new Chat();
            chat.setUser1(user1);
            chat.setUser2(user2);
            chat.setMessages(new ArrayList<>());

            return chatRepository.save(chat);
        }
        else throw new ResponseStatusException(HttpStatus.CONFLICT, "There is a chat with those users already.");
    }

    public List<Chat> getChatsForUser(Long user1Id) {
        User user1 = userRepository.findById(user1Id).orElse(null);

        if (user1 == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

        List<Chat> chats = chatRepository.findChatsByUser1_IdOrUser2_Id(user1Id, user1Id);

        return chats;
    }

    public boolean checkIfChatExists(Long user1Id, Long user2Id){
        // Check if there is a chat for those users
        return chatRepository.findByUser1_IdAndUser2_Id(user1Id, user2Id).isPresent() || chatRepository.findByUser1_IdAndUser2_Id(user2Id, user1Id).isPresent();
    }

    public Chat beginChat(Long user1Id, Long user2Id) {

        if(checkIfChatExists(user1Id, user2Id)){
            if(chatRepository.findByUser1_IdAndUser2_Id(user1Id, user2Id).isPresent()){
                return chatRepository.findByUser1_IdAndUser2_Id(user1Id, user2Id).get();
            }
            else return chatRepository.findByUser1_IdAndUser2_Id(user2Id, user1Id).get();
        }
        else {
            Chat newChat = new Chat();

            Optional<User> user1 = userRepository.findById(user1Id);
            Optional<User> user2 = userRepository.findById(user2Id);
            if (user1.isPresent() && user2.isPresent()) {
                newChat.setUser1(user1.get());
                newChat.setUser2(user2.get());
                newChat.setMessages(new ArrayList<>());
                return chatRepository.save(newChat);
            }
            else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

        }
    }
}
