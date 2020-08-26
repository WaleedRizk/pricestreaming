package com.assignment.tickerService.kafkaProducer.config;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
public class ConfigConstants {

	String TOPIC_NAME ;
	String DATA_SOURCE_URL;
	String KAFKA_BROKERS;
	String REDIS_URL;
	private Properties properties;
	public static ConfigConstants instance = new ConfigConstants();
	private ConfigConstants()
	{
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		properties = new java.util.Properties();
		try(InputStream resourceStream = loader.getResourceAsStream("application.properties")){
			properties.load(resourceStream);
		}catch(IOException e){
			e.printStackTrace();
		}
		TOPIC_NAME = properties.getProperty("tickerAPIEventTopic");
		DATA_SOURCE_URL = properties.getProperty("DATA_SOURCE_URL");
		KAFKA_BROKERS = properties.getProperty("KAFKA_BROKERS");
		REDIS_URL = properties.getProperty("REDIS_URL");
	}
	
	public String getTOPIC_NAME() {
		return TOPIC_NAME;
	}
	public void setTOPIC_NAME(String tOPIC_NAME) {
		TOPIC_NAME = tOPIC_NAME;
	}
	public String getDATA_SOURCE_URL() {
		return DATA_SOURCE_URL;
	}
	public void setDATA_SOURCE_URL(String dATA_SOURCE_URL) {
		DATA_SOURCE_URL = dATA_SOURCE_URL;
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
	public static ConfigConstants getInstance() {
		return instance;
	}
	public static void setInstance(ConfigConstants instance) {
		ConfigConstants.instance = instance;
	}
}
