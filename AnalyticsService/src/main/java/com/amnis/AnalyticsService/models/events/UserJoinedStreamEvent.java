package com.amnis.AnalyticsService.models.events;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record UserJoinedStreamEvent(String username, Long streamId, @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS") LocalDateTime joinedAt) {
}
