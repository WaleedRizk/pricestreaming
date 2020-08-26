package com.assignment.tickerService.kafkaProducer.scheduler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Optional;

import com.assignment.tickerService.kafkaProducer.config.ConfigConstants;
import com.assignment.tickerService.kafkaProducer.kafka.Producer;
import com.assignment.tickerService.kafkaProducer.redis.RedisClient;

public class DataScheduler implements Runnable {

	@Override
	public void run() {
		List<String> vendors = List.of("Vendor1", "Vendor2", "Vendor3","Vendor4", "Vendor5", "Vendor6");
		vendors.forEach(v -> {
			Optional.ofNullable(fetchDataFromDataSource(v))
					.map(s -> {
						if(RedisClient.instance.addDataToCache(v, s)) {
							Producer.instance.sendMessage(RedisClient.instance.getDataForVendor(v));
						}
						return s;
					}).orElse(null);
		});
	}

	private String fetchDataFromDataSource(String vendor) {
		StringBuilder content = new StringBuilder();
		try
		{
			URL url = new URL(ConfigConstants.instance.getDATA_SOURCE_URL()+vendor);
			URLConnection urlConnection = url.openConnection();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String line;
			while ((line = bufferedReader.readLine()) != null)
			{
				content.append(line);
			}
			bufferedReader.close();
		}
		catch(Exception e) {
			return null;
		}
		return content.toString();
	}
}
