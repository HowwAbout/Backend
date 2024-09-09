package com.threeoh.HowAbout.dateplan.dto;

import com.threeoh.HowAbout.dateactivity.entity.DateActivity;
import com.threeoh.HowAbout.dateplan.entity.DatePlan;
import com.threeoh.HowAbout.dateplan.entity.PlanActivity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record PlanActivityRequest(

        @Schema(description = "데이트 활동 id", example = "1")
        Long dateActivityId,

        @Schema(description = "데이트 활동 순서", example = "1")
        int order

) {
    public PlanActivity toEntity(DatePlan datePlan, DateActivity dateActivity) {
        return new PlanActivity(datePlan, dateActivity, order);
    }
}
