package com.threeoh.HowAbout.dateplan.dto;


import com.threeoh.HowAbout.dateactivity.dto.DateActivityResponse;
import com.threeoh.HowAbout.dateplan.entity.PlanActivity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record PlanActivityResponse(

        @Schema(description = "데이트 플랜과 데이트 활동의 연결 id", example = "1")
        Long planActivityId,

        @Schema(description = "데이트 플랜의 id", example = "1")
        Long datePlanId,

        @Schema(description = "데이트 활동")
        DateActivityResponse dateActivityResponse,

        @Schema(description = "데이트 활동 순서", example = "1")
        int order

) {
    public static PlanActivityResponse from(PlanActivity planActivity) {
        if (planActivity == null) {
            return null;
        }
        Long datePlanId = planActivity.getDatePlan() != null ? planActivity.getDatePlan().getId() : null;

        return PlanActivityResponse.builder()
                .planActivityId(planActivity.getId())
                .datePlanId(datePlanId)
                .dateActivityResponse(DateActivityResponse.from(planActivity.getDateActivity()))
                .order(planActivity.getOrder())
                .build();
    }
}
