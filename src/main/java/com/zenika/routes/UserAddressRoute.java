package com.zenika.routes;

import org.apache.camel.builder.RouteBuilder;

/**
 * Created by armel on 15/07/15.
 */
public class UserAddressRoute extends RouteBuilder{


    @Override
    public void configure() throws Exception {
//        from("direct:inputCsv")
//                .dynamicRouter(method("userAddressDynamicRouter"));
    }
}
