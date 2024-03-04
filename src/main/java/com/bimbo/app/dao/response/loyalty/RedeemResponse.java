package com.bimbo.app.dao.response.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RedeemResponse {
    @JsonProperty("Redeem Message")
    public String message;

    @JsonProperty("Total points redeem")
    public int totalPointsRedeem;

    @JsonProperty("Total Points Available")
    public int totalPointsAvailable;

    List<RedeemRewardResponse> lstRedeemRewards;

}
