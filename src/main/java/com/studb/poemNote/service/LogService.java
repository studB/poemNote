package com.studb.poemNote.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import com.studb.poemNote.domain.log.LogDto;
import com.studb.poemNote.error.Exception.BreakDatabaseWorkingException;
import com.studb.poemNote.repository.log.LogRepository;
import com.studb.poemNote.utils.LogForm;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LogService {
    
    private final LogRepository logRepository;
    private final LogForm logForm;

    public List<LogDto> findAll(){
        try{
            return logRepository.findAll().stream()
                    .map(LogDto::new)
                    .collect(Collectors.toList());
        }catch(Exception e){
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }

    public void insertLog(String subject, String action, String log, Timestamp timestamp) {
        try{
            logRepository.insertLog( logForm.makeLog(subject, action, log, timestamp) ); 
        }catch(Exception e){
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }
}
