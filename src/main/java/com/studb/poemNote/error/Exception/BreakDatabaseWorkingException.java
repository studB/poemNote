package com.studb.poemNote.error.Exception;

public class BreakDatabaseWorkingException extends RuntimeException{
    
    public BreakDatabaseWorkingException(String message){
        super(message);
    }

    public BreakDatabaseWorkingException(String message, Throwable cause){
        super(message, cause);
    }
}
