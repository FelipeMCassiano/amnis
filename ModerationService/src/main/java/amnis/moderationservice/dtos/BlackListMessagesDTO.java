package amnis.moderationservice.dtos;

import java.io.Serializable;
import java.util.List;

public record BlackListMessagesDTO(List<String> blacklist) {
}
