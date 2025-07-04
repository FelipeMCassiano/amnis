package amnis.moderationservice.dtos;

import java.util.List;

public record BlockedMessagesDTO (List<BlockedMessageDTO> blockedMessages) {
}
