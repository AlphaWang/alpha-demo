package com.alphawang.demo.alphawebsocket;

import com.alphawang.demo.alphawebsocket.server.HttpHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry broker) {
        /**
         * Set up a simple memory-based message broker 
         * to carry the messages back to the client on destinations prefixed with “/topic” and “/queue”.
         */
        broker.enableSimpleBroker("/topic", "/queue");
        broker.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry endpoint) {
        /**
         * Registered stomp endpoints at “/greeting”.
         */
        endpoint.addEndpoint("/greeting")
//                .addInterceptors(new HttpHandshakeInterceptor())
                .withSockJS();
    }
}
