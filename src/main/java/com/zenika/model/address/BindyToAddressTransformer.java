package com.zenika.model.address;

import org.apache.camel.Body;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by armel on 16/07/15.
 */
@Component
public class BindyToAddressTransformer {

    public Address bindyToAddress(@Body List<Map<String, Object>> bindyData){
        return (Address) bindyData.get(0).get(Address.class.getName());
    }
}
