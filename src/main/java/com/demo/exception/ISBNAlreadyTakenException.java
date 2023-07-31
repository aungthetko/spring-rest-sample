package com.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ISBNAlreadyTakenException extends RuntimeException{

    private String fieldValue;

    public ISBNAlreadyTakenException(String fieldValue){
        super(String.format("ISBN number %s is already exist!", fieldValue));
        this.fieldValue = fieldValue;
    }
}
