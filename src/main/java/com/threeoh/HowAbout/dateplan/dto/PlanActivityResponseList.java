package com.threeoh.HowAbout.dateplan.dto;

import com.threeoh.HowAbout.dateplan.entity.PlanActivity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

public record PlanActivityResponseList(

        @Schema(description = "데이트 플랜 & 데이트 활동 연결 객체인 PlanActivity 리스트")
        List<PlanActivityResponse> planActivities

) {
    public static PlanActivityResponseList from (List<PlanActivity> planActivities) {
        List<PlanActivityResponse> planActivityResponses;

        if(planActivities == null) {
            planActivityResponses = new ArrayList<>();
        } else {
            planActivityResponses = planActivities.stream()
                    .map(PlanActivityResponse::from)
                    .toList();
        }
        return new PlanActivityResponseList(planActivityResponses);
    }
}
