package org.onsite.models.input;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CitrixTemplate {


    List<CitrixJob> jobs;

    @Override
    public String toString() {
        return "CitrixTemplate{" +
                "jobs=" + jobs +
                '}';
    }
}
