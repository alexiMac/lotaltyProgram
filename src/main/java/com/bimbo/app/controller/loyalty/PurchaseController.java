package com.bimbo.app.controller.loyalty;

import com.bimbo.app.dao.request.loyalty.PurchaseRequest;
import com.bimbo.app.dao.response.loyalty.PurchaseByUserResponse;
import com.bimbo.app.dao.response.loyalty.PurchaseResponse;
import com.bimbo.app.service.loyalty.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseController.class);
    private final PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<PurchaseResponse> processPurchase(@RequestBody List<PurchaseRequest> purchaseRequest) {
        logger.info("Start Purchase Process: "+purchaseRequest);
        PurchaseResponse purchaseResponse = purchaseService.createPurchase(purchaseRequest);
        logger.info("Finish Purchase Process");
        return new ResponseEntity<>(purchaseResponse, HttpStatus.CREATED);

       /* try {
            purchaseService.createPurchase(purchaseAmount);
        } catch (SQLException s) {
            logger.error(s.getErrorCode()+" "+s.getMessage()+" "+s.getCause());
            s.getStackTrace();
            s.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        } catch (Exception e) {
            logger.error(e.getMessage()+" "+e.getCause());
            e.getStackTrace();
            e.printStackTrace();
        */

            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        //}
        //return ResponseEntity.status(HttpStatus.CREATED).body("Purchase processed successfully");
    }

    @GetMapping
    public List<PurchaseByUserResponse> getAllRewards() {
        return purchaseService.getAllPurchaseByUser();
    }

}
