package org.example.service;

import org.example.dto.ChatMessage;
import org.example.model.Message;
import org.example.model.User;
import org.example.repository.MessageRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public ChatService(MessageRepository messageRepository,
                       UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public Message sendMessage(ChatMessage chatMessage) {

        System.out.println(
                "SENDER = " + chatMessage.getSender()
        );

        System.out.println(
                "RECEIVER = " + chatMessage.getReceiver()
        );

        System.out.println(
                "CONTENT = " + chatMessage.getContent()
        );

        User sender =
                userRepository.findById(chatMessage.getSender());

        if (sender == null) {
            throw new IllegalArgumentException(
                    "Sender not found: " +
                            chatMessage.getSender()
            );
        }

        User receiver =
                userRepository.findById(chatMessage.getReceiver());

        if (receiver == null) {
            throw new IllegalArgumentException(
                    "Receiver not found: " +
                            chatMessage.getReceiver()
            );
        }

        Message message = new Message();

        message.setSender(chatMessage.getSender());
        message.setReceiver(chatMessage.getReceiver());
        message.setContent(chatMessage.getContent());
        message.setCreatedAt(LocalDateTime.now());

        return messageRepository.save(message);
    }

    public List<Message> getConversation(Long user1, Long user2) {
        return messageRepository.findConversation(user1, user2);
    }
}
