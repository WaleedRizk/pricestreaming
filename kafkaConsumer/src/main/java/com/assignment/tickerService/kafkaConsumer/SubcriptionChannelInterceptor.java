package com.assignment.tickerService.kafkaConsumer;

import com.assignment.tickerService.kafkaConsumer.view.service.TickerViewManager;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

public class SubcriptionChannelInterceptor implements ChannelInterceptor {

    private static final String subQueuePrefix = ServiceConstants.SLASH_SEPARATOR + ServiceConstants.USER_PREFIX + ServiceConstants.SLASH_SEPARATOR + ServiceConstants.QUEUE_PREFIX + ServiceConstants.SLASH_SEPARATOR;
    private static final String topicQueuePrefix = ServiceConstants.SLASH_SEPARATOR + ServiceConstants.USER_PREFIX + ServiceConstants.SLASH_SEPARATOR + ServiceConstants.TOPIC_PREFIX + ServiceConstants.SLASH_SEPARATOR;

    private TickerViewManager tickerViewManager;

    public SubcriptionChannelInterceptor(TickerViewManager tickerViewManager) {
        this.tickerViewManager = tickerViewManager;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor headerAccessor= StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {

        } else if (StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand())) {
            String userName = headerAccessor.getFirstNativeHeader(ServiceConstants.USERNAME_HEADER);

            if(userName == null || userName.trim().equals("")) {
                //handle no user here

            } else if(headerAccessor.getDestination() != null && headerAccessor.getDestination().contains(subQueuePrefix) ||
                    headerAccessor.getDestination() != null && headerAccessor.getDestination().contains(topicQueuePrefix)) {
                String sessionID = (String) headerAccessor.getHeader("simpSessionId");
                String simpSubscriptionId = (String) headerAccessor.getHeader("simpSubscriptionId");
                tickerViewManager.createView(headerAccessor.getDestination(), sessionID, simpSubscriptionId, userName, headerAccessor.toNativeHeaderMap());
            }

        } else if (StompCommand.UNSUBSCRIBE.equals(headerAccessor.getCommand())) {
            String sessionID = (String) headerAccessor.getHeader("simpSessionId");
            String simpSubscriptionId = (String) headerAccessor.getHeader("simpSubscriptionId");
            tickerViewManager.deleteView(sessionID,simpSubscriptionId);

        } else if (StompCommand.SEND.equals(headerAccessor.getCommand())) {
            String sessionID = (String) headerAccessor.getHeader("simpSessionId");
            String simpSubscriptionId = headerAccessor.getNativeHeader("id").get(0);
            String action = (String) headerAccessor.toNativeHeaderMap().get("action").get(0);
            if(action != null) {
                tickerViewManager.handleViewAction(sessionID, simpSubscriptionId, action, message);
            }
        }
    }
}
