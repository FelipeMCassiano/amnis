package amnis.moderationservice.services.listener;

import amnis.moderationservice.models.events.ChatMessageSentEvent;
import amnis.moderationservice.services.ModerationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

@Component
public class ChatMessageListener {
    private final ModerationService moderationService;

    public ChatMessageListener(ModerationService moderationService) {
        this.moderationService = moderationService;
    }

    @KafkaListener(
            topicPartitions = @TopicPartition(topic = "${api.chatMessage.topic}", partitions = "0"),
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void chatListen(ChatMessageSentEvent event) {
        System.out.println("event " + event);
        moderationService.filterMessage(event);
    }
}
