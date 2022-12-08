package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

/**
 * Book is the entity representing book.
 * Has id, name, author, description, year and isBooked attributes.
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
    @JoinColumn(name = "library_id")
    private Library library;

}
