package com.assignment.tickerService.kafkaConsumer.config.service;

import com.assignment.tickerService.kafkaConsumer.SubcriptionChannelInterceptor;
import com.assignment.tickerService.kafkaConsumer.view.service.TickerViewManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.config.SimpleBrokerRegistration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@EnableAsync
public class ApplicationConfig  implements WebSocketMessageBrokerConfigurer {

    @Autowired
    TickerViewManager tickerViewManager;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        SimpleBrokerRegistration registration = config.enableSimpleBroker("/topic","/queue");
        config.setUserDestinationPrefix("/user");
        config.setApplicationDestinationPrefixes("/com/assignment/tickerService/kafkaConsumer/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/tickerservice-websocket")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        SubcriptionChannelInterceptor subcriptionChannelInterceptor = new SubcriptionChannelInterceptor(tickerViewManager);
        registration.interceptors(subcriptionChannelInterceptor);
    }
}
