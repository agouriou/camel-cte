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

import java.util.Arrays;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

/**
 * Created by armel on 16/07/15.
 */
@ContextConfiguration(locations = "/applicationContext.xml")
@RunWith(CamelSpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@MockEndpointsAndSkip("direct:cbrRoute")
public class SplitLineRouteTest {

    @Produce(uri = "direct:splitLine")
    private ProducerTemplate inputCsv;

    @EndpointInject(uri = "mock:direct:cbrRoute")
    private MockEndpoint cbrRoute;


    @Test
    public void should_send_1_message() throws InterruptedException {
        cbrRoute.expectedBodiesReceived(Arrays.asList("aa"));

        inputCsv.sendBody("aa");

        cbrRoute.assertIsSatisfied();
    }


    @Test
    public void should_send_3_message() throws InterruptedException {
        cbrRoute.expectedBodiesReceived(Arrays.asList("aa", "bb", "cc"));

        inputCsv.sendBody("aa\nbb\ncc");

        cbrRoute.assertIsSatisfied();
    }


    @Test
    public void should_send_0_message() throws InterruptedException {
        cbrRoute.expectedMessageCount(0);

        inputCsv.sendBody("");

        cbrRoute.assertIsSatisfied();
    }

//    @Test
//    public void should_send_3_message() throws InterruptedException{
//        //TODO: test real file
//
//    }


}
