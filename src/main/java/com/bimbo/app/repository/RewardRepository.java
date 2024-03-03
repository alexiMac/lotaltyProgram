package com.bimbo.app.repository;

import com.bimbo.app.entities.RewardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardRepository extends JpaRepository<RewardEntity, Integer> {
    // Add custom methods sql if necessary
}
