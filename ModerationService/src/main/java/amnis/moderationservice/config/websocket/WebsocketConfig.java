package amnis.moderationservice.config.websocket;

import amnis.moderationservice.services.websocket.ModerationHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {
    private final ModerationHandler websocketHandler;

    public WebsocketConfig(ModerationHandler websocketHandler) {
        this.websocketHandler = websocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(this.websocketHandler, "/ws/mod/blocked");
    }
}
