package com.amnis.AnalyticsService.services;

import com.amnis.AnalyticsService.dtos.ChatMessageAnalyticsDTO;
import com.amnis.AnalyticsService.models.Analytics;
import com.amnis.AnalyticsService.models.ChatMessage;
import com.amnis.AnalyticsService.models.UserJoined;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

@Service
public class ChatMessagesAnalyticsService {
    public ChatMessageAnalyticsDTO generateChatMessageAnalytics(List<ChatMessage> chatMessages, Set<UserJoined> users){
        Long totalMessages =  (long) chatMessages.size();
        Double messagesPerMinute = getMessagesPerMinute(chatMessages);
        Double mediumMessagesPerUser = getMessagesMediumPerUser(chatMessages, users);

        return  new ChatMessageAnalyticsDTO(totalMessages,messagesPerMinute,mediumMessagesPerUser);
    }
    private Double getMessagesMediumPerUser(List<ChatMessage> chatMessages, Set<UserJoined> users) {
        if (chatMessages.isEmpty() || users == null || users.isEmpty()) {
            return 0.0;
        }

        return  (double) chatMessages.size() / users.size();
    }
    private Double getMessagesPerMinute(List<ChatMessage> chatMessages) {
        if (chatMessages.size() < 2) {
            return 0.0;
        }

        ChatMessage first = chatMessages.getFirst();
        ChatMessage last = chatMessages.getLast();
        long minutesBetween = ChronoUnit.MINUTES.between(first.getTimestamp(), last.getTimestamp());

        if (minutesBetween <= 0) {
            return 0.0;
        }

        return (double) chatMessages.size() / minutesBetween;
    }
}
