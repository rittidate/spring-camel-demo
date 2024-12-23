package com.rittidate.microservices.camel_microservice_a.routes.a;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyFileRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file:files/input")
        .routeId("Files-Input-Route")
        .transform().body(String.class)
        .choice()
            .when(simple("${file:ext} ends with 'xml'"))
                .log("XML FILE")
            .when(simple("${body} contains 'USD'"))
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
