package fr.istic.sit.codisgroupea.config;

import fr.istic.sit.codisgroupea.handler.HttpHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import static fr.istic.sit.codisgroupea.Constante.REGISTER_STOMP_END_POINTS;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic","/queue","/user");
        config.setApplicationDestinationPrefixes("");
    }

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint(REGISTER_STOMP_END_POINTS).addInterceptors(new HttpHandshakeInterceptor());
    }



}