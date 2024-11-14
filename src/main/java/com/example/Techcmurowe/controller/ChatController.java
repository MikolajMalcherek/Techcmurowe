package com.example.Techcmurowe.controller;

import com.example.Techcmurowe.model.Chat;
import com.example.Techcmurowe.model.User;
import com.example.Techcmurowe.repository.ChatRepository;
import com.example.Techcmurowe.repository.UserRepository;
import com.example.Techcmurowe.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chats")
public class ChatController {
    @Autowired
    ChatRepository chatRepository;

    @Autowired
    ChatService chatService;

    @Autowired
    UserRepository userRepository;

    // Trzeba dodać, żeby szukało niezależnie od kolejności id.
    @GetMapping("/{user1Id}/{user2Id}")
    public ResponseEntity<Optional<Chat>> findChatByUsersIds(@PathVariable Long user1Id,@PathVariable Long user2Id) {
        Optional<Chat> chat = chatRepository.findByUser1_IdAndUser2_Id(user1Id, user2Id);

        // Checking reversed userId order
        Optional<Chat> chat2 = chatRepository.findByUser1_IdAndUser2_Id(user2Id, user1Id);

        // Check if there is no chat with those users already
        if (chat.isPresent()) {
            return ResponseEntity.ok(chat);
        }
        else if (chat2.isPresent()) {
            return ResponseEntity.ok(chat2);
        }
        else return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Chat> createChat(@RequestParam Long user1Id, @RequestParam Long user2Id ) {

        Chat chat = chatService.createChat(user1Id, user2Id);

        return new ResponseEntity<>(chat, HttpStatus.CREATED);
    }

    @GetMapping("/getalluserchats/{userId}")
    public ResponseEntity<List<Chat>> getAllUserChats(@PathVariable Long userId) {
        return ResponseEntity.ok(chatService.getChatsForUser(userId));
    }

    @GetMapping("/checkifexists")
    public ResponseEntity<Boolean> checkIfChatExists(@RequestParam Long user1Id, @RequestParam Long user2Id) {
        return ResponseEntity.ok(chatService.checkIfChatExists(user1Id, user2Id));
    }
}
