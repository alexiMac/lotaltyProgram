package com.bimbo.app.controller.security;

import com.bimbo.app.dao.request.security.SignInRequest;
import com.bimbo.app.dao.request.security.SignUpRequest;
import com.bimbo.app.dao.response.security.JwtAuthenticationResponse;
import com.bimbo.app.exceptions.ApiResponseException;
import com.bimbo.app.exceptions.ServiceException;
import com.bimbo.app.service.security.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/signup")
    public ResponseEntity<ApiResponseException<JwtAuthenticationResponse>> signup(@RequestBody SignUpRequest request) {
        try {
            JwtAuthenticationResponse response = authenticationService.signup(request);
            return ResponseEntity.ok(new ApiResponseException<>("User and Token created", response));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponseException<>(e.getMessage(), null));
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponseException<JwtAuthenticationResponse>> signin(@RequestBody SignInRequest request) {
        try {
            JwtAuthenticationResponse response = authenticationService.signin(request);
            return ResponseEntity.ok(new ApiResponseException<>("Authentication correct", response));
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponseException<>(e.getMessage(), null));
        }
    }
}
