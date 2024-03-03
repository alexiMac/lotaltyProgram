package com.bimbo.app.service.loyalty.impl;

import com.bimbo.app.dao.request.loyalty.PurchaseRequest;
import com.bimbo.app.dao.response.loyalty.PurchaseByUserResponse;
import com.bimbo.app.dao.response.loyalty.PurchaseResponse;
import com.bimbo.app.dao.response.loyalty.RewardResponse;
import com.bimbo.app.entities.PurchaseEntity;
import com.bimbo.app.entities.RewardEntity;
import com.bimbo.app.entities.UserEntity;
import com.bimbo.app.exceptions.ServiceException;
import com.bimbo.app.repository.PurchaseRepository;
import com.bimbo.app.repository.UserRepository;
import com.bimbo.app.service.security.AuthenticationService;
import com.bimbo.app.service.loyalty.PointsService;
import com.bimbo.app.service.loyalty.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseServiceImpl.class);
    private final PurchaseRepository purchaseRepository;
    private final AuthenticationService authenticationService;
    private final PointsService pointsService;
    private final UserRepository userRepository;
    @Override
    public PurchaseResponse createPurchase(List<PurchaseRequest> purchaseRequest) {
        PurchaseResponse purchaseResponse = new PurchaseResponse();
        List<PurchaseRequest> lstPurchaseResponse = new ArrayList<>();

        int accumulatedPoints = 0;
        int totalPoints = 0;
        BigDecimal accumulatedAmount = new BigDecimal(0);

        // Need to extract from database to ensure the data in entity exists
        UserEntity userEntity = userRepository.getReferenceById(authenticationService.getUserContext().getId());
        logger.info("User entity: "+userEntity);

        if (!purchaseRequest.isEmpty()) {
            for (PurchaseRequest eachPurchase: purchaseRequest) {
                // Save Purchase Entity
                PurchaseEntity purchase = PurchaseEntity.builder()
                        .user(userEntity)
                        .purchaseTotal(eachPurchase.getTotalAmount(eachPurchase.getAmount()))
                        .generatedPoints(eachPurchase.getPoints(eachPurchase.getAmount())).build();

                purchaseRepository.save(purchase);

                lstPurchaseResponse.add(eachPurchase);

                // Save generatedPoints per purchase
                pointsService.setPurchasePoints(userEntity, eachPurchase.getGeneratedPoints());

                // Accumulated Points Purchase
                accumulatedPoints = accumulatedPoints + eachPurchase.getGeneratedPoints();
                // Accumulated Amount Purchase
                accumulatedAmount = accumulatedAmount.add(eachPurchase.getAmount());

            }
        }

        // Get all points per user
        totalPoints = pointsService.getTotalPointsForUser(userEntity.getId());

        purchaseResponse.setUserID(userEntity.getId());
        purchaseResponse.setLstPurchases(lstPurchaseResponse);
        purchaseResponse.setTotalAmount(accumulatedAmount);
        purchaseResponse.setGeneratedPoints(accumulatedPoints);
        purchaseResponse.setTotalPoints(totalPoints);

        return purchaseResponse;
    }

    @Override
    public List<PurchaseByUserResponse> getAllPurchaseByUser() {
        UserEntity userEntity = userRepository.getReferenceById(authenticationService.getUserContext().getId());
        try {
            List<PurchaseEntity> lstPurchases = purchaseRepository.findPurchaseEntityByUser_Id(userEntity.getId());
            return lstPurchases.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Error retrieving the rewards", e);
        }
    }

    private PurchaseByUserResponse convertToDto(PurchaseEntity purchaseEntity) {
        PurchaseByUserResponse purchase = new PurchaseByUserResponse();
        purchase.setId(purchaseEntity.getId());
        purchase.setPurchaseTotal(purchaseEntity.getPurchaseTotal());
        purchase.setGeneratedPoints(purchaseEntity.getGeneratedPoints());
        purchase.setPurchasedDate(purchaseEntity.getPurchasedDate());

        return purchase;
    }

}
