package com.zenika.model.user;

import org.apache.camel.Converter;
import org.elasticsearch.common.xcontent.XContentBuilder;

/**
 * Created by armel on 16/07/15.
 */
@Converter
public class UserToESContentConverter {

    @Converter
    public static XContentBuilder toESContent(User user){
        return null;
    }
}
