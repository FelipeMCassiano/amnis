package com.amnis.AnalyticsService.services;

import com.amnis.AnalyticsService.dtos.AnalyticsDTO;
import com.amnis.AnalyticsService.dtos.ChatMessageAnalyticsDTO;
import com.amnis.AnalyticsService.dtos.GiftAnalyticsDTO;
import com.amnis.AnalyticsService.dtos.UsersJoinedAnalyticsDTO;
import com.amnis.AnalyticsService.models.Analytics;
import com.amnis.AnalyticsService.models.ChatMessage;
import com.amnis.AnalyticsService.models.Gift;
import com.amnis.AnalyticsService.models.UserJoined;
import com.amnis.AnalyticsService.models.events.ChatMessageSentEvent;
import com.amnis.AnalyticsService.models.events.GiftSentEvent;
import com.amnis.AnalyticsService.models.events.UserJoinedStreamEvent;
import com.amnis.AnalyticsService.repositories.AnalyticsRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnalyticsService {
    private final AnalyticsRepository repository;
    private final GiftAnalyticsService giftAnalyticsService;
    private final UserAnalyticsService userAnalyticsService;
    private final ChatMessagesAnalyticsService chatMessagesAnalyticsService;

    public AnalyticsService(AnalyticsRepository repository, GiftAnalyticsService giftAnalyticsService, UserAnalyticsService userAnalyticsService, ChatMessagesAnalyticsService chatMessagesAnalyticsService) {
        this.repository = repository;
        this.giftAnalyticsService = giftAnalyticsService;
        this.userAnalyticsService = userAnalyticsService;
        this.chatMessagesAnalyticsService = chatMessagesAnalyticsService;
    }
    public List<Analytics> getAllAnalytics() {
        return repository.findAll();
    }
    public AnalyticsDTO generateAnalytics(Long streamId) {
       Optional<Analytics> optionalAnalytics = repository.findByStreamId(streamId);
       if (optionalAnalytics.isEmpty()) {
           // TODO: customize exception
           throw new RuntimeException("No analytics found for streamId: " + streamId);
       }
       Analytics analytics = optionalAnalytics.get();
        GiftAnalyticsDTO giftAnalyticsDTO = giftAnalyticsService.generateGiftAnalytics(analytics.getGifts());
        UsersJoinedAnalyticsDTO usersJoinedAnalyticsDTO = userAnalyticsService.generateUsersJoinedAnalytics(analytics.getUsers());
        ChatMessageAnalyticsDTO chatMessageAnalyticsDTO = chatMessagesAnalyticsService.generateChatMessageAnalytics(analytics.getMessages(), userAnalyticsService.getUniqueUsersJoined(analytics.getUsers()));

        return new AnalyticsDTO(chatMessageAnalyticsDTO, giftAnalyticsDTO, usersJoinedAnalyticsDTO);
    }
    public void saveUserJoined(UserJoinedStreamEvent event){
        UserJoined userJoined = new UserJoined(event.username(), event.joinedAt());

        Optional<Analytics> optionalAnalytics = repository.findByStreamId(event.streamId());
        if (optionalAnalytics.isPresent()){
            Analytics analytics = optionalAnalytics.get();
            analytics.addUser(userJoined);
            repository.save(analytics);
            return;
        }

        Analytics analytics = new Analytics();
        analytics.setStreamId(event.streamId());
        analytics.addUser(userJoined);
        repository.save(analytics);
    }
    public void saveChatMessage(ChatMessageSentEvent event){
        ChatMessage chatMessage = new ChatMessage(event.username(), event.message(), event.timestamp());

        Optional<Analytics> optionalAnalytics = repository.findByStreamId(event.streamId());
        if (optionalAnalytics.isPresent()){
            Analytics analytics = optionalAnalytics.get();
            analytics.addMessage(chatMessage);
            repository.save(analytics);
            return;
        }
        Analytics analytics = new Analytics();
        analytics.setStreamId(event.streamId());
        analytics.addMessage(chatMessage);
        repository.save(analytics);

    }
    public void saveGift(GiftSentEvent event){
       Gift gift = new Gift(event.username(), event.giftType(), event.value(), event.timestamp());

       Optional<Analytics> optionalAnalytics = repository.findByStreamId(event.streamId());
       if (optionalAnalytics.isPresent()){
           Analytics analytics = optionalAnalytics.get();
           analytics.addGift(gift);
           repository.save(analytics);
           return;
       }
       Analytics analytics = new Analytics();
       analytics.setStreamId(event.streamId());
       analytics.addGift(gift);
       repository.save(analytics);
    }
}
