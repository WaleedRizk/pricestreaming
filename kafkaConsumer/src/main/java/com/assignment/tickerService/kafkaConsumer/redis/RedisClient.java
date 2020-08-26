package com.assignment.tickerService.kafkaConsumer.redis;

import com.assignment.tickerService.kafkaConsumer.config.ConfigConstants;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;


@Configuration
public class RedisClient {

	private RedissonClient client;
	

	public String redisAddress;

	public RedisClient() {
		redisAddress = ConfigConstants.instance.getREDIS_URL();
		Config config = new Config();
		config.useSingleServer().setAddress(redisAddress);
		client = Redisson.create();
	}

	public List<String> getAllDataForTickers() {
		RMap<String, String> tickerMap = client.getMap("tickerMap");
		List<String> tickers = tickerMap.values().stream().collect(Collectors.toList());
		return tickers;
	}
	
	public List<String> getAllDataForVendors() {
		RMap<String, String> vendorMap = client.getMap("vendorMap");
		List<String> vendors = vendorMap.values().stream().collect(Collectors.toList());
		return vendors;
	}
	
	
}
