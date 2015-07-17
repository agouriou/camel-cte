package com.zenika.routes.splitcbr;

import com.zenika.model.address.BindyToAddressTransformer;
import com.zenika.routes.LogPatterns;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BindyType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by armel on 16/07/15.
 */
public class CsvToAddressRoute extends RouteBuilder {

    @Autowired
    private RemoveFirstColumnTransformer removeFirstColumnTransformer;

    @Autowired
    private BindyToAddressTransformer bindyToAddressTransformer;

    @Override
    public void configure() throws Exception {

        onException(IllegalArgumentException.class)
                .id("csvToAddressException")
                .log(LoggingLevel.WARN, "Format incorrect pour la ligne [${body}]")
                .handled(true)
                .useOriginalMessage()
                .to("direct:unknownOutput");

        from("direct:csvToAddress")
                .id("csvToAddress")
                .transform(method(removeFirstColumnTransformer))
                .to(LogPatterns.logAllDebugForRoute(this.getClass().getName()))
                //cohérence vérifiée par le mapping bindy
                .unmarshal().bindy(BindyType.Csv, "com.zenika.model.address")
                .transform(method(bindyToAddressTransformer, "bindyToAddress"))
                .to(LogPatterns.logAllDebugForRoute(this.getClass().getName()))
                .to("direct:esAddressOutput");
    }
}
