package com.studb.poemNote.error;

import com.studb.poemNote.error.Exception.BreakDatabaseWorkingException;
import com.studb.poemNote.utils.ApiResultForm;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {
    
    @ExceptionHandler(BreakDatabaseWorkingException.class)
    protected ResponseEntity<?> handleBreakDatabaseWorkingException(Exception e) {

        System.out.println(e);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiResultForm<?> body = null;
        if( e.getCause() instanceof Throwable){
            body = ApiResultForm.error(status.value(), e.getMessage(), e.getClass().getSimpleName(), e.getCause().getClass().getSimpleName());
        }else{
            body = ApiResultForm.error(status.value(), e.getMessage(), e.getClass().getSimpleName(), "No Cause");
        }

        return new ResponseEntity<>(body, status);

    }

}
