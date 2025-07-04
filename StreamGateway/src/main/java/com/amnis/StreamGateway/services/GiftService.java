package com.amnis.StreamGateway.services;

import com.amnis.StreamGateway.dtos.GiftDTO;
import com.amnis.StreamGateway.models.GiftSentEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GiftService {
    private final KafkaTemplate<String, GiftSentEvent> kafkaTemplate;
    @Value("${api.gift.topic}")
    private String giftTopic;

    public GiftService(KafkaTemplate<String, GiftSentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void send(Long streamId, GiftDTO giftDTO) {
        GiftSentEvent event = new GiftSentEvent(giftDTO.username(), streamId, giftDTO.giftType(), giftDTO.value(), LocalDateTime.now());

        for (int i : List.of(0,1)){
            kafkaTemplate.send(giftTopic,i,null, event);
        }
    }
}
