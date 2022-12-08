package com.example.demo.dto;

import com.example.demo.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryDto {

    private Long id;
    private String name;
    private List<BookDto> books;
}