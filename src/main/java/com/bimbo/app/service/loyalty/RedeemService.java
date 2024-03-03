package com.bimbo.app.service.loyalty;

import com.bimbo.app.dao.request.loyalty.RedeemRequest;

import java.util.List;

public interface RedeemService {
    void createRedeem(List<RedeemRequest> redeemRequest);

}
