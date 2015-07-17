package com.zenika.routes.out;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by armel on 16/07/15.
 */
@Component
public class JsonAggregatorStrategy implements AggregationStrategy{

    public Object aggregate(Object oldBody, Object newBody) {
        List<Object> users = (List<Object>) oldBody;
        if(users == null){
            users = new ArrayList<>();
        }
        users.add(newBody);
        return users;
    }

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        Exchange res = oldExchange;
        if(res == null){
            res = newExchange;
            List<Object> list = new ArrayList<>();
            list.add(newExchange.getIn().getBody());
            newExchange.getIn().setBody(list);
        }else{
            ((List) res.getIn().getBody()).add(newExchange.getIn().getBody());
        }
        return res;

    }
}
