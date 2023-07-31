package com.demo.exception;

import com.demo.response.HttpResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserEmailAlreadyTakenException.class)
    public ResponseEntity<HttpResponse> handleUserEmailAlreadyExist(UserEmailAlreadyTakenException exception, WebRequest webRequest){
        HttpResponse httpResponse = new HttpResponse(
                LocalDateTime.now().toString(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "EMAIL_ALREADY_EXIST"
        );
        return new ResponseEntity<>(httpResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HttpResponse> handleUserNotFound(UserNotFoundException exception, WebRequest webRequest){
        HttpResponse httpResponse = new HttpResponse(
                LocalDateTime.now().toString(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "USER_NOT_FOUND"
        );
        return new ResponseEntity<>(httpResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<HttpResponse> handleBookNotFound(BookNotFoundException exception, WebRequest webRequest){
        HttpResponse httpResponse = new HttpResponse(
                LocalDateTime.now().toString(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "BOOK_NOT_FOUND"
        );
        return new ResponseEntity<>(httpResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<HttpResponse> handleCommentNotFound(CommentNotFoundException exception, WebRequest webRequest){
        HttpResponse httpResponse = new HttpResponse(
                LocalDateTime.now().toString(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "COMMENT_NOT_FOUND"
        );
        return new ResponseEntity<>(httpResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ISBNAlreadyTakenException.class)
    public ResponseEntity<HttpResponse> handleBookISBNAlreadyExist(ISBNAlreadyTakenException exception, WebRequest webRequest){
        HttpResponse httpResponse = new HttpResponse(
                LocalDateTime.now().toString(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "ISBN_NUMBER_ALREADY_EXIST"
        );
        return new ResponseEntity<>(httpResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailNotValidException.class)
    public ResponseEntity<HttpResponse> handleEmailNotValid(EmailNotValidException exception, WebRequest webRequest){
        HttpResponse httpResponse = new HttpResponse(
                LocalDateTime.now().toString(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "EMAIL_IS_NOT_VALID"
        );
        return new ResponseEntity<>(httpResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        List<ObjectError> errorList = ex.getBindingResult().getAllErrors();
        errorList.forEach(error -> {
            String tag = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorMap.put(tag, message);
        });
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }
}
