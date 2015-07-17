package com.zenika.routes.out;

import com.zenika.routes.LogPatterns;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.elasticsearch.ElasticsearchException;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by armel on 15/07/15.
 */
public class ESAddressOutpoutRoute extends RouteBuilder {

    @Value("${camel.endpoint.esaddressoutput}")
    private String physicalESAddressOutput;

    @Value("${camel.endpoint.technicalerroroutput}")
    private String technicalErrorOutput;

    /**
     * Envoi un par un des addresse (cf ESUserOutputRoute pour envoie par paquet)
     * @throws Exception
     */
    @Override
    public void configure() throws Exception {

        onException(ElasticsearchException.class)
                .id("AddressESException")
                .to(LogPatterns.logAllWarnForRoute(this.getClass().getName()))
                .handled(true)
                .maximumRedeliveries(2)
                .delay(1000)
                .transform(body().append("\n"))
                .to(technicalErrorOutput);

        from("direct:esAddressOutput")
                .id("ESAddressOutputRoute")
                .setHeader(ESHeaders.TARGET_INDEX.name(), constant("address"))
                .to(LogPatterns.logAllDebugForRoute(this.getClass().getName()))
                .marshal().json(JsonLibrary.Jackson)
                .to(LogPatterns.logAllDebugForRoute(this.getClass().getName()))
                .to(physicalESAddressOutput)
                .log("Address indexing done. Id : ${body}");
    }
}
