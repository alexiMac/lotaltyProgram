package com.bimbo.app.controller.loyalty;

import com.bimbo.app.service.security.AuthenticationService;
import com.bimbo.app.service.loyalty.PointsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/points")
@RequiredArgsConstructor
public class PointController {
    private static final Logger logger = LoggerFactory.getLogger(PointController.class);
    private final PointsService pointsService;
    private final AuthenticationService authenticationService;

    @GetMapping
    public ResponseEntity<String> getPointPerUser() {
        int generatedPoints = pointsService.getTotalPointsForUser(authenticationService.getUserContext().getId());

        return ResponseEntity.status(HttpStatus.CREATED).body("Generated points: "+generatedPoints);
    }

}
