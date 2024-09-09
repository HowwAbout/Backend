package com.threeoh.HowAbout.dateplan.controller;

import com.threeoh.HowAbout.dateplan.dto.DatePlanResponse;
import com.threeoh.HowAbout.dateplan.dto.PlanActivityRequest;
import com.threeoh.HowAbout.dateplan.dto.PlanActivityResponse;
import com.threeoh.HowAbout.dateplan.dto.PlanActivityResponseList;
import com.threeoh.HowAbout.dateplan.service.PlanActivityService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/plan-activities")
@RequiredArgsConstructor
public class PlanActivityController {

    private final PlanActivityService planActivityService;

    @Operation(summary = "Add Plan Activity By DatePlan ID", description = "DatePlan ID를 통해 플랜에 새로운 활동 추가")
    @PostMapping(path = "/{datePlanId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DatePlanResponse> addPlanActivity(@PathVariable Long datePlanId, @RequestBody PlanActivityRequest planActivityRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(planActivityService.addPlanActivity(datePlanId, planActivityRequest.dateActivityId(), planActivityRequest.order()));
    }

    @Operation(summary = "Remove Plan Activity", description = "DatePlan Id와 PlanActivity Id를 통한 특정 플랜에서 특정 활동을 제거")
    @DeleteMapping(path = "/{datePlanId}/{planActivityId}")
    public ResponseEntity<DatePlanResponse> removePlanActivity(@PathVariable Long datePlanId, @PathVariable Long planActivityId) {
        return ResponseEntity.ok(planActivityService.removePlanActivity(datePlanId, planActivityId));
    }

    @Operation(summary = "Update Plan Activity Order", description = "PlanActivity ID를 통해 플랜 내 특정 활동의 순서를 수정")
    @PutMapping(path = "/{planActivityId}/order", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlanActivityResponse> updateActivityOrder(@PathVariable Long planActivityId, @RequestParam int newOrder) {
        return ResponseEntity.ok(planActivityService.updateActivityOrder(planActivityId, newOrder));
    }

    @Operation(summary = "Get All Plan Activities By DatePlan Id", description = "DatePlan ID를 통해 특정 데이트 플랜에 속한 모든 활동 조회")
    @GetMapping(path = "/{datePlanId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlanActivityResponseList> getAllPlanActivitiesByDatePlanId(@PathVariable Long datePlanId) {
        return ResponseEntity.ok(planActivityService.getAllPlanActivitiesByDatePlanId(datePlanId));
    }

    @Operation(summary = "Get Plan Activity By PlanActivity ID", description = "PlanActivity ID를 통해 특정 플랜 활동을 조회")
    @GetMapping(path = "/{planActivityId}/detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlanActivityResponse> getPlanActivityById(@PathVariable Long planActivityId) {
        return ResponseEntity.ok(planActivityService.getPlanActivityById(planActivityId));
    }

    @Operation(summary = "Update PlanActivity by PlanActivity ID", description = "PlanActivity ID를 통해 특정 플랜 활동을 수정")
    @PutMapping(path = "/{planActivityId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlanActivityResponse> updatePlanActivity(@PathVariable Long planActivityId, @RequestBody PlanActivityRequest planActivityRequest) {
        return ResponseEntity.ok(planActivityService.updatePlanActivity(planActivityId, planActivityRequest));
    }
}
