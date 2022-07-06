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


/*


{
 "description": "Start a Task at 5pm today",
 "jobWorker": "org.onsite.MyFivePMTask",
 "schedule" : {
 "type": "RunOnce",
 "startsAt": "2022-07-01T17:00:00.000Z"
 }
 },


 */