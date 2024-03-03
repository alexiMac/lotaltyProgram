package com.bimbo.app.service.loyalty.impl;

import com.bimbo.app.dao.request.loyalty.RedeemRequest;
import com.bimbo.app.dao.response.loyalty.RewardResponse;
import com.bimbo.app.service.loyalty.PointsService;
import com.bimbo.app.service.loyalty.RedeemService;
import com.bimbo.app.service.loyalty.RewardService;
import com.bimbo.app.service.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RedeemServiceImpl implements RedeemService {
    private static final Logger logger = LoggerFactory.getLogger(RedeemServiceImpl.class);
    private final RewardService rewardService;
    private final PointsService pointsService;
    private final AuthenticationService authenticationService;
    @Override
    public void createRedeem(List<RedeemRequest> redeemRequest) {
        logger.info("RedeemRequestImpl Request: "+redeemRequest.toString());

        // Variable declaration
        List<RedeemRequest> lstRedeemRequest;
        Map<Integer, RewardResponse> rewardMap;
        int totalPointsAvailable;
        String validationMessage;

        // Get only unique redeemRequest
        lstRedeemRequest = getUniqueRedeemRequest(redeemRequest);
        logger.info("Unique redeemRequest: "+lstRedeemRequest.toString());

        // Get a map of the Rewards available
        rewardMap = generateRewardMap();
        logger.info("RewardMap: "+rewardMap.toString());

        // Get Available Points to change for Rewards per User
        totalPointsAvailable = pointsService.getTotalPointsForUser(authenticationService.getUserContext().getId());
        logger.info("Total Points Available: "+totalPointsAvailable);

        validationMessage = validateRedeemRequests(rewardMap, redeemRequest, totalPointsAvailable);
        if (validationMessage == null) {
            System.out.println("Redemption requests are valid");
            // Continue Logic
        } else {
            System.out.println("Error: " + validationMessage);
        }

    }

    /* Method: If any request RewardID is duplicated makes 1 and sum the quantity*/
    public List<RedeemRequest> getUniqueRedeemRequest(List<RedeemRequest> redeemRequest) {
        Map<Integer, Integer> idQuantityMap = new HashMap<>();

        // Java Stream to process the list
        redeemRequest.forEach(request -> idQuantityMap.merge(request.idReward, request.quantityRewards, Integer::sum));

        // Return the unique list
        return idQuantityMap.entrySet().stream()
                .map(entry -> new RedeemRequest(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /* Method: Generate a map with the Rewards Available */
    public Map<Integer, RewardResponse> generateRewardMap() {
        Map<Integer, RewardResponse> rewardMap = new HashMap<>();
        List<RewardResponse> rewards = rewardService.getAllRewards();

        for (RewardResponse reward : rewards) {
            rewardMap.put(reward.getId(), reward);
        }

        return rewardMap;
    }

    /* Method: Validate Constraints:
        1.- The available points must be greater than the sum of the redeem rewards points
        2.- The RewardId in the redeemRequest must be existed in the Rewards available
        3.- The Redeem request quantity must be available to supply by the Rewards table quantity
     */
    public String validateRedeemRequests(Map<Integer, RewardResponse> rewardMap, List<RedeemRequest> redeemRequests, int totalPointsAvailable) {
        int totalPointsNeeded = 0;

        for (RedeemRequest request : redeemRequests) {
            RewardResponse reward = rewardMap.get(request.idReward);

            // Verify if the RewardIdRequest exists on MapRewards
            if (!rewardMap.containsKey(request.idReward)) {
                return "The RewardID " + request.idReward + " - "+reward.getName() + " was not Found.";
            }

            // Check if the ordered quantity is less than or equal to the available quantity
            if (request.quantityRewards > reward.getAvailable()) {
                return "There are no available amounts of the requested rewardId " + request.idReward + " - "+reward.getName();
            }

            // Calculate the total number of points needed
            totalPointsNeeded += reward.getPoints() * request.quantityRewards;
        }

        // Check if the points needed to not exceed the total points available
        if (totalPointsNeeded > totalPointsAvailable) {
            return "You do not have enough points available to redeem all rewards.";
        }

        // All validations passed
        return null;
    }

}
