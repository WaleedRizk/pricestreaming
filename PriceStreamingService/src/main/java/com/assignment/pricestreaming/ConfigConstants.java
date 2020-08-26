package com.assignment.pricestreaming;

public class ConfigConstants {
	
	public static final String ENDPOINT_GET_PRICES_FOR_INSTRUMENT = "/getPricesForInstrument/:isin";
	public static final String ENDPOINT_GET_PRICES_FOR_VENDOR = "/getPricesForVendor/:vendor";
	public static final String ENDPOINT_GET_ALL_PRICES = "/getAllPrices";
	public static final long THIRTY_DAYS = 60 * 1000; //30 * 24 * 60 * 60 * 1000;

}
