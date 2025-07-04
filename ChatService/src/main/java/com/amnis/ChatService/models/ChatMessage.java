package com.amnis.ChatService.models;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Document("tb_messages")
public class ChatMessage {
    @MongoId
    private String id;
    private final String message;
    private final String username;
    @Indexed
    private final Long streamId;
    private final LocalDateTime timestamp;

    public ChatMessage(String message, String username, Long streamId, LocalDateTime timestamp) {
        this.message = message;
        this.username = username;
        this.streamId = streamId;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }



    public Long getStreamId() {
        return streamId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getUsername() {
        return username;
    }
}
