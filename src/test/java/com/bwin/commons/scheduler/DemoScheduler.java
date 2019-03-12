package com.bwin.commons.scheduler;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DemoScheduler {

    @Scheduled(cron = "0/15 * * * * ? ")
    @SchedulerLock(name = "DemoScheduler_scheduledTask", lockAtLeastForString = "PT5M", lockAtMostForString = "PT14M")
    public void scheduledTask() {
        log.info("scheduledTask");
    }

}
