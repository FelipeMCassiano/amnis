package com.amnis.ChatService.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatMessageHandler extends TextWebSocketHandler {
    private Map<Long, Set<WebSocketSession>> sessionsByStreamId = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long streamId = getStreamId(session);
        if (streamId == null) {
            session.close(CloseStatus.BAD_DATA);
            return;
        }

        sessionsByStreamId
                .computeIfAbsent(streamId, k -> ConcurrentHashMap.newKeySet())
                .add(session);

        session.getAttributes().put("streamId", streamId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long streamId = (Long) session.getAttributes().get("streamId");
        if (streamId != null) {
            Set<WebSocketSession> sessionSet = getSessionsByStreamId(streamId);
            if (sessionSet != null) {
                sessionSet.remove(session);
                if (sessionSet.isEmpty()) {
                    sessionsByStreamId.remove(streamId);
                }
            }
        }
    }

    private Long getStreamId(WebSocketSession session) {
        String query = session.getUri() != null ? session.getUri().getQuery() : "";
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

    public Set<WebSocketSession> getSessionsByStreamId(Long streamId) {
        return sessionsByStreamId.get(streamId);
    }

}
