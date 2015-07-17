package com.zenika.routes.out;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by armel on 16/07/15.
 */
public class UnknownOutputRoute extends RouteBuilder {

    @Value("${camel.endpoint.unknownoutput}")
    private String unknownOutput;

    @Override
    public void configure() throws Exception {

        from("direct:unknownOutput")
                .transform(body().append("\n"))
                .to(unknownOutput);
    }
}
