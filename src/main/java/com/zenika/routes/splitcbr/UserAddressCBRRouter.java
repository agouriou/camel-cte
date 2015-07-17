package com.zenika.routes.splitcbr;

import org.springframework.stereotype.Component;

/**
 * Created by armel on 15/07/15.
 */
@Component
public class UserAddressCBRRouter {

    public boolean isUser(String body) {
        return body.startsWith("user");
    }

    public boolean isAddress(String body) {
        return body.startsWith("address");
    }
}
