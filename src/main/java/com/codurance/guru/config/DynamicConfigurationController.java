package com.codurance.guru.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.Registration;

import static org.springframework.http.ResponseEntity.ok;

@Controller
public class DynamicConfigurationController {

    @Autowired
    private DynamicConfigurationRepository dynamicConfigurationRepository;

    @GetMapping("/config")
    public ResponseEntity<DynamicConfiguration> get() {
        return ok(dynamicConfigurationRepository.findTopByOrderByIdDesc());
    }

}
