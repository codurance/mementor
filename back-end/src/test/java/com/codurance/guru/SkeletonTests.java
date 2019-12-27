package com.codurance.guru;

import com.jayway.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = GuruApplication.class)
@TestPropertySource(value={"classpath:application.properties"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SkeletonTests {

    @BeforeEach
    public void setBaseUri () {
        RestAssured.port = 8080;
        RestAssured.baseURI = "http://localhost"; // replace as appropriate
    }

    @Test
    public void getDataTest() {
        get("/api/tdd/responseData")
                .then()
                .assertThat()
                .body("data", equalTo("responseData"));
    }


}
