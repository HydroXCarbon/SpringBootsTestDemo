package com.backend.schedule;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserSchedule {

    // 1. Every Second
    // 2. Every minute
    // 3. Every hour
    // 4. Every day
    // 5. Every month
    // 6. Every year

    // cron default is UTC
    // set zone to Thailand which is UTC+7
//    @Scheduled(cron = "0 * * * * *", zone = "Asia/Bangkok" )
//    public void testEveryMinute() {
//        log.info("This is a test of the UserSchedule class");
//    }
//
//    @Scheduled(cron = "0 0 0 * * *" )
//    public void testEveryStartOfDay() {
//        log.info("This is a test of the UserSchedule class");
//    }
//
//    @Scheduled(cron = "0 30 10 * * *" )
//    public void testEveryDay10am30min() {
//        log.info("This is a test of the UserSchedule class");
//    }
}
