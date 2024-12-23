package com.rittidate.microservices.camel_microservice_b.routes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MyCurrencyExchangeProcessor {
    Logger logger = LoggerFactory.getLogger(MyCurrencyExchangeProcessor.class);

    public void processMessage(CurrencyExchange currencyExchange) {
        logger.info("Do some processing with currencyExchange.getConversionMultiple() value which is {}",
                currencyExchange.getConversionMultiple());
    }
}
