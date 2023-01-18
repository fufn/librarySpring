package com.example.demo.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception occurs when user wants to register with email that is busy
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserException extends RuntimeException{
    public UserException(String msg) {
        super(msg); }
}
