package com.bimbo.app.dao.request.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedeemRequest {
    @JsonProperty("Id Reward")
    public Integer idReward;

    @JsonProperty("Quatity Rewards")
    public Integer quantityRewards;

}
