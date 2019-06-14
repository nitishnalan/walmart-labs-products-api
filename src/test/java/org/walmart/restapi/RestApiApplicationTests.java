package org.walmart.restapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RestApiApplication.class)
@ActiveProfiles("test")
@SpringBootTest
public class RestApiApplicationTests {

    @Test
    public void contextLoads() {

    }

}
