package com.bimbo.app.repository;

import com.bimbo.app.entities.RedeemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RedeemRepository extends JpaRepository<RedeemEntity, Integer> {
    List<RedeemEntity> findRedeemEntityByUser_Id(int userId);
}
