package com.bimbo.app.dao.response.loyalty;

import com.bimbo.app.dao.request.loyalty.PurchaseRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseResponse {
    @JsonProperty("User ID")
    public int userID;

    @JsonProperty("Total Amount")
    public BigDecimal totalAmount;

    @JsonProperty("Generated Points")
    public int generatedPoints;

    @JsonProperty("Total Points")
    public int totalPoints;

    List<PurchaseRequest> lstPurchases;

}
