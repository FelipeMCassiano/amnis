package com.amnis.ChatService.services;

import com.amnis.ChatService.dtos.ChatMessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Set;

@Component
public class ChatMessageBroadcaster {
   private final ObjectMapper objectMapper;
   private final ChatMessageHandler chatMessageHandler;

    public ChatMessageBroadcaster(ChatMessageHandler chatMessageHandler) {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        this.chatMessageHandler = chatMessageHandler;
    }

    public void broadcast(Long streamId,ChatMessageDTO dto){
        Set<WebSocketSession> sessions = chatMessageHandler.getSessionsByStreamId(streamId);
        try {
            String json = objectMapper.writeValueAsString(dto);
            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(json));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
