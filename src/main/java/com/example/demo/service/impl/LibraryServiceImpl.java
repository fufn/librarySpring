package com.example.demo.service.impl;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.LibraryDto;
import com.example.demo.dto.mapper.impl.LibraryMapper;
import com.example.demo.entity.Library;
import com.example.demo.repository.LibraryRepository;
import com.example.demo.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LibraryServiceImpl implements LibraryService {

    private final LibraryRepository libraryRepository;
    private final LibraryMapper libraryMapper;

    @Override
    public LibraryDto getLibrary(Long id) {
        Library library = libraryRepository.findById(id).orElse(null);
        return libraryMapper.toDto(library);
    }

    @Override
    public List<LibraryDto> getLibraries() {
        List<LibraryDto> libraryDtos = new ArrayList<>();
        int count = (int) libraryRepository.count();
        int counter = 0;
        int pageSize = 20;
        int pageNumber = 0;
        while (counter < count) {
            Page<Library> libraries = libraryRepository.findAll(PageRequest.of(pageNumber, pageSize));
            for (Library l : libraries) {
                libraryDtos.add(libraryMapper.toDto(l));
            }
            counter+=pageSize;
            pageNumber++;
        }

        return libraryDtos;
    }

    @Override
    public LibraryDto addLibrary(LibraryDto library) {
        library.setBooks(new ArrayList<>());
        Library newLibrary = libraryRepository.save(libraryMapper.toEntity(library));
        return libraryMapper.toDto(newLibrary);
    }

    @Override
    public void deleteLibrary(Long id) {
        libraryRepository.deleteById(id);
    }

    @Override
    public LibraryDto updateLibrary(LibraryDto library) {
        Library newLibrary = libraryRepository.save(libraryMapper.toEntity(library));
        return libraryMapper.toDto(newLibrary);
    }

}
