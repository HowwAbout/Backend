package com.threeoh.HowAbout.dateactivity.service;

import com.threeoh.HowAbout.dateactivity.dto.DateActivityRequest;
import com.threeoh.HowAbout.dateactivity.dto.DateActivityResponse;
import com.threeoh.HowAbout.dateactivity.dto.DateActivityResponseList;
import com.threeoh.HowAbout.dateactivity.entity.DateActivity;
import com.threeoh.HowAbout.dateactivity.repository.DateActivityRepository;
import com.threeoh.HowAbout.dateplan.service.DatePlanService;
import com.threeoh.HowAbout.dateplan.service.PlanActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DateActivityService {

    private final DateActivityRepository dateActivityRepository;

    @Transactional
    public DateActivityResponse createDateActivity(DateActivityRequest dateActivityRequest) {
        DateActivity dateActivity = dateActivityRequest.toEntity();
        return DateActivityResponse.from(dateActivityRepository.save(dateActivity));
    }

    @Transactional(readOnly = true)
    public DateActivityResponse getDateActivityById(Long dateActivityId) {
        DateActivity dateActivity = dateActivityRepository.findById(dateActivityId)
                .orElseThrow(() -> new IllegalArgumentException("DateActivity not found with DateActivity id : " + dateActivityId));
        return DateActivityResponse.from(dateActivity);
    }

    @Transactional(readOnly = true)
    public DateActivityResponseList getAllDateActivities() {
        return DateActivityResponseList.from(dateActivityRepository.findAll());
    }

    @Transactional
    public DateActivityResponse updateDateActivity(Long dateActivityId, DateActivityRequest dateActivityRequest) {
        DateActivity dateActivity = dateActivityRepository.findById(dateActivityId)
                .orElseThrow(() -> new IllegalArgumentException("DateActivity not found with DateActivity id : " + dateActivityId));
        dateActivity.updateDateActivity(
                dateActivityRequest.title(),
                dateActivityRequest.location(),
                dateActivityRequest.durationTime(),
                dateActivityRequest.description(),
                dateActivityRequest.image()
        );

        return DateActivityResponse.from(dateActivityRepository.save(dateActivity));
    }

    @Transactional
    public void deleteDateActivity(Long dateActivityId) {
        DateActivity dateActivity = dateActivityRepository.findById(dateActivityId)
                .orElseThrow(() -> new IllegalArgumentException("DateActivity not found with DateActivity id : " + dateActivityId));
        dateActivityRepository.delete(dateActivity);
    }
}
