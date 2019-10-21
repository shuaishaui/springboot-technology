package com.shuai.springbootwebsocket.wechat.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig1 implements WebSocketConfigurer {

    @Autowired
    private MessageHandler messageHandler;
    @Autowired
    private MessageHandshakeInterceptor messageHandshakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(this.messageHandler, "/ws/{uid}")
                .setAllowedOrigins("*")
                .addInterceptors(this.messageHandshakeInterceptor);
    }
}