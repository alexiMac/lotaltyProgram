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
public class RewardResponse {
    @JsonProperty("Reward ID")
    private int id;

    @JsonProperty("Reward Description")
    private String name;

    @JsonProperty("Points needed to redeem")
    private int points;

    @JsonProperty("#Available rewards")
    private int available;
}
