package com.zenika.routes.out;

import com.zenika.model.user.User;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

/**
 * Created by armel on 16/07/15.
 */
@ContextConfiguration(locations = "/applicationContext-withES.xml")
@RunWith(CamelSpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class ESUserOutputRouteTest {

    @Produce(uri = "direct:esUsersOutput")
    private ProducerTemplate esUserOutput;

    @Produce(uri = "elasticsearch://local?operation=GET_BY_ID&indexName=user&indexType=user")
    private ProducerTemplate esQueryUser;

    @Test
    public void should_index_1_user(){
        User user = new User();
        user.setName("Toto");
        user.setAge(12);

        Object index = esUserOutput.requestBody(user);
        Assert.assertNotNull(index);

        Object userFromEs = esQueryUser.requestBody(index);
        Assert.assertNotNull(userFromEs);
    }
}
