package com.studb.poemNote.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.studb.poemNote.domain.poem.Poem;
import com.studb.poemNote.domain.poem.PoemDto;
import com.studb.poemNote.domain.poem.PoemNoPublishedDto;
import com.studb.poemNote.domain.poem.PoemTitleDto;
import com.studb.poemNote.error.Exception.BreakDatabaseWorkingException;
import com.studb.poemNote.repository.poem.PoemRepository;
import com.studb.poemNote.repository.published.PublishedRepository;
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
public class PoemService {
    
    private final PoemRepository poemRepository;
    private final PublishedRepository publishedRepository;
    private final LogService logService;
    private final BackupService backupService;

    @Autowired
    private DataSourceTransactionManager txManager;

    public List<PoemDto> findAll(){
        try{
            log.info("PoemService.findAll");
            return poemRepository.findAll().stream()
                    .map(PoemDto::new)
                    .collect(Collectors.toList());
        }catch(Exception e){
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }

    public PoemDto findAny(String textId){
        try{
            log.info("PoemService.findAny");
            Optional<PoemDto> result = poemRepository.findAny(textId).stream().map(PoemDto::new).findAny();
            return result.get();
        }catch(Exception e){
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }

    public List<PoemTitleDto> findPoemTitle(){
        try{
            log.info("PoemService.findPoemTitle");
            return poemRepository.findPoemTitle();
        } catch(Exception e){
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }

    public List<PoemNoPublishedDto> findAllNoPublished(){
        try{
            log.info("PoemService.findAllNoPublished");
            return poemRepository.findAllNoPublished();
        }catch(Exception e){
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }

    public List<PoemDto> findAllPublished(String textId) {
        try{
            log.info("PoemService.findAllPublished");
            return poemRepository.findAllPublished(textId).stream()
                    .map(PoemDto::new)
                    .collect(Collectors.toList());
        }catch(Exception e){
            e.printStackTrace();
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }

    public OperationResult save(Poem poem){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        OperationResult operationResult = new OperationResult();
        operationResult.addElmt("Category", "Poem");
        operationResult.addElmt("Action", "Write");

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try{
            if(poem.getTitle().isBlank()) poem.setTitle("제목 미정");
            poemRepository.save(poem, timestamp);
            publishedRepository.save("POEM", poem.getTextId());
            backupService.doBackup(poem.getTextId(), 0, poem.getTitle(), poem.getBody(), poem.getTag(), null, poem.getWrittenStatus(), null, timestamp);
            logService.insertLog("EDITOR", "Write", String.format("[POEM] %s", poem.getTitle()), timestamp);

            txManager.commit(status);
            operationResult.addElmt("Error", false);
            operationResult.addElmt("msg", "Complete to Write");
            log.info("PoemService.save : saved");
            return operationResult;
        }catch(Exception e){
            txManager.rollback(status);
            operationResult.addElmt("Error", true);
            operationResult.addElmt("ErrorMsg", e.getMessage());
            operationResult.addElmt("ErrorCause", e.getCause().getMessage());
            operationResult.addElmt("msg", "Failed to Write, All process is aborted");
            log.error("PoemService.save : Failed to save");
            return operationResult;
        }
    }

    public OperationResult delete(String textId){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        OperationResult operationResult = new OperationResult();
        operationResult.addElmt("Category", "Poem");
        operationResult.addElmt("Action", "Erase");

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try{
            int result = poemRepository.delete(textId);
            if(result == 0){
                txManager.rollback(status);
                operationResult.addElmt("Error", true);
                operationResult.addElmt("msg", "Any content isn't deleted, wrong textId");
                log.warn("PoemService.delete : No content");
            }else{
                publishedRepository.delete(textId);
                logService.insertLog("EDITOR", "Erase", "[POEM]", timestamp);

                txManager.commit(status);
                operationResult.addElmt("Error", false);
                operationResult.addElmt("msg", "Complete to Delete");
                log.info("PoemService.delete : deleted");
            }
            return operationResult;
        }catch(Exception e){
            txManager.rollback(status);
            operationResult.addElmt("Error", true);
            operationResult.addElmt("ErrorMsg", e.getMessage());
            operationResult.addElmt("ErrorCause", e.getCause().getMessage());
            operationResult.addElmt("msg", "Failed to Delete, All process is aborted");
            log.error("PoemService.delete : Falied to delete");
            return operationResult;
        }
    }

    public OperationResult update(Poem poem){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        OperationResult operationResult = new OperationResult();
        operationResult.addElmt("Category", "Poem");
        operationResult.addElmt("Action", "Rewrite");

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try{
            int result = poemRepository.update(poem, timestamp);
            if(result == 0){
                txManager.rollback(status);
                operationResult.addElmt("Error", true);
                operationResult.addElmt("msg", "Any content isn't updated, wrong textId");
                log.warn("PoemService.update : No content");
            }else{
                backupService.doBackup(poem.getTextId(), 0, poem.getTitle(), poem.getBody(), poem.getTag(), null, poem.getWrittenStatus(), null, timestamp);
                logService.insertLog("EDITOR", "Rewrite", String.format("[POEM] %s", poem.getTitle()), timestamp);
    
                txManager.commit(status);
                operationResult.addElmt("Error", false);
                operationResult.addElmt("msg", "Complete to Rewrite");
                log.info("PoemService.update : updated");
            }
            return operationResult;
        }catch(Exception e){
            txManager.rollback(status);
            operationResult.addElmt("Error", true);
            operationResult.addElmt("ErrorCause", e.getCause().getMessage());
            operationResult.addElmt("msg", "Failed to update, All process is aborted");
            log.error("PoemService.update : Failed to update");
            return operationResult;
        }
    }

    public OperationResult publish(Poem poem){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        OperationResult operationResult = new OperationResult();
        operationResult.addElmt("Category", "Poem");
        operationResult.addElmt("Action", "publish");

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try{
            Optional<Integer> publishNum = publishedRepository.up(poem.getTextId());
            if(publishNum.isEmpty()){
                txManager.rollback(status);
                operationResult.addElmt("Error", true);
                operationResult.addElmt("msg", "Stop to publish, Something wrong");
                log.warn("PoemService.publish : Falied to setting publishNum");
            }else{
                poem.setPublishIndex(publishNum.get());
                poemRepository.publish(poem, timestamp);
                backupService.doBackup(poem.getTextId(), poem.getPublishIndex(), poem.getTitle(), poem.getBody(), poem.getTag(), poem.getCompletedStatus(), null, poem.getValueStatus(), timestamp);
                logService.insertLog("EDITOR", "Publish", String.format("[POEM] %d - %s", poem.getPublishIndex(), poem.getTitle()), timestamp);

                txManager.commit(status);
                operationResult.addElmt("Error", false);
                operationResult.addElmt("msg", "Complete to Publish!");
                log.info("PoemService.publish : publish");
            }
            return operationResult;
        }catch(Exception e){
            txManager.rollback(status);
            operationResult.addElmt("Error", true);
            operationResult.addElmt("ErrorCause", e.getCause().getMessage());
            operationResult.addElmt("msg", "Failed to publish, All process is aborted");
            log.error("PoemService.publish : Falied to publish");
            return operationResult;
        }

    }

    public OperationResult updatePublished(Poem poem){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        OperationResult operationResult = new OperationResult();
        operationResult.addElmt("Category", "Poem published");
        operationResult.addElmt("Action", "update");

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try{
            int result = poemRepository.updatePublished(poem, timestamp);
            if(result == 0){
                txManager.rollback(status);
                operationResult.addElmt("Error", true);
                operationResult.addElmt("msg", "Any content isn't updated, wrong published");
                log.warn("PoemService.updatedPublished : No content");
            }else{
                backupService.doBackup(poem.getTextId(), poem.getPublishIndex(), poem.getTitle(), poem.getBody(), poem.getTag(), poem.getCompletedStatus(), null, poem.getValueStatus(), timestamp);
                logService.insertLog("EDITOR", "Rewrite", String.format("[POEM] %d - %s", poem.getPublishIndex(), poem.getTitle()), timestamp);

                txManager.commit(status);
                operationResult.addElmt("Error", false);
                operationResult.addElmt("msg", "Complete to Update!");
                log.info("PoemService.updatedPublished : publish");
            }
            return operationResult;
        }catch(Exception e){
            txManager.rollback(status);
            operationResult.addElmt("Error", true);
            operationResult.addElmt("ErrorCause", e.getCause().getMessage());
            operationResult.addElmt("msg", "Failed to update, All process is aborted");
            log.error("PoemService.updatedPublished : Failed to publish");
            return operationResult;
        }
    }
    
}
