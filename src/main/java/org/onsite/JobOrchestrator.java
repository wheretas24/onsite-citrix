package org.onsite;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.onsite.models.input.CitrixJob;
import org.onsite.models.input.CitrixSchedule;
import org.onsite.models.input.CitrixTemplate;
import org.onsite.workers.My1MinuteTask;
import org.onsite.workers.My5MinuteTask;
import org.onsite.workers.MyFivePMTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;

import static org.onsite.SchedulerConstants.*;

@Slf4j
public class JobOrchestrator {



    SimpleDateFormat formatter;

    public JobOrchestrator(){
        this.formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.ENGLISH);
    }



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
                    log.error("invalid job-worker");
                    return;
            }
        }
    }

    private void start1MinuteTask(CitrixJob citrixJob) throws ParseException {

        log.info("Starting 1 Minute Task");

        My1MinuteTask my1MinuteTask = new My1MinuteTask(citrixJob.getDescription());
        Timer timer = new Timer();
        Date date = getDate(citrixJob.getSchedule());

        if(citrixJob.getSchedule().getInterval() == 0){
            timer.schedule(my1MinuteTask, date);
        }else{
            timer.schedule(my1MinuteTask, date ,citrixJob.getSchedule().getInterval());
        }

        log.info("1 Minute Task scheduled successfully");
    }

    private void start5MinuteTask(CitrixJob citrixJob) throws ParseException {

        log.info("Starting 5 minute task");

        My5MinuteTask my5MinuteTask = new My5MinuteTask(citrixJob.getDescription());
        Timer timer = new Timer();
        Date date = getDate(citrixJob.getSchedule());

        if(citrixJob.getSchedule().getInterval() == 0){
            timer.schedule(my5MinuteTask, date);
        }else{
            timer.schedule(my5MinuteTask, date ,citrixJob.getSchedule().getInterval());
        }

        log.info("5 Minute Task scheduled successfully");
    }

    private void startFivePMTask(CitrixJob citrixJob) throws ParseException {
        log.info("Starting Five PM task");

        MyFivePMTask myFivePMTask = new MyFivePMTask(citrixJob.getDescription());
        Timer timer = new Timer();
        Date date = getDate(citrixJob.getSchedule());

        if(citrixJob.getSchedule().getInterval() == 0){
            timer.schedule(myFivePMTask, date);
        }else{
            timer.schedule(myFivePMTask, date ,citrixJob.getSchedule().getInterval());
        }

        log.info("Five PM Task scheduled successfully");

    }

    private Date getDate(CitrixSchedule citrixSchedule) throws ParseException {

        log.info("Converting input date to date object for input: {}", citrixSchedule.getStartsAt());

        Date date;
        if(StringUtils.isEmpty(citrixSchedule.getStartsAt())){
            date = new Date();
        }else{
            date = formatter.parse(citrixSchedule.getStartsAt());
        }
        return date;
    }
}

