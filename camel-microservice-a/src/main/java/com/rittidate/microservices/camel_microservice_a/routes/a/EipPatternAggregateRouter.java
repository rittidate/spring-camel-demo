package com.rittidate.microservices.camel_microservice_a.routes.a;

import org.apache.camel.Expression;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class EipPatternAggregateRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("file:files/aggregate-json")
        .unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
        .aggregate((Expression) simple("${body.to}"), new ArrayListAggregationStrategy())
        .completionSize(3)
        .to("log:aggregate-json");
    }
}
