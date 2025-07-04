package com.amnis.StreamGateway.config.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value(value = "${spring.data.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value(value = "${api.chatMessage.topic}")
    private String chatMessageTopic;
    @Value(value = "${api.gift.topic}")
    private String giftTopic;
    @Value(value = "${api.userJoined.topic}")
    private String userJoinedTopic;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topicChatMessage() {
        return new NewTopic(chatMessageTopic, 2, (short) 2);
    }

    @Bean
    public NewTopic topicUserJoined() {
        return new NewTopic(userJoinedTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic topicGift() {
        return new NewTopic(giftTopic, 2, (short) 2);
    }
}
