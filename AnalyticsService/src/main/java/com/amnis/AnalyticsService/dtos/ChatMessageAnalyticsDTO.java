package com.amnis.AnalyticsService.dtos;

public record ChatMessageAnalyticsDTO(Long totalMessages, Double messagesPerMinute, Double mediumMessagesPerUsers) {
}
