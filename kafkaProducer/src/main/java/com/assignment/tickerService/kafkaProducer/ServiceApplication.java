package com.assignment.tickerService.kafkaProducer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.assignment.tickerService.kafkaProducer.scheduler.DataScheduler;

@SpringBootApplication
public class ServiceApplication {

    public static void main(String[] args) {

        initProducer();
        SpringApplication.run(ServiceApplication.class, args);
    }

    private static void initProducer() {
    	ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
		scheduledExecutorService.scheduleAtFixedRate(new DataScheduler(), 1, 1, TimeUnit.SECONDS);
    }
}
