package com.threeoh.HowAbout.dateplan.repository;

import com.threeoh.HowAbout.dateplan.entity.DatePlan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface DatePlanRepository extends JpaRepository<DatePlan, Long> {
}
