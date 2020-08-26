package com.assignment.tickerService.kafkaConsumer.messagehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.assignment.tickerService.kafkaConsumer.domainobjects.PriceAPIEventData;
import com.assignment.tickerService.kafkaConsumer.domainobjects.TickerAPIEventData;
import com.assignment.tickerService.kafkaConsumer.view.MessageProcessor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;


@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TickerMessageHandler implements TradingMessageHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TickerMessageHandler.class);

    @Value("${useKafkaMessageBus}")
    private boolean useKafkaMessageBus = false;

    @Autowired
    private PublishSubscribeChannel tickerChannel;

    private MessageProcessor<List<PriceAPIEventData>> messageProcessor;

    @Override
    public boolean init(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
        return this.tickerChannel.subscribe(this);
    }

    @Override
    public boolean close() {
        return this.tickerChannel.unsubscribe(this);
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        GenericMessage objectMessage = (GenericMessage) message;

        if (useKafkaMessageBus) {
            String jsonStr = (String) objectMessage.getPayload();
//        Object unwrapped = MySerializationUtils.deserialize(objectMessage);

//        if (unwrapped instanceof ServerEvent) {
//            ServerEvent serverEvent = (ServerEvent) unwrapped;
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                //Map hashMap = new HashMap();


                /**
                 * Convert message params map to JSON
                 */
/*
                for (Map.Entry obj : (Set<Map.Entry>) serverEvent.getParameters().entrySet()) {
                    if (obj.getKey().equals("InvocationRequest")) {
                        continue;
                    }
                    hashMap.put(obj.getKey(), obj.getValue());
                }
                String jsonStr = objectMapper.writeValueAsString(hashMap);
*/


                /**
                 * Convert JSON to domain objects
                 */
                objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
                objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

                try {
                    List<PriceAPIEventData> eventData = objectMapper.readValue(jsonStr, new TypeReference<List<PriceAPIEventData>>(){});
                    LOGGER.info("received message=" + jsonStr, jsonStr);
                    messageProcessor.processMessage(eventData);

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

