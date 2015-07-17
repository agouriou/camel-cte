package com.zenika.routes.splitcbr;

import org.apache.camel.Body;
import org.springframework.stereotype.Component;

/**
 * Created by armel on 16/07/15.
 */
@Component
public class RemoveFirstColumnTransformer {

    public String removeFirstColumn(@Body String body){
        return body.substring(body.indexOf(",") + 1);
    }
}
