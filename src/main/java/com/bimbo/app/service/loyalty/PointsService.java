package com.bimbo.app.service.loyalty;

import com.bimbo.app.entities.UserEntity;

public interface PointsService {
    int getTotalPointsForUser(int userId);

    void setPurchasePoints(UserEntity user, int points);

    void setRedeemPoints(UserEntity user, int points);
}
