package com.escamilla.flow_tree.service;

import com.escamilla.flow_tree.payload.AuthenticationRequest;
import com.escamilla.flow_tree.payload.AuthenticationResponse;
import com.escamilla.flow_tree.payload.RegisterRequest;

public interface IAuthService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse login(AuthenticationRequest request);
}
