package com.studb.poemNote.utils;

import java.sql.Timestamp;
import java.util.Calendar;

import com.studb.poemNote.domain.log.Log;

import org.springframework.stereotype.Component;

@Component
public class LogForm {
    
    Calendar cal = Calendar.getInstance();

    public LogForm(){};

    public Log makeLog(String subject, String action, String log, Timestamp timestamp){
        cal.setTimeInMillis(timestamp.getTime());
        cal.add(Calendar.MONTH, 1);
        return Log.builder()
                    .subject(subject)
                    .action(action)
                    .log(log)
                    .year(cal.get(Calendar.YEAR))
                    .month(cal.get(Calendar.MONTH))
                    .day(cal.get(Calendar.DAY_OF_MONTH))
                    .hour(cal.get(Calendar.HOUR))
                    .minute(cal.get(Calendar.MINUTE))
                    .build();
    }

}
