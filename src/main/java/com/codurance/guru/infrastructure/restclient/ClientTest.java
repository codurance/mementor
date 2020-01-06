package com.codurance.guru.infrastructure.restclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "SlackApi", url = "https://slack.com/api/")
public interface ClientTest {

    @PostMapping("/api.test")
    String testSlackApi();

}
