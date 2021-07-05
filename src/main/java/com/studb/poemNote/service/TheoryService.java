package com.studb.poemNote.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.studb.poemNote.domain.theory.Theory;
import com.studb.poemNote.domain.theory.TheoryDto;
import com.studb.poemNote.domain.theory.TheoryTitleDto;
import com.studb.poemNote.error.Exception.BreakDatabaseWorkingException;
import com.studb.poemNote.repository.theory.TheoryRepository;
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
public class TheoryService {
    
    private final TheoryRepository theoryRepository;
    private final LogService logService;
    private final BackupService backupService;

    @Autowired
    private DataSourceTransactionManager txManager;

    public List<TheoryDto> findAll(){
        try{
            log.info("TheoryService.findAll");
            return theoryRepository.findAll().stream()
                    .map(TheoryDto::new)
                    .collect(Collectors.toList());
        }catch(Exception e){
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }

    public TheoryDto findAny(String textId){
        try{
            log.info("TheoryService.findAny");
            Optional<TheoryDto> result = theoryRepository.findAny(textId).stream().map(TheoryDto::new).findAny();
            return result.get();
        }catch(Exception e){
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }

    public List<TheoryTitleDto> findTheoryTitle() {
        try{
            log.info("TheoryService.findTheoryTitle");
            return theoryRepository.findTheoryTitle();
        }catch(Exception e){
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }

    public OperationResult save(Theory theory){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        OperationResult operationResult = new OperationResult();
        operationResult.addElmt("Category", "Theory");
        operationResult.addElmt("Action", "Write");
        operationResult.addElmt("Time", timestamp);

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try{
            if(theory.getTitle().isBlank()) theory.setTitle("제목미정");
            theoryRepository.save(theory, timestamp);
            backupService.doBackup(theory.getTextId(), theory.getTitle(), theory.getBody(), theory.getTag(), timestamp);
            logService.insertLog("EDITOR", "Write", String.format("[THEORY] %s", theory.getTitle()), timestamp);

            txManager.commit(status);
            operationResult.addElmt("Error", false);
            operationResult.addElmt("msg", "Complete to Write");
            log.info("TheoryService.save : saved");
            return operationResult;
        }catch(Exception e){
            txManager.rollback(status);
            operationResult.addElmt("Error", true);
            operationResult.addElmt("ErrorMsg", e.getMessage());
            operationResult.addElmt("ErrorCause", e.getCause().getMessage());
            operationResult.addElmt("msg", "Failed to Write Opinion, All process is aborted");
            log.error("TheoryService.save : Failed to save");
            return operationResult;
        }
    }

    public OperationResult delete(String textId){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        OperationResult operationResult = new OperationResult();
        operationResult.addElmt("Category", "Theory");
        operationResult.addElmt("Action", "Erase");

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try{
            int result = theoryRepository.delete(textId);
            if(result == 0) {
                txManager.rollback(status);
                operationResult.addElmt("Error", true);
                operationResult.addElmt("msg", "Any content isn't deleted, wrong textId");
                log.info("TheoryService.delete : No content");
            }else {
                logService.insertLog("EDITOR", "Erase", "[THEORY]", timestamp);
                txManager.commit(status);
                operationResult.addElmt("Error", false);
                operationResult.addElmt("msg", "Complete to delete!");
                log.warn("TheoryService.delete : deleted");
            }
            return operationResult;
        }catch(Exception e){
            txManager.rollback(status);
            operationResult.addElmt("Error", true);
            operationResult.addElmt("ErrorMsg", e.getMessage());
            operationResult.addElmt("ErrorCause", e.getCause().getMessage());
            operationResult.addElmt("msg", "Failed to Delete, All process is aborted");
            log.error("TheoryService.delete : Failed to delete");
            return operationResult;
        }
    }

    public OperationResult update(Theory theory){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        OperationResult operationResult = new OperationResult();
        operationResult.addElmt("Category", "Theory");
        operationResult.addElmt("Action", "Rewrite");

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try{
            int result = theoryRepository.update(theory, timestamp);
            if(result == 0){
                txManager.rollback(status);
                operationResult.addElmt("Error", true);
                operationResult.addElmt("msg", "Any content isn't updated, wrong textId");
                log.info("TheoryService.update : No content");
            } else {
                backupService.doBackup(theory.getTextId(), theory.getTitle(), theory.getBody(), theory.getTag(), timestamp);
                logService.insertLog("EDITOR", "Rewrite", String.format("[THEORY] %s", theory.getTitle()), timestamp);
                txManager.commit(status);
                operationResult.addElmt("Error", false);
                operationResult.addElmt("msg", "Complete to update!");
                log.warn("TheoryService.update : updated");
            }
            return operationResult;
        }catch(Exception e){
            txManager.rollback(status);
            operationResult.addElmt("Error", true);
            operationResult.addElmt("ErrorMsg", e.getMessage());
            operationResult.addElmt("ErrorCause", e.getCause().getMessage());
            operationResult.addElmt("msg", "Failed to Update, All procress is aborted");
            log.error("TheoryService.update : Failed to update");
            return operationResult;
        }
    }

}
