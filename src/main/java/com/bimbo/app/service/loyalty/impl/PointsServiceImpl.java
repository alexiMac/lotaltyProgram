package com.bimbo.app.service.loyalty.impl;

import com.bimbo.app.entities.PointEntity;
import com.bimbo.app.entities.UserEntity;
import com.bimbo.app.repository.PointsRepository;
import com.bimbo.app.service.loyalty.PointsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointsServiceImpl implements PointsService {

    private final PointsRepository pointsRepository;
    private static final Logger logger = LoggerFactory.getLogger(PointsServiceImpl.class);

    @Override
    public int getTotalPointsForUser(int userId) {
        return pointsRepository.getTotalPointsForUser(userId);
    }
    @Override
    public void setPurchasePoints(UserEntity user, int points) {
        PointEntity pointsEntity = pointsRepository.findPointEntityByUser_Id(user.getId());

        if (pointsEntity != null) {
            // Update points
            pointsEntity.setAvailablePoints(pointsEntity.getAvailablePoints()+points);
            logger.info("Update: Add Points purchase: "+pointsEntity);
        } else {
            // Create record per points user
            pointsEntity = PointEntity.builder().availablePoints(points).user(user).build();
            logger.info("Create: Points purchase: "+pointsEntity);
        }

        pointsRepository.save(pointsEntity);

    }
    /* Method - To decrease points used to Redeem - Rewards
    * If we use this method is because you must have points */
    @Override
    public void setRedeemPoints(UserEntity user, int points) {

        PointEntity pointsEntity = pointsRepository.findPointEntityByUser_Id(user.getId());

        pointsEntity.setAvailablePoints(pointsEntity.getAvailablePoints()-points);
        logger.info("Update Points Redeem: "+pointsEntity);

        pointsRepository.save(pointsEntity);
    }

}
