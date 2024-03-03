package com.bimbo.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_purchases", schema = "loyaltyProgram")
public class PurchaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pk_id_purchase", nullable = false)
    private Integer id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_user", nullable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;

    @Column(name="de_purchase_total", nullable = false, columnDefinition="Decimal(15,2)")
    private BigDecimal purchaseTotal;

    @Column(name="i_generated_points", nullable = false)
    private Integer generatedPoints;

    @Column(name="d_purchase_date", columnDefinition="timestamp default current_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date purchasedDate;

}
