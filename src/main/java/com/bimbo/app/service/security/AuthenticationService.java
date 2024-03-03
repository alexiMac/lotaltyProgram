package com.bimbo.app.service.security;

import com.bimbo.app.dao.request.security.SignInRequest;
import com.bimbo.app.dao.request.security.SignUpRequest;
import com.bimbo.app.dao.response.security.JwtAuthenticationResponse;
import com.bimbo.app.entities.UserEntity;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SignInRequest request);

    int getContextUserId();

    UserEntity getUserContext();
}
