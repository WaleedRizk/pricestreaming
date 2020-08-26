package com.assignment.tickerService.kafkaConsumer.messagehandler;

import com.assignment.tickerService.kafkaConsumer.view.MessageProcessor;
import org.springframework.messaging.MessageHandler;

public interface TradingMessageHandler extends MessageHandler {
    public boolean init(MessageProcessor processor);
    public boolean close();
}
