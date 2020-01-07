package com.codurance.guru.slack;

import com.codurance.guru.GuruApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = GuruApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
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
