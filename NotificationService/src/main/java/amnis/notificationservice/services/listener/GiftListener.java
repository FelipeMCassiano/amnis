package amnis.notificationservice.services.listener;

import amnis.notificationservice.models.events.GiftSentEvent;
import amnis.notificationservice.services.GiftService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

@Component
public class GiftListener {
    private final GiftService giftService;

    public GiftListener(GiftService giftService) {
        this.giftService = giftService;
    }

    @KafkaListener(
            topicPartitions = @TopicPartition(topic = "${api.gift.topic}", partitions = "1"),
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(GiftSentEvent event){
        giftService.sendGiftNotification(event);
    }
}
