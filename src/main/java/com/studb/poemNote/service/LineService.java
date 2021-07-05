package com.studb.poemNote.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.studb.poemNote.domain.line.Line;
import com.studb.poemNote.domain.line.LineDto;
import com.studb.poemNote.error.Exception.BreakDatabaseWorkingException;
import com.studb.poemNote.repository.line.LineRepository;
import com.studb.poemNote.utils.OperationResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class LineService {
    
    private final LineRepository lineRepository;
    private final LogService logService;
    private final BackupService backupService;

    @Autowired
    private DataSourceTransactionManager txManager;

    public List<LineDto> findAll(){
        try{
            log.info("LineService.findAll");
            return lineRepository.findAll().stream()
                    .map(LineDto::new)
                    .collect(Collectors.toList());
        }catch(Exception e){
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }

    public LineDto findAny(String textId){
        try{
            log.info("LineService.findAny");
            Optional<LineDto> result = lineRepository.findAny(textId).stream().map(LineDto::new).findAny();
            return result.get();
        }catch(Exception e){
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }

    public OperationResult save(Line line){

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        OperationResult operationResult = new OperationResult();
        operationResult.addElmt("Category", "Line");
        operationResult.addElmt("Action", "Write");
        operationResult.addElmt("Time", timestamp);

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try{
            // Save Line to DB
            lineRepository.save(line, timestamp);
            // Save Backup
            backupService.doBackup(line.getTextId(), line.getBody(), timestamp);
            // Save Log
            logService.insertLog("EDITOR", "Write", "[LINE] -", timestamp);

            txManager.commit(status);  
            operationResult.addElmt("Error", false);
            operationResult.addElmt("msg", "Complete to write!");
            log.info("LineService.save : saved");
            return operationResult;
        }catch(Exception e){

            txManager.rollback(status);

            operationResult.addElmt("Error", true);
            operationResult.addElmt("ErrorMsg", e.getMessage());
            operationResult.addElmt("ErrorCause", e.getCause().getMessage());
            operationResult.addElmt("msg", "Failed to Write line, All process is aborted");
            log.error("LineService.save : Failed to save");
            return operationResult;
        }
    }

    public OperationResult delete(String textId){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        OperationResult operationResult = new OperationResult();
        operationResult.addElmt("Category", "Line");
        operationResult.addElmt("Action", "Erase");
        operationResult.addElmt("Time", timestamp);

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);
        
        try{
            int result = lineRepository.delete(textId);
            if(result == 0) {
                txManager.rollback(status);
                operationResult.addElmt("Error", true);
                operationResult.addElmt("msg", "Any content isn't deleted, wrong textId");
                log.warn("LineService.delete : No content");
            }else {
                logService.insertLog("EDITOR", "Erase", "[LINE] -", timestamp);
                txManager.commit(status);
                operationResult.addElmt("Error", false);
                operationResult.addElmt("msg", "Complete to delete!");
                log.info("LineService.delete : deleted");
            }
            return operationResult;
        }catch(Exception e){
            txManager.rollback(status);
            operationResult.addElmt("Error", true);
            operationResult.addElmt("ErrorMsg", e.getMessage());
            operationResult.addElmt("ErrorCause", e.getCause().getMessage());
            operationResult.addElmt("msg", "Failed to Delete, All process is aborted");
            log.error("LineService.delete : Failed to delete");
            return operationResult;
        }
    }

}
