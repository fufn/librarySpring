package com.example.demo.controller;

import com.example.demo.error.ErrorMessage;
import com.example.demo.exception.BookNotFoundException;
import com.example.demo.exception.LibraryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Controller responsible for handling exceptions
 */
@RestControllerAdvice
public class ExceptionHandlerController {

    /**
     *
     * @param ex is the exception that was thrown
     * @return the error message with message, time and HttpStatus
     */
    @ExceptionHandler(value = LibraryNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handleCustomerAlreadyExistsException(LibraryNotFoundException ex)
    {
        return new ErrorMessage(ex.getMessage());
    }

    /**
     *
     * @param ex is the exception that was thrown
     * @return the error message with message, time and HttpStatus
     */
    @ExceptionHandler(value = BookNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handleBookNotFoundException(BookNotFoundException ex)
    {
        return new ErrorMessage(ex.getLocalizedMessage());
    }
}
