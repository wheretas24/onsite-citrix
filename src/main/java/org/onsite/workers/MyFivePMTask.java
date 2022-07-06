package org.onsite.workers;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.TimerTask;

@Slf4j
public class MyFivePMTask extends TimerTask{

    String description;
    public MyFivePMTask(String description){
        this.description = description;
    }

    @Override
    public void run() {
        log.info("MyFivePMTask: executing at time {}", new Date());

    }
}
