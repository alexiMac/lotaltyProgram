package com.bimbo.app.controller.security;

import com.bimbo.app.service.security.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/loyalty")
@RequiredArgsConstructor
public class LoyaltyAuthorizationController {
    private final AuthenticationService authenticationService;
    private static final Logger logger = LoggerFactory.getLogger(LoyaltyAuthorizationController.class);

    @GetMapping
    public ResponseEntity<String> sayHello() {
            return ResponseEntity.ok("Hi you are in the loyalty Bimbo api: "
                    + authenticationService.getUserContext().getUsername()
                    + " userId: " + authenticationService.getUserContext().getId());
    }
}
