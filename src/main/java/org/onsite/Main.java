package org.onsite;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.onsite.models.input.CitrixJob;
import org.onsite.models.input.CitrixSchedule;
import org.onsite.models.input.CitrixTemplate;

import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Slf4j
public class Main {

    public static void validateInput(CitrixTemplate template) throws ParseException {

        log.info("Initiating validation on json input file...");
        if(template == null){
            log.error("Input file is null");
            throw new RuntimeException("Input file is null");
        }

        List<String> errorList = new ArrayList<>();
        for(int i = 0; i < template.getJobs().size(); i++){
            CitrixJob job = template.getJobs().get(i);

            if(StringUtils.isEmpty(job.getDescription())){
                errorList.add("Required field is empty: 'Description' for entry #" + i);
            }

            if(StringUtils.isEmpty(job.getJobWorker())){
                errorList.add("Required field is empty: 'jobWorker' for entry #" + i);
            }else{
                if(!job.getJobWorker().equalsIgnoreCase(SchedulerConstants.MY1MINUTETASK)
                        && !job.getJobWorker().equalsIgnoreCase(SchedulerConstants.MY5MINUTETASK)
                        && !job.getJobWorker().equalsIgnoreCase(SchedulerConstants.MYFIVEPMTASK)){

                    // TODO: 7/7/2022 improvement could be made to display allowed values
                    errorList.add("jobWorker entry is invalid for entry #" + i);
                }
            }

            if(job.getSchedule() == null){
                errorList.add("Required field is null: 'schedule' for entry #" + i);
            }else{
                CitrixSchedule schedule = job.getSchedule();
                if(StringUtils.isEmpty(schedule.getType())){
                    errorList.add("Required field is null: 'type' for entry #" + i);
                }else{

                    if(!schedule.getType().equalsIgnoreCase(SchedulerConstants.RUN_INTERVAL)
                            && !schedule.getType().equalsIgnoreCase(SchedulerConstants.RUN_ONCE)){
                        errorList.add("Required entry for: 'type' is invalid for entry #" + i);

                    }

                    if(schedule.getType().equalsIgnoreCase(SchedulerConstants.RUN_INTERVAL)
                            && schedule.getInterval() == 0){
                        errorList.add("Required field for RunInterval Schedule is null: 'interval' for entry #" + i);
                    }
                }

                if(!StringUtils.isEmpty(schedule.getStartsAt())){
                    Date current = new Date();
                    if(current.compareTo(SchedulerUtils.getDate(schedule)) > 0){
                        errorList.add("StartsAt date is in the past: for entry #" + i);
                    }

                }
            }
        }

        if(errorList.size() != 0){
            log.error("Validation failed: " + errorList);
            throw new RuntimeException(String.join("|", errorList));
        }

        log.info("validation completed");
    }

    public static void main(String[] args) {
        String path = "src/main/resources/input.json";

        log.info("Input Path is the following : {}", path);

        try {
            ObjectMapper mapper = new ObjectMapper();
            CitrixTemplate template = mapper.readValue(Paths.get(path).toFile(), CitrixTemplate.class);

            validateInput(template);

            log.info("JSON input conversion after mapping : {}", template);

            JobOrchestrator jobOrchestrator = new JobOrchestrator();
            jobOrchestrator.initiateJobs(template);

        } catch (Exception ex) {
            log.error("Error in parsing input file {} with exception: ", path, ex);
            throw new RuntimeException(ex);
        }

        log.info("all jobs have been successfully scheduled!");

    }
}