package com.example.demo.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Book is the entity representing book.
 * Has id, name, author, description, year and isBooked attributes.
 * Has references to library and users table
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "book")
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String name;

    @Column
    private String author;

    @Column
    private String description;

    @Column
    private Integer year;

    @Column
    private Boolean isBooked;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Library library;

    @ManyToOne(cascade = CascadeType.MERGE)
    private User user;

}
