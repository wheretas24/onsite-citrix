package org.onsite;


import lombok.extern.slf4j.Slf4j;
import org.onsite.models.input.CitrixJob;
import org.onsite.models.input.CitrixTemplate;
import org.onsite.workers.My1MinuteTask;
import org.onsite.workers.My5MinuteTask;
import org.onsite.workers.MyFivePMTask;

import java.text.ParseException;
import java.util.Date;
import java.util.Timer;

import static org.onsite.SchedulerConstants.*;

@Slf4j
public class JobOrchestrator {

    public void initiateJobs(CitrixTemplate citrixTemplate) throws ParseException {

        for(CitrixJob citrixJob : citrixTemplate.getJobs()){
            switch(citrixJob.getJobWorker()){
                case MY1MINUTETASK:
                    start1MinuteTask(citrixJob);
                    break;
                case MY5MINUTETASK:
                    start5MinuteTask(citrixJob);
                    break;
                case MYFIVEPMTASK:
                    startFivePMTask(citrixJob);
                    break;
                default:
                    return;
            }
        }
    }

    private void start1MinuteTask(CitrixJob citrixJob) throws ParseException {

        log.info("Starting 1 Minute Task");

        My1MinuteTask my1MinuteTask = new My1MinuteTask();
        Timer timer = new Timer();
        Date date = SchedulerUtils.getDate(citrixJob.getSchedule());

        if(citrixJob.getSchedule().getInterval() == 0){
            log.info("scheduling 1minutetask with no interval");
            timer.schedule(my1MinuteTask, date);
        }else{
            log.info("scheduling 1minutetask with interval of " + citrixJob.getSchedule().getInterval());
            timer.schedule(my1MinuteTask, date ,citrixJob.getSchedule().getInterval());
        }

        log.info("1 Minute Task scheduled successfully");
    }

    private void start5MinuteTask(CitrixJob citrixJob) throws ParseException {

        log.info("Starting 5 minute task");

        My5MinuteTask my5MinuteTask = new My5MinuteTask();
        Timer timer = new Timer();
        Date date = SchedulerUtils.getDate(citrixJob.getSchedule());

        if(citrixJob.getSchedule().getInterval() == 0){
            log.info("scheduling 5minutetask with no interval");
            timer.schedule(my5MinuteTask, date);
        }else{
            log.info("scheduling 5minutetask with interval of " + citrixJob.getSchedule().getInterval());
            timer.schedule(my5MinuteTask, date ,citrixJob.getSchedule().getInterval());
        }

        log.info("5 Minute Task scheduled successfully");
    }

    private void startFivePMTask(CitrixJob citrixJob) throws ParseException {
        log.info("Starting Five PM task");

        MyFivePMTask myFivePMTask = new MyFivePMTask();
        Timer timer = new Timer();
        Date date = SchedulerUtils.getDate(citrixJob.getSchedule());

        if(citrixJob.getSchedule().getInterval() == 0){
            log.info("scheduling FivePMtask with no interval");
            timer.schedule(myFivePMTask, date);
        }else{
            log.info("scheduling FivePMtask with interval of " + citrixJob.getSchedule().getInterval());
            timer.schedule(myFivePMTask, date ,citrixJob.getSchedule().getInterval());
        }

        log.info("Five PM Task scheduled successfully");

    }
}

