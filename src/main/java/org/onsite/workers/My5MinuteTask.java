package org.onsite.workers;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.TimerTask;

@Slf4j
public class My5MinuteTask extends TimerTask {


    @Override
    public void run() {
        log.info("My5MinuteTask: executing at time {}", new Date());

    }
}
