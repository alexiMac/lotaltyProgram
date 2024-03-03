package com.bimbo.app.dao.request.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequest {
    @JsonProperty("purchase amount")
    public BigDecimal amount;

    @JsonProperty("product description")
    public String descProduct;

    @JsonProperty("quantity")
    public int quantity;

    @JsonProperty("generated points")
    public Integer generatedPoints;

    public BigDecimal getTotalAmount(BigDecimal amount) {
        return this.amount = amount.setScale(2, RoundingMode.DOWN);
    }

    public Integer getPoints(BigDecimal amount) {
        return this.generatedPoints = amount.setScale(0, RoundingMode.FLOOR).divide(BigDecimal.TEN, RoundingMode.FLOOR).intValue();
    }
}
