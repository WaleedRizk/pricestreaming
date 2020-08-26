package com.assignment.tickerService.kafkaConsumer.config.factory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConnectionFactoryConfig {

    @Value("${spring.redis.host}")
    private String redisHostName;

    @Value("${spring.redis.port}")
    private int port;

    @Bean
    LettuceConnectionFactory appChacheRedisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisHostName, port);
        LettuceConnectionFactory lettuceConFactory = new LettuceConnectionFactory(configuration);
        return lettuceConFactory;
    }

    @Bean
    public RedisTemplate<String, Object> appCacheRedisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(appChacheRedisConnectionFactory());
        return template;
    }
}
