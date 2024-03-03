package com.bimbo.app.controller.loyalty;

import com.bimbo.app.dao.response.loyalty.RewardResponse;
import com.bimbo.app.service.loyalty.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rewards")
@RequiredArgsConstructor
public class RewardController {
    private final RewardService rewardService;

    @GetMapping
    public List<RewardResponse> getAllRewards() {
        return rewardService.getAllRewards();
    }

    @GetMapping("/{rewardId}")
    public RewardResponse getRewardById(@PathVariable int rewardId) {
        return rewardService.getRewardById(rewardId);
    }
}
