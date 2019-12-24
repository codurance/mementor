package com.guru.guruback.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/helloworld")
    public String getHelloWorld(){
        return "Hello World, wanna baaaaaaaaaang!";
    }
}
