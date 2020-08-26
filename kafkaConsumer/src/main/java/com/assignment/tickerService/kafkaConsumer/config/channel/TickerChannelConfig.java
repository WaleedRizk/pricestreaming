package com.assignment.tickerService.kafkaConsumer.config.channel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class TickerChannelConfig {

    @Bean
    public PublishSubscribeChannel tickerChannel() {
        PublishSubscribeChannel channel =  new PublishSubscribeChannel(executor());
        //channel.setMinSubscribers(1);
        return channel;
    }

    public ThreadPoolTaskExecutor executor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(10);
        pool.setMaxPoolSize(15);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        pool.initialize();
        return pool;
    }
}