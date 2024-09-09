package com.threeoh.HowAbout.dateactivity.dto;

import com.threeoh.HowAbout.dateactivity.entity.DateActivity;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

public record DateActivityResponseList(

        @Schema(description = "데이트 활동 리스트")
        List<DateActivityResponse> dateActivities

) {
    public static DateActivityResponseList from(List<DateActivity> dateActivityList) {
        List<DateActivityResponse> dateActivityResponses;

        if(dateActivityList == null) {
            dateActivityResponses = new ArrayList<>(); // 빈 가변 리스트 생성해서 반환
        } else {
            dateActivityResponses = dateActivityList.stream()
                    .map(DateActivityResponse::from)
                    .toList();
        }
        return new DateActivityResponseList(dateActivityResponses);
    }
}
