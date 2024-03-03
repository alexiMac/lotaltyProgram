package com.bimbo.app.controller.loyalty;

import com.bimbo.app.dao.request.loyalty.RedeemRequest;
import com.bimbo.app.service.loyalty.RedeemService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/redeem")
@RequiredArgsConstructor
public class RedeemController {

    private static final Logger logger = LoggerFactory.getLogger(RedeemController.class);
    private final RedeemService redeemService;
    @PostMapping
    public void processRedeem(@RequestBody List<RedeemRequest> redeemRequest) {
        redeemService.createRedeem(redeemRequest);
    }

}
