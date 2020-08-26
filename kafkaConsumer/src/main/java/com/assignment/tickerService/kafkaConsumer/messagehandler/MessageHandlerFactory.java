package com.assignment.tickerService.kafkaConsumer.messagehandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "prototype")
public class MessageHandlerFactory {

    @Autowired
    TickerMessageHandler tickerMessageHandler;

    @Lookup
    public TickerMessageHandler tickerMessageHandler(){return tickerMessageHandler;}

}
