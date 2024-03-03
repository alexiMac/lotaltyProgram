package com.bimbo.app.repository;

import com.bimbo.app.entities.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PointsRepository extends JpaRepository<PointEntity, Integer> {
    @Query(value = "SELECT COALESCE(SUM(i_generated_points), 0) totalPoints " +
                   "FROM tbl_purchases " +
                   "WHERE fk_id_user = :userId", nativeQuery = true)
    int getTotalPointsForUser(int userId); ///*, @Param("variable") int variable*/

    PointEntity findPointEntityByUser_Id(int userId);
}
