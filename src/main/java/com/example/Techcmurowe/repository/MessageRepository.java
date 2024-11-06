package com.example.Techcmurowe.repository;

import com.example.Techcmurowe.model.Message;
import com.example.Techcmurowe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByReceiverAndSender(User receiver, User sender);

    List<Message> findAllBySenderAndReceiver(User sender, User receiver);

    List<Message> findAllByChatId(Long chatId);
}
