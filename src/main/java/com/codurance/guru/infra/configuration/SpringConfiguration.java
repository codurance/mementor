package com.codurance.guru.infra.configuration;

import com.codurance.guru.core.craftspeople.CraftspeopleRepository;
import com.codurance.guru.core.craftspeople.CraftspeopleService;
import com.codurance.guru.core.craftspeople.CraftspeopleValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {
    @Bean
    public CraftspeopleService craftspeopleService(CraftspeopleRepository repository, CraftspeopleValidator validator) {
        return new CraftspeopleService(repository, validator);
    }

    @Bean
    public CraftspeopleValidator craftspeopleValidator(CraftspeopleRepository repository) {
        return new CraftspeopleValidator(repository);
    }
}
