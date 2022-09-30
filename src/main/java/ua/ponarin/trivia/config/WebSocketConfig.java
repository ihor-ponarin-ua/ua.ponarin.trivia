package ua.ponarin.trivia.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Value("${ws.config.handshakeEndpoint}")
    private String handshakeEndpoint;

    @Value("${ws.config.brokerPrefix}")
    private String brokerPrefix;

    @Value("${ws.config.applicationDestinationPrefix}")
    private String applicationDestinationPrefix;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint(handshakeEndpoint)
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(brokerPrefix);
        registry.setApplicationDestinationPrefixes(applicationDestinationPrefix);
    }
}
