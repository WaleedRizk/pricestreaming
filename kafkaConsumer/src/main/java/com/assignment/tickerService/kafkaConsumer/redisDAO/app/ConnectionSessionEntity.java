package com.assignment.tickerService.kafkaConsumer.redisDAO.app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@RedisHash("connectionsession")
@AllArgsConstructor
@Getter
@Setter
public class ConnectionSessionEntity implements Serializable {

    private String websocketSessionID;

    private String userID;

    @Id
    private String viewID;

    @Indexed
    private String clientViewID;
}
