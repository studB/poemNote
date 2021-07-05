package com.studb.poemNote.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import com.studb.poemNote.domain.backup.Backup;
import com.studb.poemNote.domain.backup.BackupDto;
import com.studb.poemNote.error.Exception.BreakDatabaseWorkingException;
import com.studb.poemNote.repository.backup.BackupRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BackupService {
    
    private final BackupRepository backupRepository;

    public List<BackupDto> findAll(){
        try{
            return backupRepository.findAll().stream()
                    .map(BackupDto::new)
                    .collect(Collectors.toList());
        }catch(Exception e){
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }

    /*
    * For Line Backup
    */
    public void doBackup(String textId, String body, Timestamp timestamp){
        try{
            Backup back = new Backup.Builder()
                            .categoryId(2)
                            .textId(textId)
                            .publishIndex(-1)
                            .body(body)
                            .time(timestamp)
                            .build();

            backupRepository.doBackUp(back);
        }catch(Exception e){
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }

    /*
    * For opinion Backup
    */
    public void doBackup(String textId, String body, String tag, Timestamp timestamp){
        try{
            Backup back = new Backup.Builder()
                            .categoryId(3)
                            .textId(textId)
                            .publishIndex(-1)
                            .body(body)
                            .tag(tag)
                            .time(timestamp)
                            .build();
            backupRepository.doBackUp(back);
        }catch(Exception e){
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }

    /*
    * For Theory Backup
    */ 
    public void doBackup(String textId, String title, String body, String tag, Timestamp timestamp){
        try{
            Backup back = new Backup.Builder()
                            .categoryId(4)
                            .textId(textId)
                            .publishIndex(-1)
                            .title(title)
                            .body(body)
                            .tag(tag)
                            .time(timestamp)
                            .build();
            backupRepository.doBackUp(back);
        }catch(Exception e){
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }

    /*
    * For Poem Backup
    */
    public void doBackup(String textId, int publishIndex, String title, String body, String tag, String completedStatus, String writtenStatus, String valueStatus, Timestamp timestamp){
        try{
            Backup back = new Backup.Builder()
                            .categoryId(1)
                            .textId(textId)
                            .publishIndex(publishIndex)
                            .title(title)
                            .body(body)
                            .tag(tag)
                            .completedStatus(completedStatus)
                            .writtenStatus(writtenStatus)
                            .valuStatus(valueStatus)
                            .time(timestamp)
                            .build();
            backupRepository.doBackUp(back);
        }catch(Exception e){
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }

    /*
    * For Review Backup
    */
    public void doBackup(String textId, int publishIndex, String title, String body, String tag, String writtenStatus, String valueStatus, Timestamp timestamp){
        try{
            Backup back = new Backup.Builder()
                            .categoryId(1)
                            .textId(textId)
                            .publishIndex(publishIndex)
                            .title(title)
                            .body(body)
                            .tag(tag)
                            .writtenStatus(writtenStatus)
                            .valuStatus(valueStatus)
                            .time(timestamp)
                            .build();
            backupRepository.doBackUp(back);
        }catch(Exception e){
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }


}
