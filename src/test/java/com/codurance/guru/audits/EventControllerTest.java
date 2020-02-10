package com.codurance.guru.audits;

import com.codurance.guru.GuruApplication;
import com.codurance.guru.craftspeople.Craftsperson;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = GuruApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class EventControllerTest {

    @Autowired EventRepository repository;

    @Autowired private EventService eventService;

    @Before
    public void setUp() {
        eventService = new EventService(repository);
    }

    @Test
    public void retrieve_all_event() {

        List<Event> events = new ArrayList<>();

        events.add(new Event("Blamo","Message 0"));
        events.add(new Event("Shlami","Message 1"));
        events.add(new Event("Grumbo","Message 2"));
        events.add(new Event("Plumbus","Message 3"));
        events.add(new Event("Dinglebop","Message 4"));

        for (Event event :
                events) {
            eventService.addEvent(event);
        }

        RestAssured.given().get("events")
            .then().assertThat()
                .body("$", hasSize(events.size()));

    }

}
