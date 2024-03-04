package com.bimbo.app.service.loyalty;

import com.bimbo.app.dao.request.loyalty.RedeemRequest;
import com.bimbo.app.dao.response.loyalty.RedeemResponse;
import com.bimbo.app.dao.response.loyalty.RedeemRewardResponse;

import java.util.List;

public interface RedeemService {
    RedeemResponse createRedeem(List<RedeemRequest> redeemRequest);

    List<RedeemRewardResponse> getAllRedeemByUser();
}
