package com.escamilla.flow_tree.service;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Map;
import java.util.function.Function;

public interface IJwtService {
    String extractUserEmail(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimsTFunction);
    Claims extractAllClaims(String token);
    SecretKey getSigningKey();
    String generateToken(UserDetails userDetails);
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);
    String buildToken(Map<String, Object> extraClaims, UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
    boolean isTokenExpired(String token);
}
