package com.assignment.tickerService.kafkaConsumer.view.service;

import com.assignment.tickerService.kafkaConsumer.view.TickerActivityView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "prototype")
public class TickerViewFactory {

    @Autowired
    private TickerActivityView tickerActivityView;
    public TickerActivityView getTickerActivityView() {return tickerActivityView;}
}

