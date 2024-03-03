package com.bimbo.app.service.security.impl;

import com.bimbo.app.dao.request.security.SignInRequest;
import com.bimbo.app.dao.request.security.SignUpRequest;
import com.bimbo.app.dao.response.security.JwtAuthenticationResponse;
import com.bimbo.app.entities.UserEntity;
import com.bimbo.app.enums.Role;
import com.bimbo.app.exceptions.ServiceException;
import com.bimbo.app.repository.UserRepository;
import com.bimbo.app.service.security.AuthenticationService;
import com.bimbo.app.service.security.JwtService;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        String jwt;
        try {
            var user = UserEntity.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                    .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER).build();
            userRepository.save(user);
            jwt = jwtService.generateToken(user);
        } catch (DataIntegrityViolationException e) {
            throw new ServiceException("User already exists.");
        }

        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SignInRequest request) {
        String jwt;
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
            jwt = jwtService.generateToken(user);
        } catch (Exception e) {
            throw new ServiceException("User not found.", e);
        }

        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public int getContextUserId(){
        int userId = 0;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserEntity usuario){
            userId = usuario.getId();
        }
        return userId;
    }

    @Override
    public UserEntity getUserContext() {
        UserEntity userContext = new UserEntity();
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserEntity usuario){
            userContext = usuario;
        }
        return userContext;
    }
}