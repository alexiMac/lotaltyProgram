package com.bimbo.app.controller.loyalty;

import com.bimbo.app.dao.request.loyalty.RedeemRequest;
import com.bimbo.app.dao.response.loyalty.RedeemResponse;
import com.bimbo.app.dao.response.loyalty.RedeemRewardResponse;
import com.bimbo.app.service.loyalty.RedeemService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/redeem")
@RequiredArgsConstructor
public class RedeemController {

    private static final Logger logger = LoggerFactory.getLogger(RedeemController.class);
    private final RedeemService redeemService;
    @PostMapping
    public ResponseEntity<RedeemResponse> processRedeem(@RequestBody List<RedeemRequest> redeemRequest) {
        logger.info("Start Redeem Process: "+redeemRequest);
        RedeemResponse redeemResponse = redeemService.createRedeem(redeemRequest);
        logger.info("Finish Redeem Process");
        return new ResponseEntity<>(redeemResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public List<RedeemRewardResponse> getAllRedeemByUser() {
        return redeemService.getAllRedeemByUser();
    }

}
