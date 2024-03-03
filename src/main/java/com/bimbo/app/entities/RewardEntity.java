package com.bimbo.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_rewards", schema = "loyaltyProgram")
public class RewardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_reward", nullable = false)
    private Integer id;

    @Column(name="v_reward_name", nullable = false)
    private String rewardName;

    @Column(name="i_reward_points", nullable = false)
    private Integer rewardPoints;

    @Column(name="i_rewards_available", nullable = false)
    private Integer rewardsAvailable;
}
