package com.amnis.StreamGateway.services;

import com.amnis.StreamGateway.dtos.ChatMessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<Long,Set<WebSocketSession>> sessions = new ConcurrentHashMap<>();
    private final ChatMessageService chatService;

    public ChatWebSocketHandler(ChatMessageService chatService) {
        this.chatService = chatService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long streamId = getStreamId(session);
        String username = getUser(session);
        if (streamId == null && username == null) {
            session.close(CloseStatus.BAD_DATA);
            return;
        }
        sessions
                .computeIfAbsent(streamId, id-> ConcurrentHashMap.newKeySet())
                .add(session);

        session.getAttributes().put("streamId", streamId);
        session.getAttributes().put("username", username);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            Long streamId = (Long) session.getAttributes().get("streamId");
            String username = (String) session.getAttributes().get("username");
            ChatMessageDTO chatMessageDTO = objectMapper.readValue(message.getPayload(), ChatMessageDTO.class);
            chatService.send(streamId, username,chatMessageDTO);
        }catch (Exception e){
            session.close(CloseStatus.SERVER_ERROR);
            e.printStackTrace();
        }
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        Long streamId = (Long) session.getAttributes().get("streamId");
        if (streamId != null) {
            Set<WebSocketSession> sessionSet = sessions.get(streamId);
            if (sessionSet != null) {
                sessionSet.remove(session);
                if (sessionSet.isEmpty()) {
                    sessions.remove(streamId);
                }
            }
        }
    }

    private Long getStreamId(WebSocketSession session) {
        String query = session.getUri().getQuery();
        if (query == null ) return null;
        for (String param : query.split("&")) {
            if (param.startsWith("streamId=")) {
                try {
                    return Long.valueOf(param.split("=")[1]);
                }catch (NumberFormatException e){
                    return null;
                }
            }
        }
        return null;
    }

    private String getUser(WebSocketSession session) {
        String query = session.getUri().getQuery();
        if (query == null ) return null;
        for (String param : query.split("&")) {
            if (param.startsWith("user=")) {
                try {
                    return param.split("=")[1];
                }catch (NumberFormatException e){
                    return null;
                }
            }
        }
        return null;
    }
}
