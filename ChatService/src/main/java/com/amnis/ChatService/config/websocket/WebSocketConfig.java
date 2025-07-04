package com.amnis.ChatService.config.websocket;

import com.amnis.ChatService.services.ChatMessageHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistration;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final ChatMessageHandler chatMessageHandler;

    protected WebSocketConfig(ChatMessageHandler chatMessageHandler) {
        this.chatMessageHandler = chatMessageHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(this.chatMessageHandler, "/ws/chat")
                .setAllowedOrigins("*");
    }
}
