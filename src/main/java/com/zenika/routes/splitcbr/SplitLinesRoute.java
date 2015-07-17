package com.zenika.routes.splitcbr;

import org.apache.camel.builder.RouteBuilder;

/**
 * Created by armel on 16/07/15.
 */
public class SplitLinesRoute extends RouteBuilder{

    @Override
    public void configure() throws Exception {

        from("direct:inputCsv")
            .to("direct:splitLine");

        from("direct:splitLine")
                .id("splitLine")
                .split(body().tokenize("\n"))
                .streaming()
                .to("direct:cbrRoute");

        //mapping des données?
        //TODO: valoriser addresse es
        //TODO: version dynamic router?
        //TODO: lien user -> address?


        /*
            CAS 1 : tous les user au début, une ligne "ADDRESS", puis les address
              --> dynamic router

            CAS 2 : user et address mélés
              --> splitter + cbr
         */

    }
}
