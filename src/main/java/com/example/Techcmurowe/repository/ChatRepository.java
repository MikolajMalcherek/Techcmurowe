package com.example.Techcmurowe.repository;

import com.example.Techcmurowe.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByUser1_IdAndUser2_Id(Long user1_Id, Long user2_Id);
    boolean existsByUser1_IdAndUser2_Id(Long user1_Id, Long user2_Id);

    List<Chat> findChatsByUser1_IdOrUser2_Id(Long user1_Id, Long user2_Id);
}
