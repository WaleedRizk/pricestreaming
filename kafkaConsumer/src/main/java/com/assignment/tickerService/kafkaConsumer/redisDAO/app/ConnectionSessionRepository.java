package com.assignment.tickerService.kafkaConsumer.redisDAO.app;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionSessionRepository extends CrudRepository<ConnectionSessionEntity, String> {
    ConnectionSessionEntity findByViewID(String inquiryHandlerID);
    ConnectionSessionEntity findByClientViewID(String inventoryHandlerID);

}
