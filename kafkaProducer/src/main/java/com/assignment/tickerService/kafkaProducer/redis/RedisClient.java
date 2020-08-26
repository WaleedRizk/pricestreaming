package com.assignment.tickerService.kafkaProducer.redis;

import com.assignment.tickerService.kafkaProducer.config.ConfigConstants;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.List;
import java.util.stream.Collectors;


public class RedisClient {

	private RedissonClient client;
	
	public static RedisClient instance = new RedisClient();
	
	
	private RedisClient() {
		Config config = new Config();
		config.useSingleServer().setAddress(ConfigConstants.instance.getREDIS_URL());
		client = Redisson.create();
	}
	
	public boolean addDataToCache(String vendor, String value) {
		try {
			RMap<String, String> vendorMap = client.getMap("vendorMap");
			String data = vendorMap.get(vendor);
			if(data != null && data.equals(value)) {
				return false;
			}
			vendorMap.put(vendor, value);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String getDataForVendor(String vendor) {
		RMap<String, String> vendorMap = client.getMap("vendorMap");
		return vendorMap.get(vendor);
	}
	
	public List<String> getAllDataForVendors() {
		RMap<String, String> vendorMap = client.getMap("vendorMap");
		List<String> vendors = vendorMap.values().stream().collect(Collectors.toList());
		return vendors;
	}

	
	
}
