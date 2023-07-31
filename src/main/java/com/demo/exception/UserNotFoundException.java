package com.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{

    private String fieldValue;

    public UserNotFoundException(String fieldValue){
        super(String.format("Username %s is not found!", fieldValue));
        this.fieldValue = fieldValue;
    }
}
