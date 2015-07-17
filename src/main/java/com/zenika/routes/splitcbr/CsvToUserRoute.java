package com.zenika.routes.splitcbr;

import com.zenika.model.user.BindyToUserTransformer;
import com.zenika.routes.LogPatterns;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BindyType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by armel on 16/07/15.
 */
public class CsvToUserRoute extends RouteBuilder {

    @Autowired
    private RemoveFirstColumnTransformer removeFirstColumnTransformer;

    @Autowired
    private BindyToUserTransformer bindyToUserTransformer;

    @Override
    public void configure() throws Exception {

        onException(IllegalArgumentException.class)
                .id("csvToUserException")
                .log(LoggingLevel.WARN, "Format incorrect pour la ligne [${body}]")
                .handled(true)
                .useOriginalMessage()
                .to("direct:unknownOutput");

        from("direct:csvToUser")
                .id("csvToUser")
                .transform(method(removeFirstColumnTransformer))
                .to(LogPatterns.logAllDebugForRoute(this.getClass().getName()))
                //cohérence vérifiée par le mapping bindy
                .unmarshal().bindy(BindyType.Csv, "com.zenika.model.user")
                .transform(method(bindyToUserTransformer, "bindyToUser"))
                .to(LogPatterns.logAllDebugForRoute(this.getClass().getName()))
                .to("direct:esUsersOutput");
    }
}
