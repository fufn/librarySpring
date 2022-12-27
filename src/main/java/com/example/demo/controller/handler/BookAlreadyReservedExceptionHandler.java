package com.example.demo.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * BookAlreadyReservedExceptionHandler is the exception that raises when the book is booked by someone
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BookAlreadyReservedExceptionHandler extends RuntimeException{
    public BookAlreadyReservedExceptionHandler(String msg){
        super(msg);
    }
}
