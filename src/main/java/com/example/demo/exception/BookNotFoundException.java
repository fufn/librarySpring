package com.example.demo.exception;

/**
 * BookNotFoundException is the exception that raises when there is no requested book in database
 */
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String msg){
        super(msg);
    }

}
