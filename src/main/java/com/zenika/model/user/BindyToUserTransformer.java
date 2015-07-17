package com.zenika.model.user;

import org.apache.camel.Body;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by armel on 16/07/15.
 */
@Component
public class BindyToUserTransformer {

    public User bindyToUser(@Body List<Map<String, Object>> bindyData){
        return (User) bindyData.get(0).get(User.class.getName());
    }

    public List<User> bindyToUsers(@Body List<Map<String, Object>> bindyData){
        return bindyData.stream()
                .map(map -> (User) map.get(User.class.getName()))
                .filter(user -> user != null)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
