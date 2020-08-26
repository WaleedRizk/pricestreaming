package com.assignment.tickerService.kafkaConsumer.view.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.assignment.tickerService.kafkaConsumer.domainobjects.PriceAPIEventData;
import com.assignment.tickerService.kafkaConsumer.domainobjects.TickerAPIEventData;
import com.assignment.tickerService.kafkaConsumer.domainobjects.common.EventData;
import com.assignment.tickerService.kafkaConsumer.redis.RedisClient;
import com.assignment.tickerService.kafkaConsumer.view.View;
import com.assignment.tickerService.kafkaConsumer.view.ViewType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.assignment.tickerService.kafkaConsumer.ServiceConstants.*;

@Service
public class TickerViewManager {

    ConcurrentHashMap<String, View> viewByIDMap = new ConcurrentHashMap<>();

    @Autowired
    TickerViewFactory tickerViewFactory;

    @Autowired
    RedisClient redisClient;

    protected View createNewTickerView(Map<String, String> params){
        return tickerViewFactory.getTickerActivityView().init(params);
    }
   @Async
    public void createView(String destination, String sessionID, String subscriptionID, String userName, Map<String, List<String>> stringListMap) {
        String[] array = destination.split("/");
        String destPrefixType = array[2];
        String destType= array[3];
        String actualDest = SLASH_SEPARATOR + destPrefixType + SLASH_SEPARATOR + destType;
        Map params = new HashMap<>();
        params.put(SUB_USER_NAME, userName);
        params.put(SUB_SESSION_ID, sessionID);
        params.put(SUB_QUEUE, actualDest);
        params.put(SUB_SUBSCRIPTION_ID, subscriptionID);
        params.putAll(stringListMap);

        View newViewCreated = null;
        if(ViewType.TICKER_ACTIVITY.getViewName().equals(destType)) {
            newViewCreated = createNewTickerView(params);

        }
        if(newViewCreated != null) {
            viewByIDMap.put(sessionID+":"+subscriptionID, newViewCreated);
        }
        List<String> vendors = redisClient.getAllDataForVendors();
        sendInitialData(vendors,newViewCreated);
    }

    @Async
    public void deleteView(String sessionID, String subscriptionID){
        String viewID = sessionID+":"+subscriptionID;

        View view = viewByIDMap.get(viewID);
        if(view != null) {
            view.closeView();
        }
        viewByIDMap.remove(viewID);
    }

    @Async
    public void handleViewAction(String sessionID, String subscriptionID, String action, Message message){
        String viewID = sessionID+":"+subscriptionID;
        View view = viewByIDMap.get(viewID);
        String jsonStr = new String((byte[])message.getPayload());
    }

    private void sendInitialData(List<String> vendors,View view)
    {
        ObjectMapper objectMapper = new ObjectMapper();

        vendors.forEach(
                t -> {
                    try {
                        List<PriceAPIEventData> eventData = objectMapper.readValue(t, new TypeReference<List<PriceAPIEventData>>(){});//PriceAPIEventData.class);
                        view.processMessage(eventData);;
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
        );
    }
}
