package com.amnis.AnalyticsService.services;

import com.amnis.AnalyticsService.dtos.GiftAnalyticsDTO;
import com.amnis.AnalyticsService.models.Gift;
import com.amnis.AnalyticsService.models.events.GiftType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class GiftAnalyticsService {
    public GiftAnalyticsDTO generateGiftAnalytics(List<Gift> gifts){

        Long totalValueGift = gifts.stream().map(Gift::getValue).reduce(Long::sum).orElse(0L);
        HashMap<GiftType, Long> giftPerType = getTotalGiftPerType(gifts);

        return new GiftAnalyticsDTO(totalValueGift, giftPerType);
    }
    private HashMap<GiftType, Long> getTotalGiftPerType(List<Gift> gifts) {
        HashMap<GiftType, Long> giftPerTypeDTOs = new HashMap<>();

        gifts.forEach(gift -> {
            GiftType giftType = gift.getGiftType();
            if (!giftPerTypeDTOs.containsKey(giftType)) {
                giftPerTypeDTOs.put(giftType, 0L);
                return;
            }
            giftPerTypeDTOs.put(giftType, giftPerTypeDTOs.get(giftType) +1);
        });

        return giftPerTypeDTOs;
    }
}


