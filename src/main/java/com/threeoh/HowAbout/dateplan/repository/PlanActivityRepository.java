package com.threeoh.HowAbout.dateplan.repository;

import com.threeoh.HowAbout.dateplan.entity.PlanActivity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface PlanActivityRepository extends JpaRepository<PlanActivity, Long> {

    @Query("""
            SELECT pa
            FROM PlanActivity pa
            WHERE pa.datePlan.id = :datePlanId
            """)
    List<PlanActivity> findByDatePlanId(@Param("datePlanId") Long datePlanId);

    @Query("""
            SELECT pa
            FROM PlanActivity pa
            WHERE pa.dateActivity.id = :dateActivityId
            """)
    List<PlanActivity> findByDateActivityId(@Param("dateActivityId") Long dateActivityId);

    @Modifying
    @Query("""
            UPDATE PlanActivity pa 
            SET pa.order = pa.order + 1
            WHERE pa.datePlan.id = :datePlanId
            AND pa.order >= :order
            """)
    void incrementOrderForActivities(@Param("datePlanId") Long datePlanId, @Param("order") int order);

    @Modifying
    @Query("""
            UPDATE PlanActivity pa
            SET pa.order = pa.order - 1
            WHERE pa.datePlan.id = :datePlanId
            AND pa.order > :removedOrder
            """)
    void decrementOrderForActivities(@Param("datePlanId") Long datePlanId, @Param("removedOrder") int removedOrder);

    @Query("""
            SELECT MAX(pa.order)
            FROM PlanActivity pa
            WHERE pa.datePlan.id = :datePlanId
            """)
    Integer findMaxOrderByDatePlanId(@Param("datePlanId") Long datePlanId);

    @Modifying
    @Query("""
            UPDATE PlanActivity pa
            SET pa.order = pa.order + 1
            WHERE pa.datePlan.id = :datePlanId
            AND pa.order BETWEEN :startOrder AND :endOrder
            """)
    void incrementOrderForRange(@Param("datePlanId") Long datePlanId, @Param("startOrder") int startOrder, @Param("endOrder") int endOrder);

    @Modifying
    @Query("""
            UPDATE PlanActivity pa
            SET pa.order = pa.order - 1
            WHERE pa.datePlan.id = :datePlanId
            AND pa.order BETWEEN :startOrder AND :endOrder
            """)
    void decrementOrderForRange(@Param("datePlanId") Long datePlanId, @Param("startOrder") int startOrder, @Param("endOrder") int endOrder);
}
