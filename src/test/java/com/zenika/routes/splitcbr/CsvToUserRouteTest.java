package com.zenika.routes.splitcbr;

import com.zenika.model.user.User;
import org.apache.camel.*;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

/**
 * Created by armel on 16/07/15.
 */
@ContextConfiguration(locations = "/applicationContext.xml")
@RunWith(CamelSpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@MockEndpointsAndSkip("direct:esUsersOutput|direct:unknownOutput")
public class CsvToUserRouteTest {

    @Produce(uri = "direct:csvToUser")
    private ProducerTemplate inputCsv;

    @EndpointInject(uri = "mock:direct:esUsersOutput")
    private MockEndpoint esUsersOutput;

    @EndpointInject(uri = "mock:direct:unknownOutput")
    private MockEndpoint unknownOutput;

    @Test
    public void should_transform_to_user() throws InterruptedException {
        esUsersOutput.expectedMessagesMatches(new UserPredicate("Jean", 34));

        inputCsv.sendBody("user,Jean,34");

        esUsersOutput.assertIsSatisfied();
    }

    @Test
    public void should_treat_exception() throws InterruptedException {
        esUsersOutput.expectedMessageCount(0);
        unknownOutput.expectedMessageCount(1);

        inputCsv.sendBody("user,Jean,Zaza");

        esUsersOutput.assertIsSatisfied();
        unknownOutput.assertIsSatisfied();
    }


    private static class UserPredicate implements Predicate{

        private String name;
        private int age;

        public UserPredicate(String name, int age){
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean matches(Exchange exchange) {
            User user = (User) exchange.getIn().getBody();
            return name.equals(user.getName()) && age == user.getAge();
        }
    }

}
