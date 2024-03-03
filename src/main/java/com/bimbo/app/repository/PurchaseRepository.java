package com.bimbo.app.repository;

import com.bimbo.app.entities.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Integer> {
    // Add custom methods sql if necessary
    List<PurchaseEntity> findPurchaseEntityByUser_Id(int userId);
}
