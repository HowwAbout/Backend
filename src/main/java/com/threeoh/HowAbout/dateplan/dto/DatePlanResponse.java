package com.threeoh.HowAbout.dateplan.dto;

import com.threeoh.HowAbout.dateplan.entity.DatePlan;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record DatePlanResponse(

        @Schema(description = "데이트 플랜 id", example = "1")
        Long id,

        @Schema(description = "데이트 플랜 제목", example = "성수동에서의 활동적인 데이트")
        String title,

        @Schema(description = "데이트 플랜 날짜", example = "2024.09.28")
        LocalDate date,

        @Schema(description = "데이트 플랜 자세한 설명", example = "성수동 근처에서 자연을 즐기며 분위기 있는 데이트 코스")
        String description,

        @Schema(description = "플랜에 속한 활동들")
        PlanActivityResponseList planActivityResponseList

) {
    public static DatePlanResponse from(DatePlan datePlan) {
        return DatePlanResponse.builder()
                .id(datePlan.getId())
                .title(datePlan.getTitle())
                .date(datePlan.getDate())
                .description(datePlan.getDescription())
                .planActivityResponseList(PlanActivityResponseList.from(datePlan.getPlanActivities()))
                .build();
    }
}
