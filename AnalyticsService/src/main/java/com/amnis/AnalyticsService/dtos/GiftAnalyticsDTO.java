package com.amnis.AnalyticsService.dtos;

import com.amnis.AnalyticsService.models.events.GiftType;

import java.util.HashMap;
import java.util.List;

public record GiftAnalyticsDTO(Long totalValue, HashMap<GiftType, Long> giftPerType) {
}
