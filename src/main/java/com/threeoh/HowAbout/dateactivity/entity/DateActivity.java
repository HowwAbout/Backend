package com.threeoh.HowAbout.dateactivity.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dateactivities")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DateActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "location")
    private String location;

    @Column(name = "duration_time")
    private String durationTime;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Builder
    public DateActivity(String title, String location, String durationTime, String description, String image) {
        this.title = title;
        this.location = location;
        this.durationTime = durationTime;
        this.description = description;
        this.image = image;
    }

    public static DateActivity createDateActivity(String title, String location, String durationTime, String description, String image) {
        return DateActivity.builder()
                .title(title)
                .location(location)
                .durationTime(durationTime)
                .description(description)
                .image(image)
                .build();
    }

    public void updateDateActivity(String title, String location, String durationTime, String description, String image) {
        this.title = title;
        this.location = location;
        this.durationTime = durationTime;
        this.description = description;
        this.image = image;
    }

}
