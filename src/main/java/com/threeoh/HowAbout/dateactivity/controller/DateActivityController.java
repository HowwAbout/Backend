package com.threeoh.HowAbout.dateactivity.controller;

import com.threeoh.HowAbout.dateactivity.dto.DateActivityRequest;
import com.threeoh.HowAbout.dateactivity.dto.DateActivityResponse;
import com.threeoh.HowAbout.dateactivity.dto.DateActivityResponseList;
import com.threeoh.HowAbout.dateactivity.service.DateActivityService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/dateActivities")
@RequiredArgsConstructor
public class DateActivityController {

    private final DateActivityService dateActivityService;

    @Operation(summary = "Create Date Activity", description = "새로운 데이트 활동을 생성")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DateActivityResponse> createDateActivity(@RequestBody DateActivityRequest dateActivityRequest) {
        DateActivityResponse dateActivityResponse = dateActivityService.createDateActivity(dateActivityRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(dateActivityResponse);
    }

    @Operation(summary = "Get Date Activity", description = "DateActivity ID를 통해 특정 데이트 활동을 조회")
    @GetMapping(path = "/{dateActivityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DateActivityResponse> getDateActivityById(@PathVariable Long dateActivityId) {
        DateActivityResponse dateActivityResponse = dateActivityService.getDateActivityById(dateActivityId);
        return ResponseEntity.ok(dateActivityResponse);
    }

    @Operation(summary = "Get All Date Activities", description = "모든 데이트 활동을 조회")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DateActivityResponseList> getAllDateActivities() {
        DateActivityResponseList dateActivityResponseList = dateActivityService.getAllDateActivities();
        return ResponseEntity.ok(dateActivityResponseList);
    }

    @Operation(summary = "Update Date Activity", description = "특정 데이트 활동을 수정")
    @PutMapping(path = "/{dateActivityId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DateActivityResponse> updateDateActivity(
            @PathVariable Long dateActivityId,
            @RequestBody DateActivityRequest dateActivityRequest
    ) {
        DateActivityResponse dateActivityResponse = dateActivityService.updateDateActivity(dateActivityId, dateActivityRequest);
        return ResponseEntity.ok(dateActivityResponse);
    }

    @Operation(summary = "Delete Date Activity", description = "특정 데이트 활동을 삭제")
    @DeleteMapping(path = "/{dateActivityId}")
    public ResponseEntity<Void> deleteDateActivity(@PathVariable Long dateActivityId) {
        dateActivityService.deleteDateActivity(dateActivityId);
        return ResponseEntity.noContent().build();
    }

}
