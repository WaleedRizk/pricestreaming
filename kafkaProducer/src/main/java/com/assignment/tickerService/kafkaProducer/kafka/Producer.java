package com.assignment.tickerService.kafkaProducer.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import com.assignment.tickerService.kafkaProducer.config.ConfigConstants;

public class Producer {

	private KafkaProducer producer;
	
	public static Producer instance = new Producer();
	
	private Producer() {
		Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ConfigConstants.instance.getKAFKA_BROKERS());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producer = new KafkaProducer<>(props);
	}
	
	public void sendMessage(String message) {
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(ConfigConstants.instance.getTOPIC_NAME(), message);
		producer.send(record);
		System.out.println("Produced: " + record);
		producer.flush();
	}
}
