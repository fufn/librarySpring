package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Library class represents the library object
 * Has id, name and List of Books stored in library.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "library")
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String name;

    @OneToMany (fetch = FetchType.LAZY)
    private List<Book> books;
}
