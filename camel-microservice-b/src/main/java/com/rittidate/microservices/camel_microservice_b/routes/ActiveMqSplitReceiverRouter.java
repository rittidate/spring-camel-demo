package com.rittidate.microservices.camel_microservice_b.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqSplitReceiverRouter extends RouteBuilder {

    @Autowired
    private MyCurrencyExchangeProcessor myCurrencyExchangeProcessor;

    @Autowired
    private MyCurrencyExchangeTransformer myCurrencyExchangeTransformer;

    @Override
    public void configure() throws Exception {
        from("activemq:split-queue")
//        .unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
//        .bean(myCurrencyExchangeProcessor)
//        .bean(myCurrencyExchangeTransformer)
        .log("${body}")
        .to("log:received-message-from-active-mq");
    }
}