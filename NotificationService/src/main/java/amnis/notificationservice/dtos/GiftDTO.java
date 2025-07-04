package amnis.notificationservice.dtos;

import amnis.notificationservice.models.events.GiftType;

public record GiftDTO(String message, GiftType giftType) { }
