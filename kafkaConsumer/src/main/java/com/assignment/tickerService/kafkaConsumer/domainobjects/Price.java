package com.assignment.tickerService.kafkaConsumer.domainobjects;

public class Price {
	
	private double bid;
	private double ask;
	private String vendor;
	private long date;
	
	public Price(double bid, double ask, String vendor, long date) {
		super();
		this.bid = bid;
		this.ask = ask;
		this.vendor = vendor;
		this.date = date;
	}

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
 

}

