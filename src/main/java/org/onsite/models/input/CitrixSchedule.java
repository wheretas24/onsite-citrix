package org.onsite.models.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CitrixSchedule {
    String type;
    String startsAt;
    int interval;

    @Override
    public String toString() {
        return "CitrixSchedule{" +
                "type='" + type + '\'' +
                ", startsAt='" + startsAt + '\'' +
                ", interval=" + interval +
                '}';
    }
}
