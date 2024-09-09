package com.threeoh.HowAbout.dateplan.entity;

import com.threeoh.HowAbout.dateactivity.entity.DateActivity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "plan_activities")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plan_id")
    private DatePlan datePlan;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "activity_id")
    private DateActivity dateActivity;

    @Column(name = "activity_order")
    private int order;

    @Builder
    public PlanActivity(DatePlan datePlan, DateActivity dateActivity, int order) {
        this.datePlan = datePlan;
        this.dateActivity = dateActivity;
        this.order = order;
    }

    public void updateDatePlan(DatePlan datePlan) {
        this.datePlan = datePlan;
    }

    public void updateOrder(int order) {
        this.order = order;
    }

    public void updatePlanActivity(DateActivity dateActivity, int order) {
        this.dateActivity = dateActivity;
        this.order = order;
    }
}
