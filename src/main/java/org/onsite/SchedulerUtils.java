package org.onsite;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.onsite.models.input.CitrixSchedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


@Slf4j
public final class SchedulerUtils {

    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS",Locale.getDefault());

    public static Date getDate(CitrixSchedule citrixSchedule) throws ParseException {

        log.info("Converting input date to date object for input: {}", citrixSchedule.getStartsAt());

        Date date;
        if(StringUtils.isEmpty(citrixSchedule.getStartsAt())){
            date = new Date();
        }else{
            date = formatter.parse(citrixSchedule.getStartsAt());
        }

        log.info("Successfully Converted input date to date object {} for input: {}", date, citrixSchedule.getStartsAt());

        return date;
    }
}
