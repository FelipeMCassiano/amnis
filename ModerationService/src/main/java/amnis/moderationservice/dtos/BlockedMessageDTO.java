package amnis.moderationservice.dtos;

import java.time.LocalDateTime;

public record BlockedMessageDTO(String username, String message, LocalDateTime timestamp, LocalDateTime blockedAt) {
}
