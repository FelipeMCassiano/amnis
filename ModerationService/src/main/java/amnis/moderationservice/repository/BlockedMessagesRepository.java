package amnis.moderationservice.repository;

import amnis.moderationservice.models.BlockedMessages;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BlockedMessagesRepository extends MongoRepository<BlockedMessages, String> {
    Optional<BlockedMessages> findByStreamId(Long streamId);
}
