package com.threeoh.HowAbout.dateplan.entity;

import com.threeoh.HowAbout.dateactivity.entity.DateActivity;
import com.threeoh.HowAbout.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dateplans")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DatePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //추후 개발 시 주석 해제
//    @NotNull
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "user_id")
//    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private LocalDate date; // 날짜

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "datePlan", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "activity_order")
    private List<PlanActivity> planActivities = new ArrayList<>();

    @Builder
    public DatePlan(String title, String description, LocalDate date, String image) {
        // User 인수로 받아 초기화 하는 코드 추후 개발에 추가
        this.title = title;
        this.description = description;
        this.date = date;
        this.image = image;
    }

    public void addDatePlanActivity(DateActivity dateActivity, int order) {
        PlanActivity planActivity = new PlanActivity(this, dateActivity, order);
        this.planActivities.add(planActivity);
    }

    public void removePlanActivity(PlanActivity planActivity) {
        this.planActivities.remove(planActivity);
        planActivity.updateDatePlan(null);
    }

    public void updatePlan(String title, String description, LocalDate date, String image) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.image = image;
    }
}
