package com.studb.poemNote.utils;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;


public class ApiResultForm<T> {

    private int status;
    private T data;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
    private ErrorBox error;

    private ApiResultForm (T data){
        this.status = 200;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    private ApiResultForm (int status, String message, String exception, String causeException){
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.error = new ErrorBox(message, exception, causeException);
    }

    public int getStatus(){
        return this.status;
    }

    public T getData(){
        return this.data;
    }

    public LocalDateTime getTimestamp(){
        return this.timestamp;
    }

    public ErrorBox getError(){
        return this.error;
    }

    public static <T> ApiResultForm<T> result(T data){
        return new ApiResultForm<>(data);
    }

    public static ApiResultForm<?> error(int status, String message, String exception, String causeException){
        return new ApiResultForm<>(status, message, exception, causeException);
    }

    public class ErrorBox {

        private String message;
        private String exception;
        private String causeException;

        public ErrorBox(String message, String exception, String causeException){
            this.message = message;
            this.exception = exception;
            this.causeException = causeException;
        }

        public String getMessage(){
            return this.message;
        }

        public String getException(){
            return this.exception;
        }

        public String getCauseException(){
            return this.causeException;
        }

    }

}
