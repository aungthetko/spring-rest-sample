package com.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserEmailAlreadyTakenException extends RuntimeException{

    private String fieldValue;

    public UserEmailAlreadyTakenException(String fieldValue){
        super(String.format("User email %s is already taken!", fieldValue));
        this.fieldValue = fieldValue;
    }
}
