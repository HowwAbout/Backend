package com.threeoh.HowAbout.dateplan.dto;

import com.threeoh.HowAbout.dateplan.entity.DatePlan;
import com.threeoh.HowAbout.dateplan.repository.DatePlanRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record DatePlanRequest(

        @Schema(description = "데이트 플랜 제목", example = "성수동에서의 활동적인 데이트")
        String title,

        @Schema(description = "데이트 플랜 날짜", example = "2024.09.28")
        LocalDate date,

        @Schema(description = "데이트 플랜 자세한 설명", example = "성수동 근처에서 자연을 즐기며 분위기 있는 데이트 코스")
        String description,

        @Schema(description = "이미지 URL")
        String image

) {

    public DatePlan toEntity() {
        return DatePlan.builder()
                .title(title)
                .date(date)
                .description(description)
                .image(image)
                .build();
    }

    public static DatePlanRequest of(String title, LocalDate date, String description, String image) {
        return DatePlanRequest.builder()
                .title(title)
                .date(date)
                .description(description)
                .image(image)
                .build();
    }
}
