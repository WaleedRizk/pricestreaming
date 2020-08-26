package com.assignment.tickerService.kafkaConsumer.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.assignment.tickerService.kafkaConsumer.domainobjects.common.EventData;
import com.assignment.tickerService.kafkaConsumer.domainobjects.common.ViewData;
import com.assignment.tickerService.kafkaConsumer.messagehandler.MessageHandlerFactory;
import com.assignment.tickerService.kafkaConsumer.messagehandler.TradingMessageHandler;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.assignment.tickerService.kafkaConsumer.ServiceConstants.*;

public abstract class View implements MessageProcessor<EventData> {

    @Getter
    private String id = UUID.randomUUID().toString();

    protected String sessionID;
    protected String subscriptionID;
    protected String destinationQueue;
    protected String userName;
    protected List<TradingMessageHandler> messageHandlers = new ArrayList<>();
    @Autowired
    protected MessageHandlerFactory messageHandlerFactory;

    @Autowired
    SimpMessagingTemplate template;

/*    @Autowired
    protected ConnectionSessionRepository connectionSessionRepository;

    @Autowired
    protected ConnectionManager connectionManager;*/

    public View init(Map initParams) {
        this.userName = (String) initParams.get(SUB_USER_NAME);
        this.sessionID = (String) initParams.get(SUB_SESSION_ID);
        this.subscriptionID = (String) initParams.get(SUB_SUBSCRIPTION_ID);
        this.destinationQueue = (String) initParams.get(SUB_QUEUE);
        //       ConnectionSessionEntity connectionSessionEntity = new ConnectionSessionEntity(sessionID, null, getId(), clientViewID);
        //     connectionSessionRepository.save(connectionSessionEntity);
        reload();
        initMessageHandlers();
        return this;
    }

//    @Override
//    public void processMessage(EventData eventData) {
//               /*
//                IMPLEMENT THE VIEW LOGIC HERE
//             */
//
//    }


    protected void sendDataToUserSession(ViewData... viewData) {
        try {
        	String viewDataJSON = new ObjectMapper().writeValueAsString(viewData);
            SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
            headerAccessor.setSessionId(sessionID);
            headerAccessor.setSubscriptionId(subscriptionID);
            headerAccessor.setLeaveMutable(true);
            template.convertAndSendToUser(sessionID, destinationQueue, viewDataJSON, headerAccessor.getMessageHeaders());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    protected abstract void initMessageHandlers();

    public void reload() {
        List<ViewData> initialData = getInitialData();
        if(initialData != null) {
            sendDataToUserSession((ViewData[]) initialData.toArray());
        }
    }

    public abstract List<ViewData> getInitialData();

    public void closeView() {
        removeMessageHandlers();
    }

    protected void removeMessageHandlers() {
        messageHandlers.stream().forEach(mh -> mh.close());
    }

}
