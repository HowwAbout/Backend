package com.threeoh.HowAbout.dateplan.service;

import com.threeoh.HowAbout.dateactivity.entity.DateActivity;
import com.threeoh.HowAbout.dateactivity.repository.DateActivityRepository;
import com.threeoh.HowAbout.dateactivity.service.DateActivityService;
import com.threeoh.HowAbout.dateplan.dto.DatePlanRequest;
import com.threeoh.HowAbout.dateplan.dto.DatePlanResponse;
import com.threeoh.HowAbout.dateplan.dto.PlanActivityResponseList;
import com.threeoh.HowAbout.dateplan.entity.DatePlan;
import com.threeoh.HowAbout.dateplan.entity.PlanActivity;
import com.threeoh.HowAbout.dateplan.repository.DatePlanRepository;
import com.threeoh.HowAbout.dateplan.repository.PlanActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DatePlanService {

    private final DatePlanRepository datePlanRepository;
    private final PlanActivityRepository planActivityRepository; // 추후 리팩토링으로 삭제 후 PlanActivityService의 메서드 호출로 사용할 예정

    @Transactional
    public DatePlanResponse createDatePlan(DatePlanRequest datePlanRequest) {
        DatePlan datePlan = datePlanRequest.toEntity();
        datePlanRepository.save(datePlan);
        return DatePlanResponse.from(datePlan);
    }

    @Transactional(readOnly = true)
    public DatePlanResponse getDatePlanById(Long datePlanId) {
        DatePlan datePlan = datePlanRepository.findById(datePlanId)
                .orElseThrow(() -> new IllegalArgumentException("DatePlan not found with DatePlan id : " + datePlanId));
        return DatePlanResponse.from(datePlan);
    }

    @Transactional(readOnly = true)
    public List<DatePlanResponse> getAllDatePlans() {
        return datePlanRepository.findAll().stream()
                .map(DatePlanResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public PlanActivityResponseList getPlanActivitiesByDatePlanId(Long datePlanId) {
        List<PlanActivity> planActivityList = planActivityRepository.findByDatePlanId(datePlanId);
        return PlanActivityResponseList.from(planActivityList);
    }

    @Transactional
    public DatePlanResponse updateDatePlan(Long datePlanId, DatePlanRequest datePlanRequest) {
        DatePlan datePlan = datePlanRepository.findById(datePlanId)
                .orElseThrow(() -> new IllegalArgumentException("DatePlan not found with DatePlan id : " + datePlanId));
        datePlan.updatePlan(
                datePlanRequest.title(),
                datePlanRequest.description(),
                datePlanRequest.date(),
                datePlanRequest.image()
        );
        return DatePlanResponse.from(datePlanRepository.save(datePlan));
    }

    @Transactional
    public void deleteDatePlan(Long datePlanId) {
        DatePlan datePlan = datePlanRepository.findById(datePlanId)
                .orElseThrow(() -> new IllegalArgumentException("DatePlan not found with DatePlan id : " + datePlanId));
        datePlanRepository.delete(datePlan);
    }

}
