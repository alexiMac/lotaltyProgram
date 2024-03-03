package com.bimbo.app.service.loyalty;

import com.bimbo.app.dao.response.loyalty.RewardResponse;

import java.util.List;

public interface RewardService {
    List<RewardResponse> getAllRewards();

    RewardResponse getRewardById(int id);
}
