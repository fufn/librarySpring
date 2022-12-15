package com.example.demo.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * BookNotFoundException is the exception that raises when there is no requested book in database
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BookNotFoundExceptionHandler extends RuntimeException {

    public BookNotFoundExceptionHandler(String msg){
        super(msg);
    }

}
