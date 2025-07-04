package com.amnis.StreamGateway.dtos;

import com.amnis.StreamGateway.models.GiftType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record GiftDTO(@NotBlank String username, @NotNull GiftType giftType, @Min(1) Long value) { }
