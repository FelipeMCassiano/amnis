package com.amnis.AnalyticsService.models;

import java.time.LocalDateTime;

public class ChatMessage {
    private String username;
    private String message;
    private LocalDateTime timestamp;
    public ChatMessage(String username, String message, LocalDateTime timestamp) {
        this.username = username;
        this.message = message;
        this.timestamp = timestamp;
    }
    public ChatMessage(){

    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
