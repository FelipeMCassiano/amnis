package com.amnis.ChatService.dtos;

import com.amnis.ChatService.models.ChatMessage;

import java.time.LocalDateTime;

public record ChatMessageDTO(String username,  String message, LocalDateTime timestamp) {
    public static ChatMessageDTO fromEntity(ChatMessage entity){
        return new ChatMessageDTO(entity.getUsername(), entity.getMessage(), entity.getTimestamp());
    }
}
