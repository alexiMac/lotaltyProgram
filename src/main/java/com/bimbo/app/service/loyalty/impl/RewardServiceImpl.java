package com.bimbo.app.service.loyalty.impl;

import com.bimbo.app.dao.response.loyalty.RewardResponse;
import com.bimbo.app.entities.RewardEntity;
import com.bimbo.app.exceptions.ServiceException;
import com.bimbo.app.repository.RewardRepository;
import com.bimbo.app.service.loyalty.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {

    private final RewardRepository rewardRepository;

    @Override
    public List<RewardResponse> getAllRewards() {
        try {
            List<RewardEntity> rewards = rewardRepository.findAll();
            return rewards.stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Error retrieving the rewards", e);
        }
    }

    @Override
    public RewardResponse getRewardById(int id) {
        RewardEntity reward = rewardRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Error retriving the reward with the id: " + id));
        return convertToDto(reward);
    }

    private RewardResponse convertToDto(RewardEntity reward) {
        RewardResponse rewardDTO = new RewardResponse();
        rewardDTO.setId(reward.getId());
        rewardDTO.setName(reward.getRewardName());
        rewardDTO.setPoints(reward.getRewardPoints());
        rewardDTO.setAvailable(reward.getRewardsAvailable());
        return rewardDTO;
    }

}
