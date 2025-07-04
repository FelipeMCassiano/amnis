package com.amnis.AnalyticsService.services;

import com.amnis.AnalyticsService.models.events.ChatMessageSentEvent;
import com.amnis.AnalyticsService.models.events.GiftSentEvent;
import com.amnis.AnalyticsService.models.events.UserJoinedStreamEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsListener {
    private final AnalyticsService analyticsService;

    public AnalyticsListener(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @KafkaListener(
            topicPartitions = @TopicPartition(topic = "${api.userJoined.topic}", partitions = "0") ,

            containerFactory = "userJoinedKafkaListenerContainerFactory"
    )
    public void listenUserJoinedEvent(UserJoinedStreamEvent event) {
        analyticsService.saveUserJoined(event);
    }

    @KafkaListener(
            topicPartitions = @TopicPartition(topic = "${api.chatMessage.topic}", partitions = "1"),
            containerFactory = "chatMessageKafkaListenerContainerFactory"
    )
    public void listenChatMessageEvent(ChatMessageSentEvent event) {
        analyticsService.saveChatMessage(event);
    }
    @KafkaListener(
            topicPartitions = @TopicPartition(topic = "${api.gift.topic}", partitions = "0"),
            containerFactory = "giftKafkaListenerContainerFactory"
    )
    public void listenGiftSentEvent(GiftSentEvent event) {
        analyticsService.saveGift(event);
    }
}
