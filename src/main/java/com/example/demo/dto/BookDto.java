package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BookDTO - class representing data transfer object
 * Used in BookController and BookServiceImpl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String author;
    @JsonProperty
    private String description;
    @JsonProperty
    private Integer year;
    @JsonProperty
    private Boolean isBooked;
    @JsonProperty
    private Long libraryId;
    @JsonProperty
    private Long userId;

    @Override
    public String toString() {
        return "BookDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", year=" + year +
                ", isBooked=" + isBooked +
                ", libraryId=" + libraryId +
                ", userId=" + userId +
                '}';
    }
}
