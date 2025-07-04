package amnis.moderationservice.repository;

import amnis.moderationservice.models.BlacklistMessages;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BlacklistMessagesRepository extends MongoRepository<BlacklistMessages, String> {
    Optional<BlacklistMessages> findByStreamId(Long streamId);
}
