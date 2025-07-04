package com.amnis.ChatService.repositories;

import com.amnis.ChatService.models.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage,Long> {
}
