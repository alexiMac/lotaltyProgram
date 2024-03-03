package com.bimbo.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_points", schema = "loyaltyProgram")
public class PointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name="pk_id_points", nullable = false)
    private Integer id;

    @OneToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_user", nullable = false, updatable = false, referencedColumnName = "pk_id_user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;

    @Column(name="i_available_points", nullable = false)
    private Integer availablePoints;
}
