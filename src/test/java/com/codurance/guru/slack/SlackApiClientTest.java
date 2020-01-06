package com.codurance.guru.slack;

import com.codurance.guru.GuruApplication;
import com.codurance.guru.slack.SlackApiClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = GuruApplication.class)
@TestPropertySource(value={"classpath:application.properties"})
@SpringBootTest
public class SlackApiClientTest {

    @Autowired
    private SlackApiClient slackApiClient;

    @Test
    public void client_api_test_call() {
        String expectedJSON = "{\"ok\":true}";
        String response = slackApiClient.apiTest();

        assertEquals(expectedJSON, response);
    }

}
