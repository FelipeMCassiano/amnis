package com.amnis.AnalyticsService.models;

import com.amnis.AnalyticsService.models.events.GiftType;

import java.time.LocalDateTime;

public class Gift {
    private String username;
    private GiftType giftType;
    private Long value;
    private LocalDateTime timestamp;

    public Gift(String username, GiftType giftType, Long value, LocalDateTime timestamp) {
        this.username = username;
        this.giftType = giftType;
        this.value = value;
        this.timestamp = timestamp;
    }
    public Gift() {

    }

    public String getUsername() {
        return username;
    }

    public GiftType getGiftType() {
        return giftType;
    }

    public Long getValue() {
        return value;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
