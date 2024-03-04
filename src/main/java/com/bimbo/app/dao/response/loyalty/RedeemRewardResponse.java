package com.bimbo.app.dao.response.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedeemRewardResponse {
    @JsonProperty("Reward ID")
    private int id;

    @JsonProperty("Reward Description")
    private String name;

    @JsonProperty("Reward Quantity")
    private int quantity;

    @JsonProperty("Points needed to redeem")
    private int points;

    @JsonProperty("Reward total points redeem")
    private int totalPointsRedeem;
}