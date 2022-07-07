package org.onsite.models.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CitrixJob {

    String description;
    String jobWorker;
    CitrixSchedule schedule;


    @Override
    public String toString() {
        return "CitrixJob{" +
                "description='" + description + '\'' +
                ", jobWorker='" + jobWorker + '\'' +
                ", schedule=" + schedule +
                '}';
    }
}