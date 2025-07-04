package com.amnis.ChatService.services;

import com.amnis.ChatService.dtos.ChatMessageDTO;
import com.amnis.ChatService.models.ChatMessage;
import com.amnis.ChatService.models.ChatMessageSentEvent;
import com.amnis.ChatService.repositories.ChatMessageRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageListenerService {
    private final ChatMessageBroadcaster broadcaster;
    private final ChatMessageRepository repository;


    public ChatMessageListenerService(ChatMessageBroadcaster broadcaster, ChatMessageRepository repository) {
        this.broadcaster = broadcaster;
        this.repository = repository;
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "${api.chatMessage.topic}", partitions = "0"))
    public void chatListen(ChatMessageSentEvent event){
        ChatMessageDTO dto = saveMessage(event);
        broadcaster.broadcast(event.streamId(), dto);
    }
    private ChatMessageDTO saveMessage(ChatMessageSentEvent event){
        ChatMessage chatMessage = new ChatMessage(event.message(), event.username(), event.streamId(), event.timestamp());
        repository.save(chatMessage);
        return ChatMessageDTO.fromEntity(chatMessage);
    }
}
