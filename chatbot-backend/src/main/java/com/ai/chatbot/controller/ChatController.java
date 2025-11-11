package com.ai.chatbot.controller;

import com.ai.chatbot.model.ChatRequest;
import com.ai.chatbot.model.ChatResponse;
import com.ai.chatbot.service.OllamaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private OllamaService ollamaService;

    @PostMapping
    public ChatResponse chat(@RequestBody ChatRequest chatRequest) {
        String botReply = ollamaService.generateReply(chatRequest.getMessage());
        return new ChatResponse(botReply);
    }
}