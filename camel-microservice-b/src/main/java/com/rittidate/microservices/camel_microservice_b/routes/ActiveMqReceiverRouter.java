package com.rittidate.microservices.camel_microservice_b.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqReceiverRouter extends RouteBuilder {

    @Autowired
    private MyCurrencyExchangeProcessor myCurrencyExchangeProcessor;

    @Autowired
    private MyCurrencyExchangeTransformer myCurrencyExchangeTransformer;

    @Override
    public void configure() throws Exception {
        from("activemq:my-activemq-queue")
        .unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
        .bean(myCurrencyExchangeProcessor)
        .bean(myCurrencyExchangeTransformer)
        .to("log:received-message-from-active-mq");
    }
}