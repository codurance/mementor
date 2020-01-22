package com.codurance.guru.craftspeople.middleware;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Component
public class TokenAuthenticator extends HandlerInterceptorAdapter {

    @Value("${google.client.id:clientId}")
    private String CLIENT_ID;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws GeneralSecurityException, IOException {
        String token = request.getHeader("Authorization");
        if ("null".equals(token) || token == null)
            return false;
        return authenticateToken(token);
    }

    private boolean authenticateToken(String token) throws GeneralSecurityException, IOException {
        JacksonFactory jsonFactory = new JacksonFactory();
        HttpTransport transport = new NetHttpTransport();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        GoogleIdToken idToken = verifier.verify(token);
        return idToken != null;
    }
}