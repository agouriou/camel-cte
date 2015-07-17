package com.zenika.routes;

import org.springframework.stereotype.Component;

/**
 * Created by armel on 15/07/15.
 */
@Component
public class UserAddressDynamicRouter {

    private STATE state;

    public String route(String body){
        return null;
    }

    private static enum STATE{
        USER,
        ADDRESS;
    }
}
