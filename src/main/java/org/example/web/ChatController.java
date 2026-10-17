package org.example.web;

import org.example.model.Message;
import org.example.service.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/{user1}/{user2}")
    public List<Message> getConversation(
            @PathVariable Long user1,
            @PathVariable Long user2) {

        return chatService.getConversation(user1, user2);
    }
}