package amnis.notificationservice.services.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class GiftWebsocketHandler extends TextWebSocketHandler {

    private Map<Long, Set<WebSocketSession>> sessionsByStreamId = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long streamId = getStreamId(session);
        if (streamId == null) {
            session.close(CloseStatus.BAD_DATA);
            return ;
        }
        sessionsByStreamId
                .computeIfAbsent(streamId, k -> new HashSet<>())
                .add(session);

        session.getAttributes().put("streamId", streamId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long streamId = (Long)  session.getAttributes().get("streamId");
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
        String query =  session.getUri().getQuery();
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
