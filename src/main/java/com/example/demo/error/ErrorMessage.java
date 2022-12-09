package com.example.demo.error;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * ErrorMessage is class representing the error
 * Has description and time of happened exception
 */
@Data
public class ErrorMessage {
    private LocalDateTime createdTime = LocalDateTime.now();
    private String description;

    public ErrorMessage(String message){
        this.description = message;
    }

}
