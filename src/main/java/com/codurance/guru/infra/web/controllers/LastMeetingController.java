package com.codurance.guru.infra.web.controllers;

import com.codurance.guru.core.configuration.lastmeeting.threshold.LastMeetingThreshold;
import com.codurance.guru.core.configuration.lastmeeting.threshold.LastMeetingThresholdService;
import com.codurance.guru.core.configuration.lastmeeting.threshold.exceptions.LastMeetingThresholdNotGreaterThanZeroException;
import com.codurance.guru.core.craftspeople.CraftspeopleService;
import com.codurance.guru.core.craftspeople.exceptions.InvalidLastMeetingDateException;
import com.codurance.guru.infra.web.requests.UpdateLastMeetingRequest;
import com.codurance.guru.infra.web.responses.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.*;

@Controller
public class LastMeetingController {

    @Autowired
    private CraftspeopleService craftspeopleService;

    @Autowired
    private LastMeetingThresholdService lastMeetingThresholdService;

    @GetMapping("/config")
    public ResponseEntity<LastMeetingThreshold> get() {
        return ok(lastMeetingThresholdService.getCurrentThreshold());
    }

    @PutMapping("/config")
    public ResponseEntity<ErrorResponse> update(@RequestBody LastMeetingThreshold lastMeetingThreshold) {
        if(lastMeetingThreshold.getLastMeetingThresholdsInWeeks() == null) {
            return badRequest().body(new ErrorResponse("Last meeting threshold cannot be null"));
        }

        try {
            lastMeetingThresholdService.updateThreshold(lastMeetingThreshold.getLastMeetingThresholdsInWeeks());
            return noContent().build();
        } catch (LastMeetingThresholdNotGreaterThanZeroException e) {
            return badRequest().body(new ErrorResponse("Last meeting threshold must be greater than 0"));
        }
    }

    @PutMapping("/craftspeople/lastmeeting")
    public ResponseEntity<ErrorResponse> setLastMeeting(@Valid @RequestBody UpdateLastMeetingRequest request) {
        try {
            craftspeopleService.setLastMeeting(request.getCraftspersonId(), request.getLastMeeting());
            return noContent().build();
        } catch (InvalidLastMeetingDateException ex) {
            return badRequest().body(new ErrorResponse("The last meeting date is too far in the future"));
        }
    }

}
