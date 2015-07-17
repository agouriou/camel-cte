package com.zenika.routes.out;

import com.zenika.routes.LogPatterns;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by armel on 15/07/15.
 */
public class ESUserOutputRoute extends RouteBuilder {

    @Value("${camel.endpoint.esuseroutput}")
    private String physicalESUserOutput;

    @Autowired
    private JsonAggregatorStrategy jsonAggregatorStrategy;

    @Override
    public void configure() throws Exception {

        from("direct:esUsersOutput")
                .id("ESUserOutputRoute")
                .to(LogPatterns.logAllDebugForRoute(this.getClass().getName()))
                .marshal().json(JsonLibrary.Jackson)
//                .aggregate(constant(1), AggregationStrategies.bean(jsonAggregatorStrategy)).completionSize(50).completionTimeout(1000)
                .aggregate(constant(1), jsonAggregatorStrategy).completionSize(50).completionTimeout(1000)
                .to(LogPatterns.logAllDebugForRoute(this.getClass().getName()))
                .to(physicalESUserOutput)
                .log("User Indexing done. Id : ${body}");
    }
}
