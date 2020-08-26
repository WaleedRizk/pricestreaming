package com.assignment.tickerService.kafkaConsumer.view;

import java.util.List;

import com.assignment.tickerService.kafkaConsumer.domainobjects.PriceAPIEventData;

@FunctionalInterface
public interface MessageProcessor<P> {
    public void processMessage(List<PriceAPIEventData> eventData);
}
