package com.zenika.routes.in;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by armel on 15/07/15.
 */
public class CsvInputRoute extends RouteBuilder{


    @Value("${camel.endpoint.inputcsv}")
    private String endpointInputCsv;

    @Override
    public void configure() throws Exception {
        from(endpointInputCsv)
                .id("physicalInputCsv")
                .to("direct:inputCsv");
    }
}
