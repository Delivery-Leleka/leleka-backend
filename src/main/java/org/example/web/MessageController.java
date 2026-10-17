package org.example.web;

import org.example.model.Message;
import org.example.service.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final ChatService chatService;

    public MessageController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/conversation")
    public List<Message> getConversation(
            @RequestParam Long user1,
            @RequestParam Long user2) {

        return chatService.getConversation(user1, user2);
    }
}
