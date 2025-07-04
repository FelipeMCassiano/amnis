package com.amnis.StreamGateway.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ChatMessageDTO(@NotBlank String message) { }
