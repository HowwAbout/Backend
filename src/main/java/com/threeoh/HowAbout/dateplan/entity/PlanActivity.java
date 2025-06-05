package com.threeoh.HowAbout.dateplan.entity;

import com.threeoh.HowAbout.dateactivity.entity.DateActivity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "plan_activities")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plan_id")
    private DatePlan datePlan;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "activity_id")
    private DateActivity dateActivity;

    @Column(name = "sort_order")
    private int sortOrder;

    @Builder
    public PlanActivity(DatePlan datePlan, DateActivity dateActivity, int sortOrder) {
        this.datePlan = datePlan;
        this.dateActivity = dateActivity;
        this.sortOrder = sortOrder;
    }

    public static PlanActivity create(DatePlan datePlan, DateActivity dateActivity, int sortOrder) {
        return new PlanActivity(datePlan, dateActivity, sortOrder);
    }

    public void updateDatePlan(DatePlan datePlan) {
        this.datePlan = datePlan;
    }

    public void updateSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void updatePlanActivity(DateActivity dateActivity, int sortOrder) {
        this.dateActivity = dateActivity;
        this.sortOrder = sortOrder;
    }
}
