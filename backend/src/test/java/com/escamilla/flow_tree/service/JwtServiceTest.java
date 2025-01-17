package com.escamilla.flow_tree.service;

import com.escamilla.flow_tree.model.Role;
import com.escamilla.flow_tree.model.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


class JwtServiceTest {

    JwtService jwtService;
    public static final String EMAIL = "juan@escamilla.com";
    public static final String SECRET_KEY = "dc8481d581c30d907fb06f3d9fca2344ab9a62d8a8d71eee6e3d4154537fb3a4";
    @BeforeEach
    public void before() {
        jwtService = new JwtService();
        jwtService.setSECRET_KEY(SECRET_KEY);
    }

    @Test
    void extractUserEmailSuccess() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        var secret = Keys.hmacShaKeyFor(keyBytes);
        String jwt = Jwts.builder()
                .subject(EMAIL)
                .claims(new HashMap<>())
                .signWith(secret, Jwts.SIG.HS256)
                .issuedAt(new Date(System.currentTimeMillis()))
                .compact();
        assertEquals(EMAIL, jwtService.extractUserEmail(jwt));
    }

    @Test
    void extractUserEmailWrong() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        var secret = Keys.hmacShaKeyFor(keyBytes);
        String jwt = Jwts.builder()
                .subject("escamilla@juan.com")
                .claims(new HashMap<>())
                .signWith(secret, Jwts.SIG.HS256)
                .issuedAt(new Date(System.currentTimeMillis()))
                .compact();
        assertNotEquals(EMAIL, jwtService.extractUserEmail(jwt));
    }

    @Test
    void extractUserEmailInvalid() {
        assertNull(jwtService.extractUserEmail(null));
    }

    @Test
    void generateToken() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        var secret = Keys.hmacShaKeyFor(keyBytes);
        String jwt = Jwts.builder()
                .subject(EMAIL)
                .claims(new HashMap<>())
                .signWith(secret, Jwts.SIG.HS256)
                .issuedAt(new Date(System.currentTimeMillis()))
                .compact();
        var user = User.builder()
                .firstname("Juan")
                .lastname("Escamilla")
                .email(EMAIL)
                .password("123456")
                .role(Role.USER)
                .build();
        assertEquals(jwt, jwtService.generateToken(user));
    }

    @Test
    void isTokenValid() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        var secret = Keys.hmacShaKeyFor(keyBytes);
        String jwt = Jwts.builder()
                .subject(EMAIL)
                .claims(new HashMap<>())
                .signWith(secret, Jwts.SIG.HS256)
                .issuedAt(new Date(System.currentTimeMillis()))
                .compact();
        var user = User.builder()
                .firstname("Juan")
                .lastname("Escamilla")
                .email(EMAIL)
                .password("123456")
                .role(Role.USER)
                .build();
        assertTrue(jwtService.isTokenValid(jwt, user));
    }
}