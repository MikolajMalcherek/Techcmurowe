package com.example.Techcmurowe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user1;

    @ManyToOne
    private User user2;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages;

}
