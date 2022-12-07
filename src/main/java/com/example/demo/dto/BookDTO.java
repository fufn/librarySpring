package com.example.demo.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private Long libraryId;
    private Long id;
    private String name;
    private String author;
    private String description;
    private Integer year;
    private Boolean isBooked;

}
