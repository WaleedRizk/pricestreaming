package com.assignment.tickerService.kafkaConsumer.domainobjects;

import com.assignment.tickerService.kafkaConsumer.domainobjects.common.EventData;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TickerAPIEventData implements EventData {
    String ticker;
    BigDecimal open;
    BigDecimal price;
    BigDecimal high;
    BigDecimal low;
    BigDecimal volume;
    BigDecimal date;
    
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public BigDecimal getOpen() {
		return open;
	}
	public void setOpen(BigDecimal open) {
		this.open = open;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getHigh() {
		return high;
	}
	public void setHigh(BigDecimal high) {
		this.high = high;
	}
	public BigDecimal getLow() {
		return low;
	}
	public void setLow(BigDecimal low) {
		this.low = low;
	}
	public BigDecimal getVolume() {
		return volume;
	}
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	public BigDecimal getDate() {
		return date;
	}
	public void setDate(BigDecimal date) {
		this.date = date;
	}
}


