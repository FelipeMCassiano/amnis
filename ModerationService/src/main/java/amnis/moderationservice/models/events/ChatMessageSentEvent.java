package amnis.moderationservice.models.events;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
public record ChatMessageSentEvent(String username, Long streamId, String message,  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS") LocalDateTime timestamp) {
}
