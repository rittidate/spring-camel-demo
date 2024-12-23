package com.rittidate.microservices.camel_microservice_a.routes.a;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//@Component
public class MyFirstTimerRouter extends RouteBuilder {

    @Autowired
    private GetCurrentTimeBean getCurrentTimeBean;

    @Override
    public void configure() throws Exception {
        // timer
        // transformation
        // log
        from("timer:first-timer") //null
        .log("${body}")
//        .transform().constant("My Constant Message ")
//        .bean("getCurrentTimeBean")
        .bean(getCurrentTimeBean)
//        .bean(getCurrentTimeBean, "getCurrentTime")
        .log("${body}")
        .to("log:first-timer");


    }
}

@Component
class GetCurrentTimeBean {
    public String getCurrentTime() {
        return "Time now is" + LocalDateTime.now();
    }
}