package com.codurance.guru.infra.web.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Value("${should.filter.requests:true}")
    private boolean shouldFilterRequests;

    @Autowired
    private TokenAuthenticator tokenAuthenticator;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (shouldFilterRequests){
            registry.addInterceptor(tokenAuthenticator)
            .addPathPatterns("/**")
            .excludePathPatterns("/","/index*", "/static/**", "/*.js", "/*.json", "/*.ico");
        }
    }
}
