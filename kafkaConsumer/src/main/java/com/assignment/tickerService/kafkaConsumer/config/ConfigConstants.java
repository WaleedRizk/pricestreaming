package com.assignment.tickerService.kafkaConsumer.config;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
public class ConfigConstants {

	String TOPIC_NAME ;
	String KAFKA_BROKERS;
	String REDIS_URL;
	private Properties properties;
	public static ConfigConstants instance = new ConfigConstants();
	private ConfigConstants()
	{
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		properties = new Properties();
		try(InputStream resourceStream = loader.getResourceAsStream("application.properties")){
			properties.load(resourceStream);
		}catch(IOException e){
			e.printStackTrace();
		}
		TOPIC_NAME = properties.getProperty("tickerAPIEventTopic");
		KAFKA_BROKERS = properties.getProperty("KAFKA_BROKERS");
		REDIS_URL = properties.getProperty("REDIS_URL");
	}
	public String getTOPIC_NAME() {
		return TOPIC_NAME;
	}
	public void setTOPIC_NAME(String tOPIC_NAME) {
		TOPIC_NAME = tOPIC_NAME;
	}
	public String getKAFKA_BROKERS() {
		return KAFKA_BROKERS;
	}
	public void setKAFKA_BROKERS(String kAFKA_BROKERS) {
		KAFKA_BROKERS = kAFKA_BROKERS;
	}
	public String getREDIS_URL() {
		return REDIS_URL;
	}
	public void setREDIS_URL(String rEDIS_URL) {
		REDIS_URL = rEDIS_URL;
	}
	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
}
