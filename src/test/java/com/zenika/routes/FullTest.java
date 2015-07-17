package com.zenika.routes;

import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

/**
 * Created by armel on 16/07/15.
 */
@ContextConfiguration(locations = "/integration/applicationContext.xml")
@RunWith(CamelSpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class FullTest {

    @Test
    public void should_send_to_user_es() throws InterruptedException {


    }
}
