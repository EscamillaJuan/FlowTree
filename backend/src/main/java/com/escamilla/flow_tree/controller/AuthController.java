package com.escamilla.flow_tree.controller;

import com.escamilla.flow_tree.payload.AuthenticationRequest;
import com.escamilla.flow_tree.payload.AuthenticationResponse;
import com.escamilla.flow_tree.payload.RegisterRequest;
import com.escamilla.flow_tree.service.IAuthService;
import com.escamilla.flow_tree.service.implementation.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }
}
