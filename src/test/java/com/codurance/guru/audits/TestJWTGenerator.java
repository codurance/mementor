package com.codurance.guru.audits;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

// This worked first time
public class TestJWTGenerator {

    private static String jwt;

    public static String createToken(String username) {
        jwt = Jwts.builder()
                .setSubject(username)
                .setExpiration(Date.from(Instant.now().plus(Duration.ofHours(1L))))
                .signWith(SignatureAlgorithm.HS512, "SECRET")
                .compact();

        return jwt;
    }

}
