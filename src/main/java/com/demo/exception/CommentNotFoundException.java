package com.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CommentNotFoundException extends RuntimeException{

    private String fieldValue;

    public CommentNotFoundException(String fieldValue){
        super(String.format("Comment %s is not found!", fieldValue));
        this.fieldValue = fieldValue;
    }
}
