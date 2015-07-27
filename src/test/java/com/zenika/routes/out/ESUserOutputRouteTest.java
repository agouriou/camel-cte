package com.zenika.routes.out;

import com.zenika.model.user.User;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.indices.IndexAlreadyExistsException;
import org.junit.Assert;
import org.junit.BeforeClass;
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

    @Produce(uri = "elasticsearch://elasticsearch?ip=127.0.0.1&operation=GET_BY_ID&indexName=user&indexType=user")
    private ProducerTemplate esQueryUser;

//    @BeforeClass
//    public static void setup(){
//        Node node = NodeBuilder.nodeBuilder().client(true).clusterName("elasticsearch")
//                .node();
//        Client client = node.client();
//        try {
//            client.admin().indices().create(Requests.createIndexRequest("user")).actionGet();
//        }catch (IndexAlreadyExistsException e){
//            //ok, index already created
//            System.out.println("############################################# EXISTING");
//        }
//    }

    @BeforeClass
    public static void setup(){
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "elasticsearch").build();
        Client client = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
        try {
            client.admin().indices().create(Requests.createIndexRequest("user")).actionGet();
        }catch (IndexAlreadyExistsException e){
            //ok, index already created
            System.out.println("############################################# EXISTING");
        }
    }

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
