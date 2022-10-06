package ua.ponarin.trivia.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import ua.ponarin.trivia.model.CustomPrincipal;
import ua.ponarin.trivia.util.JwtUtils;

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

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    var token = message
                            .getHeaders()
                            .get("nativeHeaders", LinkedMultiValueMap.class)
                            .get("token")
                            .get(0)
                            .toString();
                    var userId = JwtUtils.parseClaim("userId", Long.class, token);
                    accessor.setUser(new CustomPrincipal(userId.toString()));
                }
                return message;
            }
        });
    }
}
