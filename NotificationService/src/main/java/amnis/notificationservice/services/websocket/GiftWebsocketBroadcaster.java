package amnis.notificationservice.services.websocket;

import amnis.notificationservice.dtos.GiftDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;

import java.util.Set;

@Component
public class GiftWebsocketBroadcaster {
    private final GiftWebsocketHandler giftWebsocketHandler;
    private final ObjectMapper objectMapper;

    public GiftWebsocketBroadcaster(GiftWebsocketHandler giftWebsocketHandler, ObjectMapper objectMapper) {
        this.giftWebsocketHandler = giftWebsocketHandler;

        this.objectMapper = objectMapper;
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


    public void broadcast(Long streamId, GiftDTO giftDTO) {
        Set<WebSocketSession> sessionsByStreamId = giftWebsocketHandler.getSessionsByStreamId(streamId);
        try {
            String json = objectMapper.writeValueAsString(giftDTO);
            for (WebSocketSession session : sessionsByStreamId) {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(json));
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
