package com.example.demo.exception;

/**
 * LibraryNotFoundException is the exception that raises when there is no requested library in database
 */
public class LibraryNotFoundException extends RuntimeException{
    public LibraryNotFoundException(String msg){
        super(msg);
    }
}
