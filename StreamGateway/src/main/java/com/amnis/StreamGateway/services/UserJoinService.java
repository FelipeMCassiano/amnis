package com.amnis.StreamGateway.services;

import com.amnis.StreamGateway.dtos.UserJoinedDTO;
import com.amnis.StreamGateway.models.UserJoinedStreamEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class UserJoinService {

    @Value(value = "${api.userJoined.topic}")
    private String userJoinedTopic;
    private final KafkaTemplate<String, UserJoinedStreamEvent> kafkaTemplate;

    public UserJoinService(KafkaTemplate<String, UserJoinedStreamEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(Long streamId, UserJoinedDTO userJoinedDTO) {
        UserJoinedStreamEvent event = new UserJoinedStreamEvent(userJoinedDTO.username(), streamId, LocalDateTime.now());
        kafkaTemplate.send(userJoinedTopic, event);
    }

}
