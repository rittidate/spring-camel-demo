package com.rittidate.microservices.camel_microservice_a.routes.a;

import org.apache.camel.Body;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.Expression;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EipPatternSlipRouter extends RouteBuilder {

    @Autowired
    DynamicRouterBean dynamicRouterBean;

    @Override
    public void configure() throws Exception {
        String routingSlip = "direct:endpoint1,direct:endpoint3";
//
//        from("timer:routingSlip?period=10000")
//        .transform().constant("My Message is Hardcoded")
//        .routingSlip(simple(routingSlip));

        from("timer:dynamicRouting?period=10000")
        .transform().constant("My Message is Hardcoded")
        .dynamicRouter(method(dynamicRouterBean));

        from("direct:endpoint1")
        .to("log:directendpoint1");


        from("direct:endpoint2")
        .to("log:directendpoint2");

        from("direct:endpoint3")
        .to("log:directendpoint3");

    }
}

@Component
class DynamicRouterBean {
    Logger logger = LoggerFactory.getLogger(DynamicRouterBean.class);

    int invocations;

    public String decideTheNextEndpoint(
            @ExchangeProperties Map<String, String> properties,
            @Headers Map<String, String> headers,
            @Body String body
    ) {
        logger.info("{} {} {}", properties, headers, body);

        invocations++;

        if(invocations%3==0)
            return "direct:endpoint1";

        if(invocations%3==1)
            return "direct:endpoint2,direct:endpoint3";

        return null;
    }
}