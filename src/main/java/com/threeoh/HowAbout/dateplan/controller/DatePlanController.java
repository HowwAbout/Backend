package com.threeoh.HowAbout.dateplan.controller;

import com.threeoh.HowAbout.dateplan.dto.DatePlanRequest;
import com.threeoh.HowAbout.dateplan.dto.DatePlanResponse;
import com.threeoh.HowAbout.dateplan.dto.PlanActivityResponseList;
import com.threeoh.HowAbout.dateplan.service.DatePlanService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/date-plans")
@RequiredArgsConstructor
public class DatePlanController {

    private final DatePlanService datePlanService;

    @Operation(summary = "Create Date Plan", description = "새로운 데이트 플랜 생성")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DatePlanResponse> createDatePlan(@RequestBody DatePlanRequest datePlanRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(datePlanService.createDatePlan(datePlanRequest));
    }

    @Operation(summary = "Get Date Plan by DatePlan Id", description = "DatePlan ID를 통해 특정 데이트 플랜 조회")
    @GetMapping(path = "/{datePlanId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DatePlanResponse> getDatePlanById(@PathVariable Long datePlanId) {
        return ResponseEntity.ok(datePlanService.getDatePlanById(datePlanId));
    }

    @Operation(summary = "Get All Date Plans", description = "모든 데이터 플랜 조회")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DatePlanResponse>> getAllDatePlans() {
        return ResponseEntity.ok(datePlanService.getAllDatePlans());
    }

    @Operation(summary = "Get Plan Activities By Date Plan ID", description = "DatePlan ID의 플랜에 해당하는 활동들을 조회")
    @GetMapping(path = "/{datePlanId}/activities", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlanActivityResponseList> getPlanActivitiesByDatePlanId(@PathVariable Long datePlanId) {
        return ResponseEntity.ok(datePlanService.getPlanActivitiesByDatePlanId(datePlanId));
    }

    @Operation(summary = "Update Date Plan by DatePlan ID", description = " DatePlan ID를 통해 특정 데이트 플랜을 수정")
    @PutMapping(path = "/{datePlanId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DatePlanResponse> updateDatePlan(@PathVariable Long datePlanId, @RequestBody DatePlanRequest datePlanRequest) {
        return ResponseEntity.ok(datePlanService.updateDatePlan(datePlanId, datePlanRequest));
    }

    @Operation(summary = "Delete Date Plan By ID", description = "DatePlan ID를 통한 데이트 플랜 삭제")
    @DeleteMapping(path = "/{datePlanId}")
    public ResponseEntity<Void> deleteDatePlan(@PathVariable Long datePlanId) {
        datePlanService.deleteDatePlan(datePlanId);
        return ResponseEntity.noContent().build();
    }
}
