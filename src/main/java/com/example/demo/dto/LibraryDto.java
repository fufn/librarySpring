package com.example.demo.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String city;
    private List<BookDto> books;
}
