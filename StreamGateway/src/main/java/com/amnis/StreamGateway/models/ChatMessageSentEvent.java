package com.amnis.StreamGateway.models;

import java.time.LocalDateTime;


import com.fasterxml.jackson.annotation.JsonFormat;


public record ChatMessageSentEvent(String username, Long streamId, String message,  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS") LocalDateTime timestamp) {
}

