package com.assignment.tickerService.kafkaConsumer.domainobjects;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Ticker {
    String ticker;
    BigDecimal open;
    BigDecimal price;
    BigDecimal high;
    BigDecimal low;
    BigDecimal volume;
    BigDecimal date;
}
