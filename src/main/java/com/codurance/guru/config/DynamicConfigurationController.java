package com.codurance.guru.config;

import com.codurance.guru.craftspeople.responses.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.Registration;

import static org.springframework.http.ResponseEntity.*;

@Controller
public class DynamicConfigurationController {

    @Autowired
    private DynamicConfigurationRepository dynamicConfigurationRepository;

    @GetMapping("/config")
    public ResponseEntity<DynamicConfiguration> get() {
        return ok(dynamicConfigurationRepository.findTopByOrderByIdDesc());
    }

    @PutMapping("/config")
    public ResponseEntity<ErrorResponse> update(@RequestBody DynamicConfiguration dynamicConfiguration) {
        if(dynamicConfiguration.getLastMeetingThresholdsInWeeks() == null) {
            return badRequest().body(new ErrorResponse("Last meeting cannot be null"));
        }

        dynamicConfigurationRepository.save(dynamicConfiguration);
        return noContent().build();
    }

}
