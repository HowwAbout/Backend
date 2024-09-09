package com.threeoh.HowAbout.dateplan.repository;

import com.threeoh.HowAbout.dateplan.entity.PlanActivity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

}
