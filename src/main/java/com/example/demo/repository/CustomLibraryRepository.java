package com.example.demo.repository;

import com.example.demo.entity.Library;
import com.example.demo.entity.Library_;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
@RequiredArgsConstructor
public class CustomLibraryRepository {
    private final LibraryRepository libraryRepository;

    public List<Library> findByFilters(String name, String city) {
        return libraryRepository.findAll(where(nameLike(name)).and(cityLike(city)));
    }

    public Specification<Library> cityLike(String city) {
        if (city == null) return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Library_.CITY), "%" + city + "%");
    }

    private Specification<Library> nameLike(String name) {
        if (name == null) return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Library_.NAME), "%" + name + "%");
    }
}
