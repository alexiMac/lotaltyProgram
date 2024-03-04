package com.bimbo.app.service.loyalty.impl;

import com.bimbo.app.dao.request.loyalty.RedeemRequest;
import com.bimbo.app.dao.response.loyalty.RedeemResponse;
import com.bimbo.app.dao.response.loyalty.RedeemRewardResponse;
import com.bimbo.app.dao.response.loyalty.RewardResponse;
import com.bimbo.app.entities.RedeemEntity;
import com.bimbo.app.entities.RewardEntity;
import com.bimbo.app.entities.UserEntity;
import com.bimbo.app.exceptions.ServiceException;
import com.bimbo.app.repository.RedeemRepository;
import com.bimbo.app.repository.RewardRepository;
import com.bimbo.app.repository.UserRepository;
import com.bimbo.app.service.loyalty.PointsService;
import com.bimbo.app.service.loyalty.RedeemService;
import com.bimbo.app.service.loyalty.RewardService;
import com.bimbo.app.service.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final RewardRepository rewardRepository;
    private final RedeemRepository redeemRepository;
    private final UserRepository userRepository;
    @Override
    public RedeemResponse createRedeem(List<RedeemRequest> redeemRequest) {
        logger.info("RedeemRequestImpl Request: "+redeemRequest.toString());
        // Response Object
        RedeemResponse redeemResponse = new RedeemResponse();

        // Variable declaration
        List<RedeemRequest> lstRedeemRequest;
        Map<Integer, RewardResponse> rewardMap;
        int totalPointsAvailable;
        String validationMessage;

        // Get only unique redeemRequest
        lstRedeemRequest = getUniqueRedeemRequest(redeemRequest);
        logger.info("Unique redeemRequest: "+lstRedeemRequest.toString());

        // Get Available Points to change for Rewards per User
        totalPointsAvailable = pointsService.getTotalPointsForUser(authenticationService.getUserContext().getId());
        logger.info("Total Points Available: "+totalPointsAvailable);

        // Get a map of the Rewards available
        rewardMap = generateRewardMap();
        logger.info("RewardMap: "+rewardMap.toString());

        // Constraints
        validationMessage = rewardMap.isEmpty() ? "Rewards Unavailable" : validateRedeemRequests(rewardMap, redeemRequest, totalPointsAvailable);

        // Jpa to insert data and get Response
        if (validationMessage == null) {
            System.out.println("Redemption constraints are valid");

            // Create Redeem
            redeemResponse = createRedeemData(lstRedeemRequest, rewardMap);

            validationMessage = "Redemption was processed successfully";
        }

        redeemResponse.setMessage(validationMessage);
        redeemResponse.setTotalPointsAvailable(pointsService.getTotalPointsForUser(authenticationService.getUserContext().getId()));

        return redeemResponse;

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
                return "The RewardID: " + request.idReward + " was not Found.";
            }

            // Check if the ordered quantity is less than or equal to the available quantity
            if (request.quantityRewards > reward.getAvailable()) {
                return "There are no available amounts of the requested rewardId: " + request.idReward + " - "+reward.getName();
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

    /* Method: Create data in db and generate response */
    public RedeemResponse createRedeemData(List<RedeemRequest> lstRedeemRequest, Map<Integer, RewardResponse> rewardMap) {
        // Variable declaration
        RedeemResponse redeemResponse = new RedeemResponse();
        List<RedeemRewardResponse> lstRedeemRewardResponse = new ArrayList<>();
        int redeemRewardPoints = 0;
        int totalRedeemRewardPointsPerRedeem = 0;

        // Get User entity
        UserEntity userEntity = userRepository.getReferenceById(authenticationService.getUserContext().getId());
        logger.info("User entity: "+userEntity);

        for (RedeemRequest eachRedeem: lstRedeemRequest) {
            logger.info("Redeem processing: "+eachRedeem);
            RewardResponse reward = rewardMap.get(eachRedeem.getIdReward());
            logger.info("Reward getMap: "+reward);

            // Get Reward entity
            RewardEntity rewardEntity =  rewardRepository.getReferenceById(reward.getId());
            logger.info("Reward entity: "+rewardEntity);

            totalRedeemRewardPointsPerRedeem = reward.getPoints()*eachRedeem.quantityRewards;
            redeemRewardPoints = redeemRewardPoints + (totalRedeemRewardPointsPerRedeem);

            // Create Data in DB
            RedeemEntity redeemEntity = RedeemEntity.builder()
                    .user(userEntity)
                    .reward(rewardEntity)
                    .rewardName(reward.getName())
                    .redeemPoints(reward.getPoints())
                    .quantityReward(eachRedeem.quantityRewards)
                    .totalRedeemPoints(reward.getPoints()*eachRedeem.quantityRewards).build();

            redeemRepository.save(redeemEntity);

            // Decrease redeemed points
            pointsService.setRedeemPoints(userEntity, reward.getPoints()*eachRedeem.quantityRewards);

            // Decrease quantity on Rewards
            rewardService.decreaseQuantityReward(reward.getId(), eachRedeem.quantityRewards);

            // Generate Response
            RedeemRewardResponse redeemRewardResponse = RedeemRewardResponse.builder()
                    .id(reward.getId())
                    .name(reward.getName())
                    .quantity(eachRedeem.getQuantityRewards())
                    .points(reward.getPoints())
                    .totalPointsRedeem(totalRedeemRewardPointsPerRedeem)
                    .build();

            lstRedeemRewardResponse.add(redeemRewardResponse);
        }

        redeemResponse.setLstRedeemRewards(lstRedeemRewardResponse);
        redeemResponse.setTotalPointsRedeem(redeemRewardPoints);

        return redeemResponse;
    }
    @Override
    public List<RedeemRewardResponse> getAllRedeemByUser() {
        // Get
        UserEntity userEntity = userRepository.getReferenceById(authenticationService.getUserContext().getId());

        try {
            List<RedeemEntity> lstRedeem = redeemRepository.findRedeemEntityByUser_Id(userEntity.getId());

            return lstRedeem.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Error retrieving the rewards", e);
        }

    }

    private RedeemRewardResponse convertToDto(RedeemEntity redeemEntity) {

        RedeemRewardResponse redeem = new RedeemRewardResponse();
        redeem.setId(redeemEntity.getId());
        redeem.setName(redeemEntity.getRewardName());
        redeem.setPoints(redeemEntity.getRedeemPoints());
        redeem.setQuantity(redeemEntity.getQuantityReward());
        redeem.setTotalPointsRedeem(redeemEntity.getTotalRedeemPoints());

        return redeem;
    }

}
