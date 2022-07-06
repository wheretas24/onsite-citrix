package org.onsite;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.onsite.models.input.CitrixTemplate;

import java.nio.file.Paths;


@Slf4j
public class Main {

    public static void validateInput(){


        //if its runinterval -> it needs an interval time
        //

    }

    public static void main(String[] args) {

        // src/main/resources/input.json
        //String path = args[0];
        String path = "src/main/resources/input.json";

        log.info("Input Path is the following : {}", path);

        try {
            ObjectMapper mapper = new ObjectMapper();
            CitrixTemplate template = mapper.readValue(Paths.get(path).toFile(), CitrixTemplate.class);

            // TODO: 7/6/2022 add validation to the input

            log.info("JSON input conversion after mapping : {}", template);

            JobOrchestrator jobOrchestrator = new JobOrchestrator();
            jobOrchestrator.initiateJobs(template);

        } catch (Exception ex) {
            log.error("Error in parsing input file {} with exception: ", path, ex);
        }

    }
}