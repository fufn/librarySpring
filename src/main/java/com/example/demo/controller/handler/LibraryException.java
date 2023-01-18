package com.example.demo.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * LibraryNotFoundException is the exception that raises when there is no requested library in database
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LibraryException extends RuntimeException {
    public LibraryException(String msg) {
        super(msg);
    }
}
