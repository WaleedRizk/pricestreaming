package com.assignment.tickerService.kafkaConsumer.view;

import com.assignment.tickerService.kafkaConsumer.domainobjects.PriceAPIEventData;
import com.assignment.tickerService.kafkaConsumer.domainobjects.PriceViewData;
import com.assignment.tickerService.kafkaConsumer.domainobjects.TickerAPIEventData;
import com.assignment.tickerService.kafkaConsumer.domainobjects.TickerViewData;
import com.assignment.tickerService.kafkaConsumer.domainobjects.common.EventData;
import com.assignment.tickerService.kafkaConsumer.domainobjects.common.ViewData;
import com.assignment.tickerService.kafkaConsumer.messagehandler.TickerMessageHandler;
import lombok.Getter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TickerActivityView extends View {

    @Override
    public void initMessageHandlers() {
        TickerMessageHandler tickerMessageHandler = messageHandlerFactory.tickerMessageHandler();
        tickerMessageHandler.init(this);
        messageHandlers.add(tickerMessageHandler);
    }

    //@Override
    public void processMessage(EventData eventData) {
        if(eventData instanceof TickerAPIEventData) {
            TickerAPIEventData tickerAPIEventData = (TickerAPIEventData) eventData;
            TickerViewData tickerViewData = new TickerViewData();
            tickerViewData.setTicker(tickerAPIEventData.getTicker());
            tickerViewData.setDate(tickerAPIEventData.getDate());
            tickerViewData.setHigh(tickerAPIEventData.getHigh());
            tickerViewData.setLow(tickerAPIEventData.getLow());
            tickerViewData.setPrice(tickerAPIEventData.getPrice());
            tickerViewData.setOpen(tickerAPIEventData.getOpen());
            tickerViewData.setVolume(tickerAPIEventData.getVolume());
            sendDataToUserSession(tickerViewData);
        }
    }

    @Override
    public List<ViewData> getInitialData() {
        return null;
    }

	@Override
	public void processMessage(List<PriceAPIEventData> eventData) {
		List priceViewDataList = new ArrayList<PriceViewData>();
		if(eventData instanceof List) {
			for (PriceAPIEventData p :eventData) {
				PriceViewData priceViewData = new PriceViewData();
				priceViewData.setInstrument(p.getInstrument());
				priceViewData.setAsk(p.getAsk());
				priceViewData.setBid(p.getBid());
				priceViewData.setVendor(p.getVendor());
				priceViewData.setDate(p.getDate());
				//priceViewDataList.add(priceViewData);
				sendDataToUserSession(priceViewData);
			}

            
        }
		
	}

}
