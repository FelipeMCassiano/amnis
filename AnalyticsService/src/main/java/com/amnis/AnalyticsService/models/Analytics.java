package com.amnis.AnalyticsService.models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@Document
public class Analytics {
    @MongoId
    private String id;
    @Field("streamId")
    private Long streamId;
    private List<ChatMessage> messages = new ArrayList<>();
    private List<UserJoined> users = new ArrayList<>();
    private List<Gift> gifts = new ArrayList<>();
    public Analytics(){

    }

    public List<ChatMessage> getMessages() {
        return messages;
    }
    public void addMessage(ChatMessage chatMessage) {
        messages.add(chatMessage);
    }

    public List<UserJoined> getUsers() {
        return users;
    }
    public void addUser(UserJoined userJoined) {
        users.add(userJoined);
    }

    public List<Gift> getGifts() {
        return gifts;
    }
    public void addGift(Gift gift) {
        gifts.add(gift);
    }

    public Long getStreamId() {
        return streamId;
    }

    public void setStreamId(Long streamId) {
        this.streamId = streamId;
    }
}
