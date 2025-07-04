package amnis.moderationservice.services.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ModerationHandler extends TextWebSocketHandler {
    private Map<Long, Set<WebSocketSession>> sessionsByStreamId = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long streamId = (Long) getStreamId(session);
        if (streamId == null) {
            session.close(CloseStatus.BAD_DATA);
            return;
        }

        sessionsByStreamId.
                computeIfAbsent(streamId, k -> new HashSet<>())
                .add(session);
        session.getAttributes().put("streamId", streamId);
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long streamId = (Long) session.getAttributes().get("streamId");
        if (streamId != null) {
            Set<WebSocketSession> sessions = sessionsByStreamId.get(streamId);
            if (sessions != null) {
                sessions.remove(session);
                if (sessions.isEmpty()) {
                    sessionsByStreamId.remove(streamId);
                }
            }
        }
    }

    private Long getStreamId(WebSocketSession session) {
        if (session.getUri() == null){
            return null;
        }
        String query = session.getUri().getQuery();
        if (query != null){
            for (String pair : query.split("&")) {
                if (pair.startsWith("streamId=")) {
                    try {
                        return Long.valueOf(pair.split("=")[1]);
                    }catch (NumberFormatException e){
                        return null;

                    }
                }
            }
        }
        return null;
    }

    public Set<WebSocketSession> getSessionByStreamId(Long streamId) {
        return sessionsByStreamId.get(streamId);
    }
}
