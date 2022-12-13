package com.example.demo.exception;

/**
 * Exception occurs when user wants to register with email that is busy
 */
public class UserAlreadyExist extends RuntimeException{
    public UserAlreadyExist(String msg) { super(msg); }
}
