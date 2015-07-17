package com.zenika.routes.splitcbr;

import com.zenika.routes.LogPatterns;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by armel on 15/07/15.
 */
public class CBRRoute extends RouteBuilder {

    @Autowired
    private UserAddressCBRRouter userAddressCBRRouter;

    @Override
    public void configure() throws Exception {
        from("direct:cbrRoute")
                .id("cbrRoute")
                .to(LogPatterns.logAllDebugForRoute(this.getClass().getName()))
                .choice()
                    .when(method(userAddressCBRRouter, "isUser"))
                        .to("direct:csvToUser")
                    .when(method(userAddressCBRRouter, "isAddress"))
                        .to("direct:csvToAddress")
                    .otherwise()
                        .log(LoggingLevel.WARN, "Unknown type [${body}]")
                        .to("direct:unknownOutput");

    }
}
