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

import java.util.Comparator;
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

        // 데이터베이스에서 순서 밀어내기 처리
        planActivityRepository.incrementOrderForActivities(datePlanId, order);

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

        planActivityRepository.decrementOrderForActivities(datePlanId, removedOrder);

        return DatePlanResponse.from(datePlanRepository.save(datePlan));
    }

    @Transactional
    public PlanActivityResponse updateActivityOrder(Long planActivityId, int newOrder) {
        PlanActivity planActivity = planActivityRepository.findById(planActivityId)
                .orElseThrow(() -> new IllegalArgumentException("PlanActivity not found with PlanActivity id : " + planActivityId));
        DatePlan datePlan = planActivity.getDatePlan();
        int currentOrder = planActivity.getOrder();
        int maxOrder = planActivityRepository.findMaxOrderByDatePlanId(datePlan.getId());
        if ( newOrder < 1 || newOrder > maxOrder + 1) {
            throw new IllegalArgumentException("Invalid order value. It must be between 1 and " + (maxOrder + 1));
        }
        if (newOrder > currentOrder) {
            planActivityRepository.decrementOrderForRange(datePlan.getId(), currentOrder + 1, newOrder);
        } else if (newOrder < currentOrder) {
            planActivityRepository.incrementOrderForRange(datePlan.getId(), newOrder, currentOrder - 1);
        }

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

        DatePlan datePlan = planActivity.getDatePlan();
        int newOrder = planActivityRequest.order();
        int currentOrder = planActivity.getOrder();
        int maxOrder = planActivityRepository.findMaxOrderByDatePlanId(datePlan.getId());
        if ( newOrder < 1 || newOrder > maxOrder + 1) {
            throw new IllegalArgumentException("Invalid order value. It must be between 1 and " + (maxOrder + 1));
        }
        if (newOrder > currentOrder) {
            planActivityRepository.decrementOrderForRange(datePlan.getId(), currentOrder + 1, newOrder);
        } else if (newOrder < currentOrder) {
            planActivityRepository.incrementOrderForRange(datePlan.getId(), newOrder, currentOrder - 1);
        }

        planActivity.updateOrder(newOrder);

        planActivity.updatePlanActivity(dateActivity, planActivityRequest.order());

        return PlanActivityResponse.from(planActivityRepository.save(planActivity));
    }





}
