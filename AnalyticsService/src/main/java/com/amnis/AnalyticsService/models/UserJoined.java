package com.amnis.AnalyticsService.models;

import java.time.LocalDateTime;

public class UserJoined {
    private String username;
    private LocalDateTime joinedAt;

    public UserJoined(String username, LocalDateTime joinedAt) {
        this.username = username;
        this.joinedAt = joinedAt;
    }
    public UserJoined() {

    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }
}
