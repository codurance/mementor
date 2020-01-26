package com.codurance.guru.core.configuration.lastmeeting.threshold;

import com.codurance.guru.core.configuration.lastmeeting.threshold.exceptions.LastMeetingThresholdNotGreaterThanZeroException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.BDDMockito.then;

public class LastMeetingThresholdServiceTest {

    private LastMeetingThresholdService service;

    private LastMeetingThresholdRepository repositoryMock;

    @Before
    public void setUp() {
        repositoryMock = Mockito.mock(LastMeetingThresholdRepository.class);
        service = new LastMeetingThresholdService(repositoryMock);
    }

    @Test(expected = LastMeetingThresholdNotGreaterThanZeroException.class)
    public void negative_value_should_rejected() {
        service.updateThreshold(-1);
    }

    @Test(expected = LastMeetingThresholdNotGreaterThanZeroException.class)
    public void zero_should_rejected() {
        service.updateThreshold(0);
    }

    @Test
    public void any_other_value_will_update_the_repository() {
        service.updateThreshold(1);
        then(repositoryMock).should()
                .updateThreshold(1);
    }
}
