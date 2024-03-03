package com.bimbo.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_redeem", schema = "loyaltyProgram")
public class RedeemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name="pk_id_redeem", nullable = false)
    private Integer id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_user", nullable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_reward", nullable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RewardEntity reward;

    @Column(name="v_reward_name")
    private String rewardName;

    @Column(name="i_redeem_points", nullable = false)
    private Integer redeemPoints;

    @Column(name="d_redeem_date_date", columnDefinition="timestamp default current_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date redeemedDate;

}
