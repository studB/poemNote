package com.studb.poemNote.error.Exception;

public class WrongKeyAccessException extends RuntimeException{
    
    public WrongKeyAccessException(String message){
        super(message);
    }

}
