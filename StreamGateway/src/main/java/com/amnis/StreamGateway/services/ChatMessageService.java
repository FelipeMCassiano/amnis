package com.amnis.StreamGateway.services;

import com.amnis.StreamGateway.dtos.ChatMessageDTO;
import com.amnis.StreamGateway.models.ChatMessageSentEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatMessageService {
    private final KafkaTemplate<String, ChatMessageSentEvent> kafkaTemplate;

    @Value(value = "${api.chatMessage.topic}")
    private String chatMessageTopic;


    public ChatMessageService(KafkaTemplate<String, ChatMessageSentEvent> chatMessageSentKafkaTemplate) {
        this.kafkaTemplate = chatMessageSentKafkaTemplate;
    }
    public void send(Long streamId, String username, ChatMessageDTO chatMessageDTO) {
        ChatMessageSentEvent event = new ChatMessageSentEvent(username,streamId,chatMessageDTO.message(), LocalDateTime.now());
        for (int i : List.of(0,1)){
            kafkaTemplate.send(chatMessageTopic,i,null, event);
        }
    }
}
