package com.bimbo.app.dao.response.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseByUserResponse {

    @JsonProperty("# de Compra")
    private Integer id;

    @JsonProperty("Purchase Amount")
    private BigDecimal purchaseTotal;

    @JsonProperty("Purchase points generated")
    private Integer generatedPoints;

    @JsonProperty("Purchase date")
    private Date purchasedDate;
}
