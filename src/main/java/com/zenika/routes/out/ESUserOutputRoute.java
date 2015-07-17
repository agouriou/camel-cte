package com.zenika.routes.out;

import com.zenika.routes.LogPatterns;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.elasticsearch.ElasticsearchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by armel on 15/07/15.
 */
public class ESUserOutputRoute extends RouteBuilder {

    @Value("${camel.endpoint.esuseroutput}")
    private String physicalESUserOutput;

    @Value("${camel.endpoint.technicalerroroutput}")
    private String technicalErrorOutput;

    @Autowired
    private JsonAggregatorStrategy jsonAggregatorStrategy;

    /**
     * transforme les users en json, les aggrége et les envoi par paquet au ES
     * @throws Exception
     */
    @Override
    public void configure() throws Exception {

        onException(ElasticsearchException.class)
                .id("UserESException")
                .to(LogPatterns.logAllWarnForRoute(this.getClass().getName()))
                .handled(true)
                .maximumRedeliveries(2)
                .delay(1000)
                //body est une list aggrégée
                .split().body()
                .convertBodyTo(String.class)
                .transform(body().append("\n"))
                .to(technicalErrorOutput);

        from("direct:esUsersOutput")
                .id("ESUserOutputRoute")
                .setHeader(ESHeaders.TARGET_INDEX.name(), constant("user"))
                .to(LogPatterns.logAllDebugForRoute(this.getClass().getName()))
                .marshal().json(JsonLibrary.Jackson)
//                .aggregate(constant(1), AggregationStrategies.bean(jsonAggregatorStrategy)).completionSize(50).completionTimeout(1000)
                .aggregate(constant(1), jsonAggregatorStrategy).completionSize(50).completionTimeout(1000)
                .to(LogPatterns.logAllDebugForRoute(this.getClass().getName()))
                .to(physicalESUserOutput)
                .log("User Indexing done. Id : ${body}");
    }
}
