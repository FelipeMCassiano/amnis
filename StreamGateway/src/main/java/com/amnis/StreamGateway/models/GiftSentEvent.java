package com.amnis.StreamGateway.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record GiftSentEvent(String username, Long streamId, GiftType giftType, Long value, @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")LocalDateTime timestamp) {
}
