package amnis.moderationservice.services.websocket;

import amnis.moderationservice.models.events.ChatMessageSentEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;

import java.util.Set;

@Component
public class ModerationBroadcaster {
    private final ModerationHandler moderationHandler;
    private final ObjectMapper objectMapper;

    public ModerationBroadcaster(ModerationHandler moderationHandler) {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        this.moderationHandler = moderationHandler;
    }

    public void broadcast(Long streamId, ChatMessageSentEvent event) {

        try{
            String json =  objectMapper.writeValueAsString(event);
            Set<WebSocketSession> sessionByStreamId = moderationHandler.getSessionByStreamId(streamId);
            for (WebSocketSession session : sessionByStreamId) {
                session.sendMessage(new TextMessage(json));
            }

        }catch (Exception e){

        }

    }
}
