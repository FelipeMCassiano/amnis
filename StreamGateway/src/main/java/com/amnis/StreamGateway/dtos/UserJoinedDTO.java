package com.amnis.StreamGateway.dtos;

import jakarta.validation.constraints.NotNull;


public record UserJoinedDTO (@NotNull String username) {
}
