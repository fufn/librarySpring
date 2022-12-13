package com.example.demo.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "library", cascade = CascadeType.MERGE)
    private List<Book> books;
}
