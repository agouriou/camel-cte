package com.zenika.routes.out;

import com.zenika.routes.LogPatterns;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by armel on 15/07/15.
 */
public class ESAddressOutpoutRoute extends RouteBuilder {

    @Value("${camel.endpoint.esaddressoutput}")
    private String physicalESAddressOutput;

    @Override
    public void configure() throws Exception {

        from("direct:esAddressOutput")
                .id("ESAddressOutputRoute")
                .to(LogPatterns.logAllDebugForRoute(this.getClass().getName()))
                .marshal().json(JsonLibrary.Jackson)
                .to(LogPatterns.logAllDebugForRoute(this.getClass().getName()))
                .to(physicalESAddressOutput)
                .log("Address indexing done. Id : ${body}");
    }
}
