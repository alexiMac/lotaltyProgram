package com.bimbo.app.service.loyalty;

import com.bimbo.app.dao.request.loyalty.PurchaseRequest;
import com.bimbo.app.dao.response.loyalty.PurchaseByUserResponse;
import com.bimbo.app.dao.response.loyalty.PurchaseResponse;

import java.util.List;

public interface PurchaseService {
    PurchaseResponse createPurchase(List<PurchaseRequest> purchaseRequest);

    List<PurchaseByUserResponse> getAllPurchaseByUser();
}
