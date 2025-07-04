package amnis.moderationservice.models;


import java.time.LocalDateTime;

public class Message {
    private String username;
    private String message;
    private LocalDateTime timestamp;
    private LocalDateTime blockedAt;
    public Message() {
    }
    public Message(String username, String message, LocalDateTime timestamp) {
        this.username = username;
        this.message = message;
        this.timestamp = timestamp;
        this.blockedAt = LocalDateTime.now();
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

    public LocalDateTime getBlockedAt() {
        return blockedAt;
    }
}
