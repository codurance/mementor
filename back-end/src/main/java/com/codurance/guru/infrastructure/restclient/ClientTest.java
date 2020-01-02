package com.codurance.guru.infrastructure.restclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "slackTestCall", url = "https://slack.com/api/")
public interface ClientTest {

    @RequestMapping(method = RequestMethod.POST, value = "/api.test")
    String testSlackApi();

}
