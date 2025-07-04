package amnis.notificationservice.config.websocket;

import amnis.notificationservice.services.websocket.GiftWebsocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebConfig implements WebSocketConfigurer {
    private final GiftWebsocketHandler giftWebsocketHandler;

    public WebConfig(GiftWebsocketHandler giftWebsocketHandler) {
        this.giftWebsocketHandler = giftWebsocketHandler;
    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(this.giftWebsocketHandler, "/ws/gift").setAllowedOrigins("*");
    }
}
