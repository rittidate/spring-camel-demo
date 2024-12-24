package com.rittidate.microservices.camel_microservice_a.routes.a;

import org.apache.camel.ExchangeProperties;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class MyFileRouter extends RouteBuilder {

    @Autowired
    private DeciderBean deciderBean;

    @Override
    public void configure() throws Exception {
        // Pipeline pattern
        // Content Based Pattern

        from("file:files/input")
        .routeId("Files-Input-Route")
        .transform().body(String.class)
        .choice()
            .when(simple("${file:ext} ends with 'xml'"))
                .log("XML FILE")
            .when(method(deciderBean))
//            .when(simple("${body} contains 'USD'"))
                .log("Not XML FILE BUT contains USD")
            .otherwise()
                .log("Not an XML FILE")
        .end()
        .to("direct:log-file-values")
        .to("file:files/output");

        from("direct:log-file-values")
        .log("${messageHistory} ${headers.CamelFileAbsolute}")
        .log("${file:name} ${file:name.ext} ${file:name.noext} ${file:onlyname}")
        .log("${file:onlyname.noext} ${file:parent} ${file:path} ${file:absolute}")
        .log("${file:size} ${file:modified}")
        .log("${routeId} ${camelId} ${body}");
    }
}

@Component
class DeciderBean {
    Logger logger = LoggerFactory.getLogger(DeciderBean.class);

    public boolean isThisConditionMet(String body,
                                      @Headers Map<String, String> headers,
                                      @ExchangeProperties Map<String, String> exchangeProperties) {
        logger.info("DeciderBean {} {} {}", body, headers, exchangeProperties);
        return true;
    }
}
