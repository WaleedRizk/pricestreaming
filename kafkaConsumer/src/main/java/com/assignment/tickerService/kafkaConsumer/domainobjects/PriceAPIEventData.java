package com.assignment.tickerService.kafkaConsumer.domainobjects;

import com.assignment.tickerService.kafkaConsumer.domainobjects.common.EventData;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceAPIEventData implements EventData {
	
	private String instrument;
	private double bid;
	private double ask;
	private String vendor;
	private long date;
	
	public double getBid() {
		return bid;
	}

	public void setBid(double bid) {
		this.bid = bid;
	}

	public double getAsk() {
		return ask;
	}

	public void setAsk(double ask) {
		this.ask = ask;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getInstrument() {
		return instrument;
	}

	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}
 

}

