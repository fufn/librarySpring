package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * LibraryDTO - class representing data transfer object
 * Used in LibraryController and LibraryServiceImpl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LibraryDto {

    private Long id;
    private String name;
    private List<BookDto> books;
}
