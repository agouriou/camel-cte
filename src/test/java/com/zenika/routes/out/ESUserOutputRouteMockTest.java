package com.zenika.routes.out;

import com.zenika.model.user.User;
import org.apache.camel.*;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

/**
 * Created by armel on 16/07/15.
 */
@ContextConfiguration(locations = "/applicationContext.xml")
@RunWith(CamelSpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@MockEndpointsAndSkip("direct:elasticsearchUser|direct:technicalErrorOutput")
public class ESUserOutputRouteMockTest {

    @Produce(uri = "direct:esUsersOutput")
    private ProducerTemplate esUserOutput;

    @EndpointInject(uri = "mock:direct:elasticsearchUser")
    private MockEndpoint elasticsearchUser;

    @EndpointInject(uri = "mock:direct:technicalErrorOutput")
    private MockEndpoint technicalErrorOutput;

    @Test
    public void should_aggregate_2_users() throws InterruptedException {
        elasticsearchUser.expectedMessageCount(1);
        elasticsearchUser.expectedMessagesMatches(new Predicate() {
            @Override
            public boolean matches(Exchange exchange) {
                List<Object> list = (List<Object>) exchange.getIn().getBody();
                return list.size() == 2;
            }
        });

        esUserOutput.sendBody(new User());
        esUserOutput.sendBody(new User());

        elasticsearchUser.assertIsSatisfied();
    }

    @Test
    public void should_treat_technical_exception() throws InterruptedException {
        elasticsearchUser.whenAnyExchangeReceived((exchange) -> {
            throw new NoNodeAvailableException("no node");
        });
        technicalErrorOutput.expectedMessageCount(2);

        esUserOutput.sendBody(new User());
        esUserOutput.sendBody(new User());

        technicalErrorOutput.assertIsSatisfied();
    }
}
