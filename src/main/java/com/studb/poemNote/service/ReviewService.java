package com.studb.poemNote.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.studb.poemNote.domain.review.Review;
import com.studb.poemNote.domain.review.ReviewDto;
import com.studb.poemNote.domain.review.ReviewNoPublishedDto;
import com.studb.poemNote.domain.review.ReviewTitleDto;
import com.studb.poemNote.error.Exception.BreakDatabaseWorkingException;
import com.studb.poemNote.repository.published.PublishedRepository;
import com.studb.poemNote.repository.review.ReviewRepository;
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
public class ReviewService {
    
    private final ReviewRepository reviewRepository;
    private final PublishedRepository publishedRepository;
    private final LogService logService;
    private final BackupService backupService;
    
    @Autowired
    private DataSourceTransactionManager txManager;

    public List<ReviewDto> findAll(){
        try{
            log.info("ReviewService.findAll");
            return reviewRepository.findAll().stream()
                    .map(ReviewDto::new)
                    .collect(Collectors.toList());
        }catch(Exception e){
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }

    public ReviewDto findAny(String textId) {
        try{
            log.info("ReviewService.findAny");
            Optional<ReviewDto> result = reviewRepository.findAny(textId).stream().map(ReviewDto::new).findAny();
            return result.get();
        }catch(Exception e){
            e.printStackTrace();
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }

    public List<ReviewTitleDto> findReviewTitle() {
        try{
            log.info("ReviewService.findReviewTitle");
            return reviewRepository.findReviewTitle();
        }catch(Exception e){
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }

    public List<ReviewNoPublishedDto> findAllNoPublished() {
        try{
            log.info("ReviewService.findAllNoPublished");
            return reviewRepository.findAllNoPublished();
        }catch(Exception e){
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }

    public List<ReviewDto> findAllPublished(String textId) {
        try {
            log.info("ReviewService.findAllPublished");
            return reviewRepository.findAllPublished(textId).stream()
                    .map(ReviewDto::new)
                    .collect(Collectors.toList());
        }catch(Exception e){
            if( e.getCause() instanceof Throwable){
                throw new BreakDatabaseWorkingException(e.getCause().getMessage(), e.getCause());
            }else{
                throw new BreakDatabaseWorkingException(e.getMessage());
            }
        }
    }

    public OperationResult save(Review review){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        OperationResult operationResult = new OperationResult();
        operationResult.addElmt("Category", "Review");
        operationResult.addElmt("Action", "Write");

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try{
            if(review.getTitle().isBlank()) review.setTitle("제목 미정");
            reviewRepository.save(review, timestamp);
            publishedRepository.save("REVIEW", review.getTextId());
            backupService.doBackup(review.getTextId(), 0, review.getTitle(), review.getBody(), review.getTag(), review.getWrittenStatus(), null, timestamp);
            logService.insertLog("EDITOR", "Write", String.format("[REVIEW] %s", review.getTitle()), timestamp);

            txManager.commit(status);
            operationResult.addElmt("Error", false);
            operationResult.addElmt("msg", "Complete to Write");
            log.info("ReviewService.save : saved");
            return operationResult;
        }catch(Exception e){
            txManager.rollback(status);
            operationResult.addElmt("Error", true);
            operationResult.addElmt("ErrorMsg", e.getMessage());
            operationResult.addElmt("ErrorCause", e.getCause().getMessage());
            operationResult.addElmt("msg", "Failed to Write, All process is aborted");
            log.error("ReviewService.save : Falied to save");
            return operationResult;
        }
    }

    public OperationResult delete(String textId){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        OperationResult operationResult = new OperationResult();
        operationResult.addElmt("Category", "Review");
        operationResult.addElmt("Action", "Erase");

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try{
            int result = reviewRepository.delete(textId);
            if( result == 0 ){
                txManager.rollback(status);
                operationResult.addElmt("Error", true);
                operationResult.addElmt("msg", "Any content isn't deleted, wrong textId");
                log.warn("ReviewService.delete : No content");
            }else{
                publishedRepository.delete(textId);
                logService.insertLog("EDITOR", "Erase", "[REVIEW]", timestamp);

                txManager.commit(status);
                operationResult.addElmt("Error", false);
                operationResult.addElmt("msg", "Complete to Delete");
                log.info("ReviewService.delete : deleted");
            }
            return operationResult;
        }catch(Exception e){
            txManager.rollback(status);
            operationResult.addElmt("Error", true);
            operationResult.addElmt("ErrorMsg", e.getMessage());
            operationResult.addElmt("ErrorCause", e.getCause().getMessage());
            operationResult.addElmt("msg", "Failed to Delete, All process is aborted");
            log.error("ReviewService.delete : Failed to delete");
            return operationResult;
        }
    }

    public OperationResult update(Review review){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        OperationResult operationResult = new OperationResult();
        operationResult.addElmt("Category", "Review");
        operationResult.addElmt("Action", "Rewrite");

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try{
            int result = reviewRepository.update(review, timestamp);
            if( result == 0 ){
                txManager.rollback(status);
                operationResult.addElmt("Error", true);
                operationResult.addElmt("msg", "Any content isn't updated, wrong textId");
                log.warn("ReviewService.update : No content");
            }else{
                backupService.doBackup(review.getTextId(), 0, review.getTitle(), review.getBody(), review.getTag(), review.getWrittenStatus(), review.getValueStatus(), timestamp);
                logService.insertLog("EDITOR", "Rewrite", String.format("[REVIEW] %s", review.getTitle()), timestamp);

                txManager.commit(status);
                operationResult.addElmt("Error", false);
                operationResult.addElmt("msg", "Complete to Rewrite");
                log.info("ReviewService.update : updated");
            }
            return operationResult;
        }catch(Exception e){
            txManager.rollback(status);
            operationResult.addElmt("Error", true);
            operationResult.addElmt("ErrorCause", e.getCause().getMessage());
            operationResult.addElmt("msg", "Failed to update, All process is aborted");
            log.error("ReviewService.update : Failed to update");
            return operationResult;
        }
    }

    public OperationResult publish(Review review){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        OperationResult operationResult = new OperationResult();
        operationResult.addElmt("Category", "Review");
        operationResult.addElmt("Action", "publish");

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try{
            Optional<Integer> publishNum = publishedRepository.up(review.getTextId());
            if(publishNum.isEmpty()){
                txManager.rollback(status);
                operationResult.addElmt("Error", true);
                operationResult.addElmt("msg", "Stop to publish, Something wrong");
                log.warn("ReviewService.publish : Falied to setting publishNum");
            }else{
                review.setPublishIndex(publishNum.get());
                reviewRepository.publish(review, timestamp);
                backupService.doBackup(review.getTextId(), review.getPublishIndex(), review.getTitle(), review.getBody(), review.getTag(), null, review.getValueStatus(), timestamp);
                logService.insertLog("EDITOR", "Publish", String.format("[REVIEW] %d - %s", review.getPublishIndex(), review.getTitle()), timestamp);
 
                txManager.commit(status);
                operationResult.addElmt("Error", false);
                operationResult.addElmt("msg", "Complete to Publish!");
                log.info("ReviewService.publish : published");
            }
            return operationResult;
        }catch(Exception e){
            txManager.rollback(status);
            operationResult.addElmt("Error", true);
            operationResult.addElmt("ErrorCause", e.getCause().getMessage());
            operationResult.addElmt("msg", "Failed to publish, All process is aborted");
            log.error("ReviewService.publish : Failed to publish");
            return operationResult;
        }
    }

    public OperationResult updatePublished(Review review){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        OperationResult operationResult = new OperationResult();
        operationResult.addElmt("Category", "Review published");
        operationResult.addElmt("Action", "update");

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(def);

        try{
            int result = reviewRepository.updatePublished(review, timestamp);
            if( result == 0 ){
                txManager.rollback(status);
                operationResult.addElmt("Error", true);
                operationResult.addElmt("msg", "Any content isn't updated, wrong published");
                log.warn("ReviewService.updatePublished : No content");
            }else{
                backupService.doBackup(review.getTextId(), review.getPublishIndex(), review.getTitle(), review.getBody(), review.getTag(), null, review.getValueStatus(), timestamp);
                logService.insertLog("EDITOR", "Rewrite", String.format("[REVIEW] %d - %s", review.getPublishIndex(), review.getTitle()), timestamp);
 
                txManager.commit(status);
                operationResult.addElmt("Error", false);
                operationResult.addElmt("msg", "Complete to Update!");
                log.info("ReviewService.updatePublished : updated");
            }
            return operationResult;
        }catch(Exception e){
            txManager.rollback(status);
            operationResult.addElmt("Error", true);
            operationResult.addElmt("ErrorCause", e.getCause().getMessage());
            operationResult.addElmt("msg", "Failed to update, All process is aborted");
            log.error("ReviewService.updatePublished : Falied to update");
            return operationResult;
        }
    }

}
