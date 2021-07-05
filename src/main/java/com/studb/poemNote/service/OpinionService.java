package com.studb.poemNote.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.studb.poemNote.domain.opinion.Opinion;
import com.studb.poemNote.domain.opinion.OpinionDto;
import com.studb.poemNote.domain.opinion.OpinionTitleDto;
import com.studb.poemNote.error.Exception.BreakDatabaseWorkingException;
import com.studb.poemNote.repository.opinion.OpinionRepository;
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
public class OpinionService {
    
    private final OpinionRepository opinionRepository;
    private final LogService logService;
    private final BackupService backupService;

    @Autowired
    private DataSourceTransactionManager txManager;

    public List<OpinionDto> findAll(){
        try{
            log.info("OpinionService.findAll");
            return opinionRepository.findAll().stream()
                    .map(OpinionDto::new)
                    .collect(Collectors.toList());
        }catch(Exception e){
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }

    public OpinionDto findAny(String textId){
        try{
            log.info("OpinionService.findAny");
            Optional<OpinionDto> result = opinionRepository.findAny(textId).stream().map(OpinionDto::new).findAny();
            return result.get();
        }catch(Exception e){
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }

    public List<OpinionTitleDto> findOpinionTitle(){
        try{
            log.info("OpinionService.findOpinionTitle");
            return opinionRepository.findOpinionTitle();
        }catch(Exception e){
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }

    public OperationResult save(Opinion opinion) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        OperationResult operationResult = new OperationResult();
        operationResult.addElmt("Category", "Opinion");
        operationResult.addElmt("Action", "Write");
        operationResult.addElmt("Time", timestamp);

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try{
            // Save Opinion to DB
            opinionRepository.save(opinion, timestamp);
            // Save Backup
            backupService.doBackup(opinion.getTextId(), opinion.getBody(), opinion.getTag(), timestamp);
            // Save Log
            logService.insertLog("EDITOR", "Write", "[OPINION] -", timestamp);

            txManager.commit(status);
            operationResult.addElmt("Error", false);
            operationResult.addElmt("msg", "Complete to Write");
            log.info("OpinionService.save : saved");
            return operationResult;
        }catch(Exception e){
            txManager.rollback(status);
            operationResult.addElmt("Error", true);
            operationResult.addElmt("ErrorMsg", e.getMessage());
            operationResult.addElmt("ErrorCause", e.getCause().getMessage());
            operationResult.addElmt("msg", "Failed to Write Opinion, All process is aborted");
            log.error("OpinionService.save : Failed to save");
            return operationResult;
        }
    }

    public OperationResult delete(String textId){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        OperationResult operationResult = new OperationResult();
        operationResult.addElmt("Category", "Opinion");
        operationResult.addElmt("Action", "Erase");

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try{
            int result = opinionRepository.delete(textId);
            if(result == 0) {
                txManager.rollback(status);
                operationResult.addElmt("Error", true);
                operationResult.addElmt("msg", "Any content isn't deleted, wrong textId");
                log.warn("OpinionService.delete : No content");
            }else {
                logService.insertLog("EDITOR", "Erase", "[OPINION] -", timestamp);
                txManager.commit(status);
                operationResult.addElmt("Error", false);
                operationResult.addElmt("msg", "Complete to delete!");
                log.info("OpinionService.delete : deleted");
            }
            return operationResult;
        }catch(Exception e){
            txManager.rollback(status);
            operationResult.addElmt("Error", true);
            operationResult.addElmt("ErrorMsg", e.getMessage());
            operationResult.addElmt("ErrorCause", e.getCause().getMessage());
            operationResult.addElmt("msg", "Failed to Delete, All process is aborted");
            log.error("OpinionService.delete : Falied to delete");
            return operationResult;
        }
    }

    public OperationResult update(Opinion opinion){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        OperationResult operationResult = new OperationResult();
        operationResult.addElmt("Category", "Opinion");
        operationResult.addElmt("Action", "Rewrite");

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try{
            int result = opinionRepository.update(opinion, timestamp);
            if(result == 0){
                txManager.rollback(status);
                operationResult.addElmt("Error", true);
                operationResult.addElmt("msg", "Any content isn't updated, wrong textId");
                log.warn("OpinionService.update : No content");
            } else {
                backupService.doBackup(opinion.getTextId(), opinion.getBody(), opinion.getTag(), timestamp);
                logService.insertLog("EDITOR", "Rewrite", "[OPINION] -", timestamp);
                txManager.commit(status);
                operationResult.addElmt("Error", false);
                operationResult.addElmt("msg", "Complete to update!");
                log.info("OpinionService.update : updated");
            }
            return operationResult;
        }catch(Exception e){
            txManager.rollback(status);
            operationResult.addElmt("Error", true);
            operationResult.addElmt("ErrorMsg", e.getMessage());
            operationResult.addElmt("ErrorCause", e.getCause().getMessage());
            operationResult.addElmt("msg", "Failed to Update, All procress is aborted");
            log.error("OpinionService.update : Failed to update");
            return operationResult;
        }
    }
}
