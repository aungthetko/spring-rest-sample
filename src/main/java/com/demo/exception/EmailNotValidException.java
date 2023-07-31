package com.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailNotValidException extends RuntimeException{

    private String fieldValue;

    public EmailNotValidException(String fieldValue){
        super(String.format("Your email %s is not valid!", fieldValue));
        this.fieldValue = fieldValue;
    }
}
