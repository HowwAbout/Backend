package com.threeoh.HowAbout.dateplan.service;

import com.threeoh.HowAbout.dateactivity.entity.DateActivity;
import com.threeoh.HowAbout.dateactivity.repository.DateActivityRepository;
import com.threeoh.HowAbout.dateactivity.service.DateActivityService;
import com.threeoh.HowAbout.dateplan.dto.DatePlanResponse;
import com.threeoh.HowAbout.dateplan.dto.PlanActivityRequest;
import com.threeoh.HowAbout.dateplan.dto.PlanActivityResponse;
import com.threeoh.HowAbout.dateplan.dto.PlanActivityResponseList;
import com.threeoh.HowAbout.dateplan.entity.DatePlan;
import com.threeoh.HowAbout.dateplan.entity.PlanActivity;
import com.threeoh.HowAbout.dateplan.repository.DatePlanRepository;
import com.threeoh.HowAbout.dateplan.repository.PlanActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanActivityService {

    private final PlanActivityRepository planActivityRepository;
    private final DatePlanRepository datePlanRepository;
    private final DateActivityRepository dateActivityRepository;

    @Transactional
    public DatePlanResponse addPlanActivity(Long datePlanId, Long dateActivityId, int order) {
        DatePlan datePlan = datePlanRepository.findById(datePlanId)
                .orElseThrow(() -> new IllegalArgumentException("DatePlan not found with DatePlan id : " + datePlanId));
        DateActivity dateActivity = dateActivityRepository.findById(dateActivityId)
                .orElseThrow(() -> new IllegalArgumentException("DateActivity not found with DateActivity id : " + dateActivityId));

        datePlan.addDatePlanActivity(dateActivity, order);
        return DatePlanResponse.from(datePlanRepository.save(datePlan));
    }

    @Transactional
    public DatePlanResponse removePlanActivity(Long datePlanId, Long planActivityId) {
        DatePlan datePlan = datePlanRepository.findById(datePlanId)
                .orElseThrow(() -> new IllegalArgumentException("DatePlan not found with DatePlan id : " + datePlanId));
        PlanActivity planActivity = datePlan.getPlanActivities().stream()
                .filter(onePlanActivity -> onePlanActivity.getId().equals(planActivityId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("PlanActivity not found with PlanActivity id : " + planActivityId ));

        int removedOrder = planActivity.getOrder();
        datePlan.removePlanActivity(planActivity);

        // 순서 조정
        datePlan.getPlanActivities().stream()
                .filter(onePlanActivity -> onePlanActivity.getOrder() > removedOrder)
                .forEach(onePlanActivity -> onePlanActivity.updateOrder(onePlanActivity.getOrder() - 1));

        return DatePlanResponse.from(datePlanRepository.save(datePlan));
    }

    @Transactional
    public PlanActivityResponse updateActivityOrder(Long planActivityId, int newOrder) {
        PlanActivity planActivity = planActivityRepository.findById(planActivityId)
                .orElseThrow(() -> new IllegalArgumentException("PlanActivity not found with PlanActivity id : " + planActivityId));
        planActivity.updateOrder(newOrder);
        return PlanActivityResponse.from(planActivityRepository.save(planActivity));
    }

    @Transactional(readOnly = true)
    public PlanActivityResponseList getAllPlanActivitiesByDatePlanId(Long datePlanId) {
        List<PlanActivity> planActivityList = planActivityRepository.findByDatePlanId(datePlanId);
        return PlanActivityResponseList.from(planActivityList);
    }

    @Transactional(readOnly = true)
    public PlanActivityResponse getPlanActivityById(Long planActivityId) {
        PlanActivity planActivity = planActivityRepository.findById(planActivityId)
                .orElseThrow(() -> new IllegalArgumentException("PlanActivity not found with PlanActivity id :" + planActivityId));
        return PlanActivityResponse.from(planActivity);
    }

    @Transactional
    public PlanActivityResponse updatePlanActivity(Long planActivityId, PlanActivityRequest planActivityRequest) {
        PlanActivity planActivity = planActivityRepository.findById(planActivityId)
                .orElseThrow(() -> new IllegalArgumentException("PlanActivity not found with PlanActivity id : " + planActivityId));

        DateActivity dateActivity = dateActivityRepository.findById(planActivityRequest.dateActivityId())
                .orElseThrow(() -> new IllegalArgumentException("DateActivity not found with DateActivity id : " + planActivityRequest.dateActivityId()));

        planActivity.updatePlanActivity(dateActivity, planActivityRequest.order());

        return PlanActivityResponse.from(planActivityRepository.save(planActivity));
    }





}
