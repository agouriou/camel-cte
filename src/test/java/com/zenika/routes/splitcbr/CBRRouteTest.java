package com.zenika.routes.splitcbr;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.MockEndpointsAndSkip;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;


/**
 * Created by armel on 15/07/15.
 */
@ContextConfiguration(locations = "/applicationContext.xml")
@RunWith(CamelSpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@MockEndpointsAndSkip("direct:csvToUser|direct:csvToAddress|direct:unknownOutput")
public class CBRRouteTest {

    @Produce(uri = "direct:cbrRoute")
    private ProducerTemplate csvInput;

    @EndpointInject(uri = "mock:direct:csvToUser")
    private MockEndpoint csvToUser;

    @EndpointInject(uri = "mock:direct:csvToAddress")
    private MockEndpoint csvToAddresss;

    @EndpointInject(uri = "mock:direct:unknownOutput")
    private MockEndpoint unknownOutput;

    @Test
    public void should_send_to_user() throws InterruptedException {
        csvToUser.expectedMessageCount(1);

        csvInput.sendBody("user,aaaa,eeee");

        csvToUser.assertIsSatisfied();
    }


    @Test
    public void should_send_to_address() throws InterruptedException {
        csvToAddresss.expectedMessageCount(1);

        csvInput.sendBody("address,aaaa,eeee");

        csvToAddresss.assertIsSatisfied();
    }

    @Test
    public void should_send_to_unknown() throws InterruptedException {
        unknownOutput.expectedMessageCount(1);

        csvInput.sendBody("azeaeaz,aaaa,eeee");

        unknownOutput.assertIsSatisfied();
    }
}
