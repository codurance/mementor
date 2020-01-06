package com.codurance.guru.slack;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "SlackApi", url = "https://slack.com/api/")
public interface SlackApiClient {

    @PostMapping("/api.test")
    String apiTest();

}
