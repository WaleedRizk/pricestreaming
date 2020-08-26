package com.assignment.pricestreaming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class Service {
	
	private static List<String> endpoints = new ArrayList<String>();
	
	private static List<String> vendors = List.of("Vendor1", "Vendor2", "Vendor3","Vendor4", "Vendor5", "Vendor6");
	private static List<String> instruments = List.of("AA123", "BB123", "CC123","DD123", "EE123", "FF123");
	
	private static 	Map<String, List<Price>> allPricesByVendor = new HashMap<String, List<Price>>();
	private static 	Map<String, List<Price>> allVendorsPricesByInstrumentIsin = new HashMap<String, List<Price>>();
	private static List<Price> allPrices = new ArrayList<Price>();
	
	private static Handler pricesForInstrumentHandler = ctx -> {
		String isin = ctx.pathParam("isin");
		if(allVendorsPricesByInstrumentIsin.containsKey(isin) ) {
			ctx.json(allVendorsPricesByInstrumentIsin.get(isin));
		} else {
			ctx.html("No prices found.");
		}
	};
	
	private static Handler pricesForVendorHandler = ctx -> {
		String vendor = ctx.pathParam("vendor");
		if(allPricesByVendor.containsKey(vendor) ) {
			ctx.json(allPricesByVendor.get(vendor));
		} else {
			ctx.html("No prices found.");
		}
	};
	
	private static Handler allPricesHandler = ctx -> {
		if(!allPrices.isEmpty() ) {
			ctx.json(allPrices);
		} else {
			ctx.html("No prices found.");
		}
	};
	
	private static Handler helpHandler = ctx -> {
			ctx.json(endpoints);
	};
	
	private static void init() {
		endpoints.add(ConfigConstants.ENDPOINT_GET_PRICES_FOR_INSTRUMENT);
		endpoints.add(ConfigConstants.ENDPOINT_GET_PRICES_FOR_VENDOR);
		endpoints.add(ConfigConstants.ENDPOINT_GET_ALL_PRICES);
		
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		
		Runnable addPrice = () -> {
			Random random = new Random();
			String vendor = vendors.get(random.nextInt(vendors.size()));
			String instrument = instruments.get(random.nextInt(instruments.size()));
			Price px1 = new Price(instrument, 500-random.nextInt(2), 503+random.nextInt(2), vendor, System.currentTimeMillis());
			List<Price> prices = allPricesByVendor.get(vendor);
			List<Price> oldPrices = new ArrayList<Price>();
			if(prices==null) {
				prices = new ArrayList<Price>();
			}
			prices.add(px1);
			allPrices.add(px1);
			allPricesByVendor.put(vendor, prices );
			//now purge any old prices
			for(String v : vendors) {
				prices = allPricesByVendor.get(v);
				if(prices !=null) {
					for(Price px : prices) {
						if ( px.getDate()<System.currentTimeMillis()-ConfigConstants.THIRTY_DAYS ) {
							oldPrices.add(px);
						}
					}
					
					if(!oldPrices.isEmpty()) {
						prices.removeAll(oldPrices); // do it here so we don't get ConcurrentModificationException
						allPrices.removeAll(oldPrices); 
					}
				}
			}
		};
		
		scheduledExecutorService.scheduleAtFixedRate(addPrice, 1, 1, TimeUnit.SECONDS);
		
	}


	public static void main(String[] args) {
		init();
		Javalin app = Javalin.create();
		app.get(ConfigConstants.ENDPOINT_GET_PRICES_FOR_INSTRUMENT, pricesForInstrumentHandler);
		app.get(ConfigConstants.ENDPOINT_GET_PRICES_FOR_VENDOR, pricesForVendorHandler);
		app.get(ConfigConstants.ENDPOINT_GET_ALL_PRICES, allPricesHandler);
		app.get("/help", helpHandler);
		
		app.start(8080);
		
		
	}

}
