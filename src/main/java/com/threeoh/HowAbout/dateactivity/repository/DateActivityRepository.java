package com.threeoh.HowAbout.dateactivity.repository;

import com.threeoh.HowAbout.dateactivity.entity.DateActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DateActivityRepository extends JpaRepository<DateActivity, Long> {

}
