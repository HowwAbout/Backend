package com.threeoh.HowAbout.dateplan.service;

import com.threeoh.HowAbout.dateactivity.entity.DateActivity;
import com.threeoh.HowAbout.dateactivity.repository.DateActivityRepository;
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
    public DatePlanResponse addPlanActivity(Long datePlanId, Long dateActivityId, int sortOrder) {
        DatePlan datePlan = getDatePlanOrThrow(datePlanId);
        DateActivity dateActivity = getDateActivityOrThrow(dateActivityId);

        // 데이터베이스에서 순서 밀어내기 처리
        planActivityRepository.incrementOrderForActivities(datePlanId, sortOrder);

        datePlan.addDatePlanActivity(dateActivity, sortOrder);
        return DatePlanResponse.from(datePlanRepository.save(datePlan));
    }

    @Transactional
    public DatePlanResponse removePlanActivity(Long datePlanId, Long planActivityId) {
        DatePlan datePlan = getDatePlanOrThrow(datePlanId);
        PlanActivity planActivity = datePlan.getPlanActivities().stream()
                .filter(onePlanActivity -> onePlanActivity.getId().equals(planActivityId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("PlanActivity not found with PlanActivity id : " + planActivityId ));

        int removedOrder = planActivity.getSortOrder();
        datePlan.removePlanActivity(planActivity);

        planActivityRepository.decrementOrderForActivities(datePlanId, removedOrder);

        return DatePlanResponse.from(datePlanRepository.save(datePlan));
    }

    @Transactional
    public PlanActivityResponse updateActivityOrder(Long planActivityId, int newOrder) {
        PlanActivity planActivity = getPlanActivityOrThrow(planActivityId);
        DatePlan datePlan = planActivity.getDatePlan();
        int currentOrder = planActivity.getSortOrder();
        int maxOrder = planActivityRepository.findMaxOrderByDatePlanId(datePlan.getId());
        if ( newOrder < 1 || newOrder > maxOrder + 1) {
            throw new IllegalArgumentException("Invalid sortOrder value. It must be between 1 and " + (maxOrder + 1));
        }
        if (newOrder > currentOrder) {
            planActivityRepository.decrementOrderForRange(datePlan.getId(), currentOrder + 1, newOrder);
        } else if (newOrder < currentOrder) {
            planActivityRepository.incrementOrderForRange(datePlan.getId(), newOrder, currentOrder - 1);
        }

        planActivity.updateSortOrder(newOrder);
        return PlanActivityResponse.from(planActivityRepository.save(planActivity));
    }

    @Transactional(readOnly = true)
    public PlanActivityResponseList getAllPlanActivitiesByDatePlanId(Long datePlanId) {
        List<PlanActivity> planActivityList = planActivityRepository.findByDatePlanId(datePlanId);
        return PlanActivityResponseList.from(planActivityList);
    }

    @Transactional(readOnly = true)
    public PlanActivityResponse getPlanActivityById(Long planActivityId) {
        PlanActivity planActivity = getPlanActivityOrThrow(planActivityId);
        return PlanActivityResponse.from(planActivity);
    }

    @Transactional
    public PlanActivityResponse updatePlanActivity(Long planActivityId, PlanActivityRequest planActivityRequest) {
        PlanActivity planActivity = getPlanActivityOrThrow(planActivityId);

        DateActivity dateActivity = getDateActivityOrThrow(planActivityRequest.dateActivityId());
        DatePlan datePlan = planActivity.getDatePlan();
        int newOrder = planActivityRequest.sortOrder();
        int currentOrder = planActivity.getSortOrder();
        int maxOrder = planActivityRepository.findMaxOrderByDatePlanId(datePlan.getId());
        if ( newOrder < 1 || newOrder > maxOrder + 1) {
            throw new IllegalArgumentException("Invalid sortOrder value. It must be between 1 and " + (maxOrder + 1));
        }
        if (newOrder > currentOrder) {
            planActivityRepository.decrementOrderForRange(datePlan.getId(), currentOrder + 1, newOrder);
        } else if (newOrder < currentOrder) {
            planActivityRepository.incrementOrderForRange(datePlan.getId(), newOrder, currentOrder - 1);
        }

        planActivity.updateSortOrder(newOrder);

        planActivity.updatePlanActivity(dateActivity, planActivityRequest.sortOrder());

        return PlanActivityResponse.from(planActivityRepository.save(planActivity));
    }

    private DatePlan getDatePlanOrThrow(Long datePlanId) {
        return datePlanRepository.findById(datePlanId)
                .orElseThrow(() -> new IllegalArgumentException("DatePlan not found with id: " + datePlanId));
    }

    private PlanActivity getPlanActivityOrThrow(Long planActivityId) {
        return planActivityRepository.findById(planActivityId)
                .orElseThrow(() -> new IllegalArgumentException("PlanActivity not found with id: " + planActivityId));
    }

    private DateActivity getDateActivityOrThrow(Long dateActivityId) {
        return dateActivityRepository.findById(dateActivityId)
                .orElseThrow(() -> new IllegalArgumentException("DateActivity not found with id: " + dateActivityId));
    }

}
